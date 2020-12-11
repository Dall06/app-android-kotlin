package com.example.artister.fragments.singin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.artister.R
import com.example.artister.communicators.Communicator
import com.example.artister.models.Links
import com.example.artister.models.User
import kotlinx.android.synthetic.main.fragment_singin_1.*

class fragment_singin_1 : Fragment() {
    private lateinit var communicator: Communicator
    val links = Links()
    var passedUser = User(links = links)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singin_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = activity as Communicator

        bt_next.setOnClickListener {
            passedUser.email = et_email.text.toString()
            passedUser.accountName = et_account.text.toString()
            passedUser.password = et_password.text.toString()
            passedUser.phone = et_phone.text.toString()
            communicator.passData(passedUser)
        }

        bt_cancel.setOnClickListener {
            requireActivity().finish()
        }
    }
}