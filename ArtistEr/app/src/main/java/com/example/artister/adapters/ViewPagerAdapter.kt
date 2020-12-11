package com.example.artister.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import  androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val lstFragments = ArrayList<Fragment>()
    private val lstFragmentsTitles = ArrayList<String>()

    override fun getCount(): Int {
        return lstFragments.size
    }

    override fun getItem(i: Int): Fragment {
        return lstFragments[i]
    }

    override fun getPageTitle(i: Int): CharSequence {
        return lstFragmentsTitles[i]
    }

    fun addFragment(fragment: Fragment, title: String) {

        lstFragments.add(fragment)
        lstFragmentsTitles.add(title)
    }
}