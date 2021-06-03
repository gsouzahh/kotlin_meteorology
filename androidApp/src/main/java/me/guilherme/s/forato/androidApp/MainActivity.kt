package me.guilherme.s.forato.androidApp

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import api.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import me.guilherme.s.forato.androidApp.databinding.ActivityMainBinding
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import vm.GenericVm
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var generic = GenericVm()
    private var job1: Job? = null
    private var job2: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @SuppressLint("SetTextI18n", "VisibleForTests")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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
                    Picasso.get()
                        .load("https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png")
                        .into(binding.iconTemp);
                }
                binding.txtTemp.text = "${generic.convertCelsius(it.main.temp)}C°"
                binding.txtHumidity.text = "${generic.convertCelsius(it.main.humidity)}C°"
                binding.txtFeelsLike.text =
                    "Feels Like: ${generic.convertCelsius(it.main.feels_like)}C°"
            }
        }
        openLocation()
    }

    private fun getDeviceLocation() {
        try {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    generic.getApi(location.latitude, location.longitude)
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE)
    fun openLocation() {
        val param: String = Manifest.permission.ACCESS_FINE_LOCATION
        if (EasyPermissions.hasPermissions(applicationContext, param)) {
            Toast.makeText(applicationContext, "Opening Location", Toast.LENGTH_SHORT).show()
            getDeviceLocation()
        } else {
            EasyPermissions.requestPermissions(
                this, "We need permission because this and that",
                Constants.REQUEST_CODE, param
            )
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
        println("")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onStop() {
        job1?.cancel()
        job2?.cancel()
        super.onStop()
    }
}
