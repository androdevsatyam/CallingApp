package com.androdevsatya.callingapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androdevsatya.callingapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val REQUEST_SET_DEFAULT_DIALER = 101
    var allow: Boolean = false
    var defaultDialer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callClicks()
    }


    override fun onResume() {
        super.onResume()
        checkLauncher()
    }

    private fun checkLauncher() {
        GlobalScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getSystemService(TelecomManager::class.java).defaultDialerPackage
                    == packageName
                ) {
                    defaultDialer = true
                }
                val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        packageName
                    )
                startActivityForResult(intent, REQUEST_SET_DEFAULT_DIALER)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SET_DEFAULT_DIALER) {
            if (resultCode != RESULT_OK) {
                // The user did not grant permission to make this app the default dialer app.
                // Handle this as appropriate for your app.
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initiateCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    private fun callClicks() {
        binding.call.setOnClickListener {
            if (allow) {
                if (defaultDialer) {
                    if (binding.number.text.toString().isNotEmpty())
                        initiateCall(binding.number.text.toString())
                } else {
                    val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                        .putExtra(
                            TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                            packageName
                        )
                    startActivityForResult(intent, REQUEST_SET_DEFAULT_DIALER)
                    Toast.makeText(this, "Make default dialer", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(this, "Allow permission to call", Toast.LENGTH_SHORT).show()

        }

        binding.del.setOnClickListener {
            if (binding.number.text.toString().isNotEmpty())
                binding.number.text = getEditable(
                    binding.number.text.toString()
                        .substring(0, binding.number.text.toString().length - 1)
                )
        }
        binding.key1.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "1")
        }
        binding.key2.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "2")
        }
        binding.key3.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "3")
        }
        binding.key4.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "4")
        }
        binding.key5.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "5")
        }
        binding.key6.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "6")
        }
        binding.key7.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "7")
        }
        binding.key8.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "8")
        }
        binding.key9.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "9")
        }
        binding.keystar.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "*")
        }
        binding.key0.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "0")
        }
        binding.keyhas.setOnClickListener {
            binding.number.text = getEditable(binding.number.text.toString() + "#")
        }
    }

    fun getEditable(keyPress: String): Editable {
        return (Editable.Factory.getInstance().newEditable(keyPress))
    }
}