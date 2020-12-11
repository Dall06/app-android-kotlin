package com.example.artister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.artister.communicators.Communicator
import com.example.artister.fragments.singin.fragment_singin_1
import com.example.artister.fragments.singin.fragment_singin_2
import com.example.artister.models.User

class SinginActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singin)

        val fragment1 = fragment_singin_1()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment1).commit()
    }

    override fun passData(user: User) {
        val bundle = Bundle()
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragment2 = fragment_singin_2()

        bundle.putParcelable("data1", user)

        fragment2.arguments = bundle
        transaction.replace(R.id.fragment_container, fragment2)
        transaction.commit()
    }

    override fun toReturn() {
        val bundle = Bundle()
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragment1 = fragment_singin_1()

        fragment1.arguments = bundle
        transaction.replace(R.id.fragment_container, fragment1)
        transaction.commit()
    }
}