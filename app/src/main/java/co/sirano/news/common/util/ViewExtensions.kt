package co.sirano.news.common.util

import android.view.View

// 연속 클릭으로 인한 화면 이동 겹침 문제 등을 해결하기 위해서 interval 딜레이 를 줌
fun View.onClick(interval: Long = 800L, action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener, interval))
}

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 800L
) : View.OnClickListener {
    private var clickable = true

    override fun onClick(p0: View?) {
        if (clickable) {
            clickable = false
            clickListener.onClick(p0)
            p0?.run {
                postDelayed({
                    clickable = true
                }, interval)
            }
        }
    }
}
