package com.example.artister.fragments.tab

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.example.artister.R
import com.example.artister.adapters.RecyclerViewMediaAdapter
import com.example.artister.api.ApiResponse
import com.example.artister.controllers.MediaController
import com.example.artister.converters.ImageConverter
import com.example.artister.helpers.SharedApp
import com.example.artister.models.LinksMedia
import com.example.artister.models.Media
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_add_media.view.*
import kotlinx.android.synthetic.main.fragment_media.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragment_tab_media : Fragment() {
    private val mediaRvAdapter = RecyclerViewMediaAdapter()
    private lateinit var mediaController: MediaController
    private var links = LinksMedia()
    private var media = Media(links = links)
    private var imgProvided: Bitmap? = null
    private val imageConverter = ImageConverter()
    private var img: String = "no_photo"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaController = MediaController()

        setUpRView(requireContext())
        loadMediaAction(requireContext())

        swipe_rv.setOnRefreshListener {
            loadMediaAction(requireContext())
            swipe_rv.isRefreshing = false
        }

        bt_add_media.setOnClickListener {
            if (imgProvided == null) {
                Toast.makeText(requireContext(), "no image selected", Toast.LENGTH_SHORT).show()
            }

            val builder = AlertDialog.Builder(requireContext())
            val inflater = requireActivity().layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialog_add_media, null)

            val etDesc = dialogLayout.findViewById<EditText>(R.id.et_desc)
            val etWeb = dialogLayout.findViewById<EditText>(R.id.et_Web)
            val etFb = dialogLayout.findViewById<EditText>(R.id.et_fb)
            val etYt = dialogLayout.findViewById<EditText>(R.id.et_yt)
            val etSp = dialogLayout.findViewById<EditText>(R.id.et_sp)
            val etIg = dialogLayout.findViewById<EditText>(R.id.et_ig)


            val accountTypeList = arrayOf("Music", "Photo", "Film", "Design", "Tattoo", "Lecture")
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                accountTypeList
            )
            dialogLayout.sp_category.adapter = adapter


            builder
                .setTitle("Add media")
                .setView(dialogLayout)
                .setPositiveButton("Add"
                ) { _, _ ->

                    if (img == "null" || img == "" || img == "no_photo") {
                        Toast.makeText(
                            requireContext(),
                            "null img, keeping last image",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        media.img = img
                    }

                    media = setMedia(
                        media, etDesc.text.toString(), etWeb.text.toString(),
                        etFb.text.toString(), etYt.text.toString(), etSp.text.toString(),
                        etIg.text.toString(), dialogLayout
                    )

                    addMediaAction(media)
                }
                .setNegativeButton("Cancel"
                ) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
            builder.show()

            dialogLayout.ib_takepic.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    ImageProvider(requireActivity()).getImage(ImageSource.CAMERA) { bitmap ->
                        if (bitmap != null) {
                            imgProvided = bitmap
                            dialogLayout.ib_takepic.setImageBitmap(imgProvided)
                            img = imageConverter.convertToBase64(imgProvided!!)
                        }
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.CAMERA),
                        42424
                    )
                }
            }
        }
    }

    private fun setUpRView(context: Context) {
        rv_media.adapter = mediaRvAdapter
        rv_media.layoutManager = LinearLayoutManager(context)
    }

    private fun setMedia(media: Media, etDesc: String, etWeb: String, etFb: String, etYt: String,
                         etSp: String, etIg: String, dialogLayout: View): Media {
        media.description = etDesc
        media.links.webPage = etWeb
        media.links.facebookPage = etFb
        media.links.youtubeChannel = etYt
        media.links.spotifyAccount = etSp
        media.links.instagramProfile = etIg
        media.img = img
        media.userId = savedId()!!
        media.userName = savedAcName()!!
        media.category = dialogLayout.sp_category.selectedItemPosition

        return media
    }

    private fun loadMediaAction(context: Context) {
        val result = mediaController.getAllMedia()

        result.enqueue(object : Callback<List<Media>> {
            override fun onFailure(call: Call<List<Media>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Media>>, response: Response<List<Media>>) {
                if (response.isSuccessful) {
                    val lstMedia: List<Media>? = response.body()

                    if (lstMedia != null) {
                        mediaRvAdapter.setData(lstMedia)
                    }

                } else {
                    Toast.makeText(context, "Cannot get data", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun addMediaAction(media: Media) {
        val result = addMedia(media)

        result.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    Snackbar.make(requireView(), "Media added", Snackbar.LENGTH_LONG)
                        .setAction("ok", null).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        response.toString(),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("ok", null).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                throw t
            }
        })
    }

    private fun savedId(): String? {
        return SharedApp.prefs._id
    }

    private fun savedAcName(): String? {
        return SharedApp.prefs.accountName
    }

    private fun addMedia(media: Media): Call<ApiResponse> {
        return mediaController.addMedia(media)
    }
}