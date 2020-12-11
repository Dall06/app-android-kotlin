package com.example.artister.fragments.nav

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.input
import com.example.artister.MainActivity
import com.example.artister.R
import com.example.artister.api.ApiResponse
import com.example.artister.controllers.UserController
import com.example.artister.converters.ImageConverter
import com.example.artister.helpers.SharedApp
import com.example.artister.models.Links
import com.example.artister.models.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class fragment_account : Fragment() {
    private lateinit var userController: UserController
    private val imageConverter = ImageConverter()
    private var img ="no_photo"
    private var imgProvided: Bitmap? = null
    private lateinit var links: Links
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // setStatusBarCompat()
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userController = UserController()
        links = Links()
        user = User(links = links)
        var bm: Bitmap?

        setStatusBarCompat()

        val logged = getLoggedUser(savedId()!!)

        logged.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Snackbar.make(view, "Failed to get logged user", Snackbar.LENGTH_LONG)
                    .setAction("ok", null).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user = response.body()!!

                    if (user.img == "no_photo" || user.img == "" || user.img == "null") {
                        iv_account.setImageResource(R.drawable.no_img)
                    } else {
                        bm = imageConverter.convertToBitmap(user.img)
                        iv_account.setImageBitmap(bm)
                    }

                    bt_web.setOnClickListener {
                        user = setAndShowMaterialDialog(user, "web page")
                    }

                    bt_fb.setOnClickListener {
                        user = setAndShowMaterialDialog(user, "facebook page")
                    }

                    bt_yt.setOnClickListener {
                        user = setAndShowMaterialDialog(user, "Youtube channel")
                    }

                    bt_ig.setOnClickListener {
                        user = setAndShowMaterialDialog(user, "instagram profile")
                    }

                    bt_sp.setOnClickListener {
                        user = setAndShowMaterialDialog(user, "soptify account")
                    }

                    ib_newImg.setOnClickListener {
                        if (ActivityCompat.checkSelfPermission(
                                requireContext(),
                                android.Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            ImageProvider(requireActivity()).getImage(ImageSource.CAMERA) { bitmap ->
                                if (bitmap != null) {
                                    imgProvided = bitmap
                                    iv_account.setImageBitmap(imgProvided)
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

                    et_account.setText(user.accountName)
                    et_email.setText(user.email)
                    et_phone.setText(user.phone)
                    et_bio.setText(user.bio)

                    fab_update.setOnClickListener {
                        if (img == "null" || img == "" || img == "no_photo") {
                            Toast
                                .makeText(
                                    requireContext(), "null img, keeping last image",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            img = savedImg()!!
                        }

                        user = setAccountToUpdate(user, et_email.text.toString(), et_phone.text.toString(),
                            et_bio.text.toString(), et_account.text.toString())

                        savedId()?.let { updateAction(it, user) }
                    }
                }
            }
        })

        bt_logout.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)

            requireActivity().finish()
            startActivity(intent)
        }
    }

    private fun savedId(): String? {
        return SharedApp.prefs._id
    }

    private fun savedImg(): String? {
        return SharedApp.prefs.img
    }

    private fun setAccountToUpdate(user: User, et_email: String, et_phone: String,
                                   et_bio: String, et_account: String) : User {
        user.email = et_email
        user.phone = et_phone
        user.bio = et_bio
        user.accountName = et_account
        user.img = img

        SharedApp.prefs.accountName = user.accountName
        SharedApp.prefs._id = user._id
        SharedApp.prefs.email = user.email
        SharedApp.prefs.img = user.img

        return user
    }

    private fun setStatusBarCompat() {

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

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

    private fun updateAction(id: String, user: User) {
        val result = userController.updateUser(id, user)

        result.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                throw t
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Snackbar.make(requireView(), "User updated", Snackbar.LENGTH_LONG)
                        .setAction("ok", null).show()
                } else {
                    Snackbar.make(requireView(), "Failed to update user", Snackbar.LENGTH_LONG)
                        .setAction("ok", null).show()
                }
            }
        })
    }

    private fun getLoggedUser(id: String): Call<User> {
        return userController.getUseById(id)
    }
}