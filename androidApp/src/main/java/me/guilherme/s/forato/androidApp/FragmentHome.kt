package me.guilherme.s.forato.androidApp

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import me.guilherme.s.forato.androidApp.Adapter.Horizontal_Recycler
import me.guilherme.s.forato.androidApp.databinding.FragmentHomeBinding
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import me.guilherme.s.forato.androidApp.util.Util
import vm.GenericVm
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class FragmentHome : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterView: Horizontal_Recycler
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private var generic = GenericVm()
    private var util = Util()
    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerHorizontal
        adapterView = Horizontal_Recycler(arrayListOf())

        with(recyclerView){
            adapter = adapterView
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        val timeConvert = formatted.split(" ")

        job1 = lifecycleScope.launchWhenStarted {
            generic.uiStateFlow.collect { uiState ->
                when (uiState) {
                    GenericVm.UiState.Success -> {
                        binding.run {
                            viewFlipper.displayedChild = 1
                            progressBar.isVisible = false
                            dataCurrent.text = "${timeConvert[0]}\n${timeConvert[1]}"
                        }
                    }
                    GenericVm.UiState.Loading -> {
                        binding.run {
                            viewFlipper.displayedChild = 0
                            progressBar.isVisible = true
                        }
                    }
                }
            }
        }

        job2 = lifecycleScope.launchWhenStarted {
            generic.results.collect {
                binding.testando.text = it.name
                if (it.weather.count() > 0) {
                    binding.txtInfo.text = it.weather[0].description
                    binding.imageLogo.setImageResource(Util().setImage(it.weather[0].description))
                }
                binding.txtTemp.text = "${util.convertCelsius(it.main.temp)}°C"
                binding.txtHumidity.text = "${it.main.humidity}%"
            }
        }

        job3 = lifecycleScope.launchWhenStarted {
            generic.daily.collect {
                if (it.daily.count() > 0) adapterView.getList(it.daily)
            }
        }
        openLocation()
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE)
    fun openLocation() {
        val param: String = Manifest.permission.ACCESS_FINE_LOCATION
        if (EasyPermissions.hasPermissions(binding.root.context, param)) {
            getDeviceLocation()
        } else {
            EasyPermissions.requestPermissions(
                this, "We need permission because this and that",
                Constants.REQUEST_CODE, param
            )
        }
    }

    private fun getDeviceLocation() {
        try {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    generic.getApi(location.latitude, location.longitude)
                    generic.getApiWeek(location.latitude, location.longitude)
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        println("PermissionsGranted!!!")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        println("PermissionsDenied!!!")
    }

    override fun onStop() {
        job1?.cancel()
        job2?.cancel()
        job3?.cancel()
        super.onStop()
    }
}