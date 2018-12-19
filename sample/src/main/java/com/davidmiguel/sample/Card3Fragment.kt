package com.davidmiguel.sample

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Example of closing activity both dragging or clicking.
 */
class Card3Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_card3, container, false)
    }

    companion object {

        internal fun newInstance(): Card3Fragment {
            return Card3Fragment()
        }
    }
}
