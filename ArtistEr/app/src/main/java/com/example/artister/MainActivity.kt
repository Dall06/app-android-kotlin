package com.example.artister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artister.api.ApiResponseLogin
import com.example.artister.controllers.UserController
import com.example.artister.helpers.SharedApp
import com.example.artister.models.Links
import com.example.artister.models.User
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val userController: UserController = UserController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val links = Links()
        val user = User(links = links)


        bt_login.setOnClickListener {
            user.email = et_u_email.text.toString()
            user.password = et_u_pwd.text.toString()

            loginAction(user, this@MainActivity)
        }

        bt_singnin.setOnClickListener {
            val intent = Intent(this, SinginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginAction(user: User, context: Context) {
        val result = userController.loginUser(user)

        result.enqueue(object : Callback<ApiResponseLogin> {
            override fun onFailure(call: Call<ApiResponseLogin>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ApiResponseLogin>,
                response: Response<ApiResponseLogin>
            ) {
                if (response.isSuccessful) {
                    val intent = Intent(context, NavigationActivity::class.java)

                    if(response.body()?.message == "logged") {
                        SharedApp.prefs._id = response.body()?.body?._id
                        SharedApp.prefs.accountName = response.body()?.body?.accountName
                        SharedApp.prefs.email = response.body()?.body?.email
                        SharedApp.prefs.img = response.body()?.body?.img
                        SharedApp.prefs.phone = response.body()?.body?.phone
                        SharedApp.prefs.bio = response.body()?.body?.bio

                        (context as Activity).finish()
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Cannot login, something happened :c",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}