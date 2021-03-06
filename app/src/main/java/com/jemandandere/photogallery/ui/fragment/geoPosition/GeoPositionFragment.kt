package com.jemandandere.photogallery.ui.fragment.geoPosition

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemandandere.photogallery.R

class GeoPositionFragment : Fragment() {

    companion object {
        fun newInstance() = GeoPositionFragment()
    }

    private lateinit var viewModel: GeoPositionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.geo_position_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GeoPositionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}