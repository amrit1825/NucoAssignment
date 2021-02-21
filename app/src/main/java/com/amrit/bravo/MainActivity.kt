package com.amrit.bravo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrit.bravo.databinding.ActivityMainBinding
import com.amrit.bravo.model.Message

const val PERMISSION = Manifest.permission.RECEIVE_SMS
const val SMS_PERMISSION_CODE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        init()
        setViewObservers()
    }

    private fun setViewObservers() {
        mainViewModel.getMessageList().observe(this) { messageList: List<Message> ->
            setUi(
                messageList
            )
        }
    }

    private fun init() {
        checkPermission()
        mainViewModel.getDataFromServer()
        activityMainBinding.messageList.layoutManager = LinearLayoutManager(this)
        activityMainBinding.messageList.isNestedScrollingEnabled = false
    }

    private fun setUi(dataList: List<Message>) {
        if (dataList.isNotEmpty()) {
            activityMainBinding.title.text = resources.getString(R.string.txt_round_ups)
        } else {
            activityMainBinding.title.text = resources.getString(R.string.txt_no_round_ups)
        }
        if (activityMainBinding.messageList.adapter == null) {
            // first time create new adapter
            activityMainBinding.messageList.adapter = MessageAdapter(dataList)
        } else {
            // other times reuse old adapter and update data
            val adapter = (activityMainBinding.messageList.adapter as MessageAdapter)
            adapter.updateMessageList(dataList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                PERMISSION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, R.string.txt_already_granted, Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                PERMISSION
            )
        ) {
            Toast.makeText(this, R.string.txt_need_permission, Toast.LENGTH_SHORT).show()
        }
        ActivityCompat.requestPermissions(this, arrayOf(PERMISSION), SMS_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == SMS_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.txt_thank_you, Toast.LENGTH_SHORT).show()
        } else {
            // closing app if permission is not granted
            Toast.makeText(this, R.string.txt_work, Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                onBackPressed()
            }, 2000)
        }
    }

}