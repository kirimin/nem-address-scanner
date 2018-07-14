package me.kirimin.nemaddressscanner.domain.util

import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.WriterException
import java.util.*


object ZxingUtil {

    @Throws(WriterException::class)
    fun createQRCodeByZxing(contents: String, size: Int): Bitmap {
        //QRコードをエンコードするクラス
        val writer = QRCodeWriter()

        //異なる型の値を入れるためgenericは使えない
        val encodeHint = Hashtable<EncodeHintType, Any>()

        //日本語を扱うためにシフトJISを指定
        encodeHint.put(EncodeHintType.CHARACTER_SET, "shiftjis")

        //エラー修復レベルを指定
        //L 7%が復元可能
        //M 15%が復元可能
        //Q 25%が復元可能
        //H 30%が復元可能
        encodeHint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L)

        val qrCodeData = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, encodeHint)

        //QRコードのbitmap画像を作成
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.argb(255, 255, 255, 255)) //いらないかも
        for (x in 0 until qrCodeData.width) {
            for (y in 0 until qrCodeData.height) {
                if (qrCodeData.get(x, y)) {
                    //0はBlack
                    bitmap.setPixel(x, y, Color.argb(255, 0, 0, 0))
                } else {
                    //-1はWhite
                    bitmap.setPixel(x, y, Color.argb(255, 255, 255, 255))
                }
            }
        }

        return bitmap
    }
}