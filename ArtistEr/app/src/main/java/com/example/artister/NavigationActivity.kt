package com.example.artister

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.artister.converters.ImageConverter
import com.example.artister.helpers.SharedApp
import com.google.android.material.navigation.NavigationView

class NavigationActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val headerView: View = navView.getHeaderView(0)
        val tvHeaderAcName: TextView = headerView.findViewById(R.id.nav_header_tv_account_name)
        val tvHeaderEmail: TextView = headerView.findViewById(R.id.nav_header_tv_account_email)
        val ivHeader: ImageView = headerView.findViewById(R.id.nav_header_iv)

        val imageConverter = ImageConverter()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_account, R.id.nav_about
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val img = savedImg()

        if (img == "no_photo" || img == "" || img == "null") {
            ivHeader.setImageResource(R.drawable.no_img)
        } else {
            val bm: Bitmap? = img?.let { imageConverter.convertToBitmap(it) }
            ivHeader.setImageBitmap(bm)
        }

        tvHeaderEmail.text = savedEmail()
        tvHeaderAcName.text = savedAccountName()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun savedAccountName(): String? {
        return SharedApp.prefs.accountName
    }

    private fun savedEmail(): String? {
        return SharedApp.prefs.email
    }

    private fun savedImg(): String? {
        return SharedApp.prefs.img
    }
}