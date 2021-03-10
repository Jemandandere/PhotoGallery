package com.jemandandere.photogallery.ui.fragment.geoPosition

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.databinding.GeoPositionFragmentBinding
import com.jemandandere.photogallery.service.GeoPositionService
import com.jemandandere.photogallery.service.MediaService
import com.jemandandere.photogallery.util.Constants
import com.jemandandere.photogallery.util.Utils

class GeoPositionFragment : Fragment(R.layout.geo_position_fragment),
    SharedPreferences.OnSharedPreferenceChangeListener {

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
        receiver = Receiver()

        binding.geoPositionOnoffButton.setOnClickListener {
            if (Utils.requestingLocationUpdates(requireContext())) {
                geoService!!.removeLocationUpdates()
                requireActivity().stopService(Intent(requireContext(), MediaService::class.java))
            } else {
                if (!checkPermissions()) {
                    requestPermissions()
                } else {
                    geoService!!.requestLocationUpdates()
                    requireActivity().startService(Intent(requireContext(), MediaService::class.java))
                }
            }
        }
        updateButtonsState(Utils.requestingLocationUpdates(requireContext()))
    }

    override fun onStart() {
        super.onStart()
        serviceBind()
    }

    override fun onStop() {
        serviceUnbind()
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
            AlertDialog.Builder(requireActivity()).setTitle(getString(R.string.permission_title))
                .setMessage(getString(R.string.permission_message))
                .setPositiveButton(getString(R.string.permission_ok)) { dialog, _ ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constants.REQUEST_PERMISSIONS_REQUEST_CODE
                    )
                }.create().show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    geoService!!.requestLocationUpdates()
                }
                else -> {
                    updateButtonsState(false)
                }
            }
        }
    }

    private fun serviceBind() {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)
        requireActivity().bindService(
            Intent(requireContext(), GeoPositionService::class.java), serviceConnection,
            Context.BIND_AUTO_CREATE
        )
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver!!,
            IntentFilter(Constants.GEO_SERVICE_MESSAGE_BROADCAST)
        )
    }

    private fun serviceUnbind() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver!!)
        if (bound) {
            requireActivity().unbindService(serviceConnection)
            bound = false
        }
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        if (s == Constants.REQUESTING_LOCATION_UPDATES_KEY) {
            updateButtonsState(
                sharedPreferences.getBoolean(
                    Constants.REQUESTING_LOCATION_UPDATES_KEY,
                    false
                )
            )
        }
    }

    private fun updateButtonsState(requestingLocationUpdates: Boolean) {
        if (requestingLocationUpdates) {
            binding.geoPositionOnoffButton.text = requireContext().getString(R.string.geo_position_stop)
        } else {
            binding.geoPositionOnoffButton.text = requireContext().getString(R.string.geo_position_start)
        }
    }

    private inner class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val location: Location? =
                intent.getParcelableExtra(Constants.GEO_SERVICE_LOCATION_KEY)
            if (location != null) {
                binding.geoPositionPositionText.text = Utils.getLocationText(location)
            }
        }
    }
}