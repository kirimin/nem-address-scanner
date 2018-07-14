package me.kirimin.nemaddressscanner.ui.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.kirimin.nemaddressscanner.R
import me.kirimin.nemaddressscanner.databinding.ActivityAddressDetailBinding
import android.content.ClipData
import android.content.ClipboardManager
import android.view.MenuItem
import android.widget.Toast


interface AddressDetailView {

    class AddressDetailActivity : AppCompatActivity(), AddressDetailView {

        lateinit var viewModel: AddressDetailViewModel
        lateinit var binding: ActivityAddressDetailBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_address_detail)

            this.binding = DataBindingUtil.setContentView<ActivityAddressDetailBinding>(this, R.layout.activity_address_detail)
            viewModel = AddressDetailViewModel()
            binding.viewmodel = viewModel
            viewModel.onCreate(this, intent.getStringExtra(EXTRA_ADDRESS))
        }

        override fun onDestroy() {
            viewModel.onDestroy()
            super.onDestroy()
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (android.R.id.home == item.itemId) {
                finish()
            }
            return super.onOptionsItemSelected(item)
        }

        override fun setQrImage(qr: Bitmap) {
            binding.qrcode.setImageBitmap(qr)
        }

        override fun copyToClipboard(address: String) {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager ?: return
            clipboardManager.primaryClip = ClipData.newPlainText("", address)
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        override fun initToolbar(address: String) {
            setSupportActionBar(binding.toolbar)
            val actionBar = supportActionBar ?: return
            actionBar.title = address
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        override fun showError() {
            Toast.makeText(this, "Oops! Something wrong!", Toast.LENGTH_SHORT).show()
        }

        override fun finishView() {
            finish()
        }
    }

    companion object {

        const val EXTRA_ADDRESS = "extra_address"

        fun newIntent(context: Context, address: String): Intent {
            val intent = Intent(context, AddressDetailActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS, address)
            return intent
        }
    }

    fun setQrImage(qr: Bitmap)
    fun copyToClipboard(address: String)
    fun initToolbar(address: String)
    fun showError()
    fun finishView()
}