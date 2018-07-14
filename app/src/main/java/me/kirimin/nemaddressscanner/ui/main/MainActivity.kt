package me.kirimin.nemaddressscanner.ui.main


import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.journeyapps.barcodescanner.CaptureActivity
import me.kirimin.nemaddressscanner.R
import me.kirimin.nemaddressscanner.ui.detail.AddressDetailView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mBtnCamera = findViewById<Button>(R.id.camera_button)
        mBtnCamera.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.captureActivity = CaptureActivity::class.java
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }

        val goButton = findViewById<Button>(R.id.goAddress)
        goButton.setOnClickListener {
            startActivity(AddressDetailView.newIntent(this, findViewById<EditText>(R.id.enterAddress).text.toString()))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (scanResult.contents != null) {
            Log.d("test", scanResult.contents)
            startActivity(AddressDetailView.newIntent(this, scanResult.contents))
        }
    }
}
