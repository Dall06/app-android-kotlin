package com.example.artister.fragments.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.artister.R
import com.example.artister.adapters.ViewPagerAdapter
import com.example.artister.fragments.tab.fragment_tab_artists
import com.example.artister.fragments.tab.fragment_tab_agencies
import com.example.artister.fragments.tab.fragment_tab_media
import kotlinx.android.synthetic.main.fragment_nav_home.*


class fragment_nav_home : Fragment() {
    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(fragment_tab_media(), "Media")
        adapter.addFragment(fragment_tab_artists(), "Artists")
        adapter.addFragment(fragment_tab_agencies(), "Agencies")

        viewPager.adapter = adapter

        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)
        tabs.getTabAt(1)
        tabs.getTabAt(2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }
}