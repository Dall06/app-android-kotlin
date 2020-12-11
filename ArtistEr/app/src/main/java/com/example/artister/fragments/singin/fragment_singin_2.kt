@file:Suppress("DEPRECATION")

package com.example.artister.fragments.singin

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.input
import com.example.artister.R
import com.example.artister.api.ApiResponse
import com.example.artister.communicators.Communicator
import com.example.artister.controllers.UserController
import com.example.artister.converters.ImageConverter
import com.example.artister.models.Links
import com.example.artister.models.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_singin_2.*
import kotlinx.android.synthetic.main.fragment_singin_2.bt_fb
import kotlinx.android.synthetic.main.fragment_singin_2.bt_ig
import kotlinx.android.synthetic.main.fragment_singin_2.bt_sp
import kotlinx.android.synthetic.main.fragment_singin_2.bt_web
import kotlinx.android.synthetic.main.fragment_singin_2.bt_yt
import kotlinx.android.synthetic.main.fragment_singin_2.et_bio
import kotlinx.android.synthetic.main.fragment_singin_2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragment_singin_2 : Fragment() {
    private val userController = UserController()
    private val imageConverter = ImageConverter()
    private var links = Links()
    private var passedUser = User(links = links)
    private var imgProvided: Bitmap? = null
    private var img: String = "no_photo"
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_singin_2, container, false)

        passedUser = arguments?.getParcelable("data1")!!

        val accountTypeList = arrayOf("artist", "agency")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            accountTypeList
        )

        view.sp_accountType.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = activity as Communicator

        ib_takepic.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                ImageProvider(requireActivity()).getImage(ImageSource.CAMERA) { bitmap ->
                    imgProvided = bitmap
                    ib_takepic.setImageBitmap(bitmap)
                    img = imageConverter.convertToBase64(imgProvided!!)
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    42424
                )
            }
        }

        bt_web.setOnClickListener {
            passedUser = setAndShowMaterialDialog(passedUser, "web page")
        }

        bt_fb.setOnClickListener {
            passedUser = setAndShowMaterialDialog(passedUser, "facebook page")
        }

        bt_yt.setOnClickListener {
            passedUser = setAndShowMaterialDialog(passedUser, "Youtube channel")
        }

        bt_ig.setOnClickListener {
            passedUser = setAndShowMaterialDialog(passedUser, "instagram profile")
        }

        bt_sp.setOnClickListener {
            passedUser = setAndShowMaterialDialog(passedUser, "soptify account")
        }

        bt_create.setOnClickListener {
            if (imgProvided == null) {
                Toast.makeText(requireContext(), "no image selected", Toast.LENGTH_SHORT).show()
            }

            passedUser.img = img

            passedUser.bio = et_bio.text.toString()
            passedUser.userType = sp_accountType.selectedItemPosition + 1

            val result = createUser(passedUser)

            result.enqueue(object: Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Snackbar.make(view, "Congrats your account has been created", Snackbar.LENGTH_LONG)
                            .setAction("ok", null).show()
                        requireActivity().finish()
                    } else {
                        Snackbar.make(view, "Failed to create account try it later :c", Snackbar.LENGTH_LONG)
                            .setAction("ok", null).show()
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    throw t
                }
            })
        }

        bt_back.setOnClickListener {
            communicator.toReturn()
        }
    }

    private fun setAndShowMaterialDialog(user: User, title: String): User {
        var string = ""

        when (title) {
            "web page" -> {
                string = user.links.webPage
            }
            "facebook page" -> {
                string = user.links.facebookPage
            }
            "Youtube channel" -> {
                string = user.links.youtubeChannel
            }
            "instagram profile" -> {
                string = user.links.instagramProfile
            }
            "soptify account" -> {
                string = user.links.spotifyAccount
            }
            else -> {
                Toast.makeText(requireContext(), "Something gone wrong", Toast.LENGTH_SHORT).show()
            }
        }

        MaterialDialog(requireContext()).show {
            input(
                waitForPositiveButton = false,
                hint = title,
                prefill = string
            ) { dialog, text ->
                when (title) {
                    "web page" -> {
                        user.links.webPage = text.toString()
                        dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                    }
                    "facebook page" -> {
                        user.links.facebookPage = text.toString()
                        dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                    }
                    "Youtube channel" -> {
                        user.links.youtubeChannel = text.toString()
                        dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                    }
                    "instagram profile" -> {
                        user.links.instagramProfile = text.toString()
                        dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                    }
                    "soptify account" -> {
                        user.links.spotifyAccount = text.toString()
                        dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Something gone wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            positiveButton(R.string.submit)
        }
        return user
    }

    private fun createUser(user: User): Call<ApiResponse> {
        return userController.addUser(user)
    }
}