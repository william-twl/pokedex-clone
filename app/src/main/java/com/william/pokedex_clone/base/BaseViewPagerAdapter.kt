package com.william.pokedex_clone.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.ViewGroup

class BaseViewPagerAdapter(fm: FragmentManager, var context: Context) : FragmentStatePagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


    var fragments: ArrayList<Fragment> = arrayListOf()
    var titles: ArrayList<String> = arrayListOf()

    fun addFragment(fragment: Fragment, title: String) {
        titles.add(title)
        fragments.add(fragment)
    }

    fun addFragment(fragment: Fragment, title: Int) {
        addFragment(fragment, context.getString(title))
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {

        return fragments.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        super.destroyItem(container, position, o)

        var view: Fragment? = o as Fragment
        (container as ViewPager).removeView(view?.view)
    }
}