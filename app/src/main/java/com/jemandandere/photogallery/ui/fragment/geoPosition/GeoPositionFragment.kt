package com.jemandandere.photogallery.ui.fragment.geoPosition

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import com.google.android.gms.location.*
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.databinding.GeoPositionFragmentBinding
import com.jemandandere.photogallery.service.GeoPositionService
import com.jemandandere.photogallery.util.Constants.REQUEST_PERMISSIONS_REQUEST_CODE
import com.jemandandere.photogallery.util.Constants.TAG
import com.jemandandere.photogallery.util.Utils

class GeoPositionFragment : Fragment(R.layout.geo_position_fragment),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var viewModel: GeoPositionViewModel
    private lateinit var binding: GeoPositionFragmentBinding

    private var receiver: Receiver? = null
    private var geoService: GeoPositionService? = null
    private var bound = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as GeoPositionService.LocalBinder
            geoService = binder.service
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            geoService = null
            bound = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GeoPositionFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(GeoPositionViewModel::class.java)
        receiver = Receiver()

        binding.geoPositionOnoffButton.setOnClickListener {

            if (Utils.requestingLocationUpdates(requireContext())) {
                //stop
                geoService!!.removeLocationUpdates()
            } else {
                //start
                if (!checkPermissions()) {
                    requestPermissions()
                } else {
                    geoService!!.requestLocationUpdates()
                }
            }
        }
        setButtonsState(Utils.requestingLocationUpdates(requireContext()))

    }

    override fun onStart() {
        super.onStart()

        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)
        requireActivity().bindService(
            Intent(requireContext(), GeoPositionService::class.java), serviceConnection,
            Context.BIND_AUTO_CREATE
        )
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver!!,
            IntentFilter(GeoPositionService.ACTION_BROADCAST)
        )
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver!!)
        if (bound) {
            requireActivity().unbindService(serviceConnection)
            bound = false
        }
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    private fun checkPermissions(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (shouldProvideRationale) {
            // TODO Dialog.show()
            Toast.makeText(requireContext(), "Разреши, пожалуйста, чё ты", Toast.LENGTH_SHORT)
                .show()
        }
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    geoService!!.requestLocationUpdates()
                }
                else -> {
                    setButtonsState(false)
                    Toast.makeText(requireContext(), "Зачем запрещаешь?", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        if (s == Utils.KEY_REQUESTING_LOCATION_UPDATES) {
            setButtonsState(
                sharedPreferences.getBoolean(
                    Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false
                )
            )
        }
    }

    private fun setButtonsState(requestingLocationUpdates: Boolean) {
        if (requestingLocationUpdates) {
            binding.geoPositionOnoffButton.text = requireContext().getString(R.string.geo_position_stop)
        } else {
            binding.geoPositionOnoffButton.text = requireContext().getString(R.string.geo_position_start)
        }
    }

    private inner class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val location: Location? =
                intent.getParcelableExtra(GeoPositionService.EXTRA_LOCATION)
            if (location != null) {
                binding.geoPositionPositionText.text = Utils.getLocationText(location)
            }
        }
    }
}