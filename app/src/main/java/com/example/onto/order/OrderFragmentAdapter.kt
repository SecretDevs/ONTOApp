package com.example.onto.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.onto.order.confirmation.OrderConfirmationFragment
import com.example.onto.order.contact.OrderContactDetailsFragment
import com.example.onto.order.delivery.OrderDeliveryFragment

class OrderFragmentAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> OrderContactDetailsFragment()
            1 -> OrderDeliveryFragment()
            2 -> OrderConfirmationFragment()
            else -> throw IllegalArgumentException("Position out of bounds.")
        }
}