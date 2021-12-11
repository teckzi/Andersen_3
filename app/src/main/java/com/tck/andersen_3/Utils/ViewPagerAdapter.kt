package com.tck.andersen_3.Utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tck.andersen_3.R
import com.tck.andersen_3.TaskOne
import com.tck.andersen_3.TaskTwo


class ViewPagerAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {

    val pageNameList = listOf(fm.getString(R.string.taskone), fm.getString(R.string.tasktwo))

    override fun getItemCount(): Int {
        return pageNameList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> TaskTwo()
            else -> TaskOne()
        }
    }
}