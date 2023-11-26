package co.sirano.news.common.util

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowInsets
import android.view.WindowManager
import kotlin.math.roundToInt

// 스크린 가로 길이를 얻어오기 위한 함수
fun Context.getScreenWidth(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun Context.getPxToDp(px: Float): Int {
    val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, resources.displayMetrics)
    return dp.roundToInt()
}

fun Context.getDpToPx(dp: Float): Int {
    val resultPix =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    return resultPix.roundToInt()
}
