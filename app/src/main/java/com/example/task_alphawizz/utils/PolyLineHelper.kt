package com.example.task_alphawizz.utils

import android.animation.ValueAnimator
import android.os.Handler
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline

object PolyLineHelper {
    public fun animatePolyLine(
        listLatLng: List<LatLng>,
        greayPolyLine: Polyline,
        blackPolyLine: Polyline
    ) {
        startAnimater(listLatLng, greayPolyLine)
        Handler().postDelayed({ startAnimater(listLatLng, blackPolyLine) }, 900)
    }

    private fun startAnimater(
        listLatLng: List<LatLng>,
        polyLine: Polyline
    ) {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 1000
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animator ->
            val latLngList = polyLine.points
            val initialPointSize = latLngList.size
            val animatedValue = animator.animatedValue as Int
            val newPoints = animatedValue * listLatLng.size / 100
            if (initialPointSize < newPoints) {
                latLngList.addAll(listLatLng.subList(initialPointSize, newPoints))
                polyLine.points = latLngList
            }
        }
        animator.start()
    }

    public fun decodePoly(encoded: String): List<LatLng>{
        val poly: MutableList<LatLng> = mutableListOf<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f) shl shift
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f) shl shift
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }
}