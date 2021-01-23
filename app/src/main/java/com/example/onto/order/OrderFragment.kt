package com.example.onto.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.onto.R
import com.example.onto.navigation.Coordinator
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_dialog_payment.*
import kotlinx.android.synthetic.main.fragment_order.*
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator
    private lateinit var fragmentAdapter: OrderFragmentAdapter
    private val onChangePage = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            progressBar.progress = (1.0 / 3 * 100 * (position + 1)).toInt() + (position + 1) / 3
            step_text.text = resources.getString(
                R.string.order_step_tab_title,
                resources.getString(R.string.order_step_text, position + 1),
                resources.getStringArray(R.array.order_description_text)[position]
            )
        }
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_order,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentAdapter = OrderFragmentAdapter(this)
        viewpager.adapter = fragmentAdapter
        viewpager.registerOnPageChangeCallback(onChangePage)
        arrow_back_btn.setOnClickListener { coordinator.pop() }
        checkout_prev.setOnClickListener {
            viewpager.setCurrentItem(
                if (viewpager.currentItem != 0) viewpager.currentItem - 1 else 0,
                true
            )
        }
        checkout_next.setOnClickListener {
            if (viewpager.currentItem == 2) {
                bottomSheetDialog.show()
            }
            viewpager.setCurrentItem(
                if (viewpager.currentItem != 2) viewpager.currentItem + 1 else 2,
                true
            )
        }
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_payment)
        bottomSheetDialog.dismissWithAnimation = true
        bottomSheetDialog.bottom_sheet_exit.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        viewpager.offscreenPageLimit = 3
    }

    override fun onStop() {
        super.onStop()
        viewpager.unregisterOnPageChangeCallback(onChangePage)
    }

    companion object {
        fun newInstance() = OrderFragment()
    }

}