@file:Suppress("unused")

package com.jesusrojo.mvvmdemo.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.jesusrojo.mvvmdemo.R
import java.text.NumberFormat


fun Activity.showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.cancel) { dialog, _ -> dialog?.dismiss() }
        builder.create().show()
    }



fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager = ContextCompat.getSystemService(this,
            InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}


//ENUM
val <T> T.exhaustive: T
    get() = this


//INTENT, USE IN ACTIVITY: startMyActivity<MainActivity>()
inline fun <reified T: Activity> Activity.startMyActivity() {
    try {
        startActivity( Intent(this, T::class.java))
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

//inline fun <reified T: Activity> Context.createMyIntent() =
//        Intent(this, T::class.java)


//EXTENSIONS OR UTIL?
fun formatNumber(number: Int?): String{
    var result = ""
    number?.let{
        val numberFormat = NumberFormat.getInstance()
        result = numberFormat.format(number)
    }
    return result
}
//fun convertTitle(title: String?) =
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
//        } else {
//            Html.fromHtml(title).toString()
//        }
//
//
//fun convertDate(timeStamp: Long?): String{
//    var time = ""
//    timeStamp?.let{
//        val cal = Calendar.getInstance()
//        cal.timeInMillis= timeStamp * 1000
//        time = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString()
//    }
//    return time
//}

//////////////////////////////////////////////////////
//TOAST CONTEXT
//fun Context.toast(message: CharSequence) {
//    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//}
//fun Context.toast(@StringRes message: Int){
//    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
//}
//
//TOAST ACTIVITY
//fun Activity.toast(message: String){
//    Toast.makeText(this, message , Toast.LENGTH_SHORT).show();
//}
//fun Activity.toast(@StringRes message: Int){
//    Toast.makeText(this, message , Toast.LENGTH_SHORT).show();
//}
//
//
//STRINGS
//fun String.upperCaseFirstLetter(): String {
//    return this.substring(0, 1).toUpperCase().plus(this.substring(1))
//}
//
//
////RESOURCES
//@ColorInt
//fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
//    return ContextCompat.getColor(this, colorRes)
//}
//
//fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable {
//    return AppCompatResources.getDrawable(this, drawableRes)!!
//}
//
//@CheckResult
//fun Drawable.tint(@ColorInt color: Int): Drawable {
//    val tintedDrawable = DrawableCompat.wrap(this).mutate()
//    DrawableCompat.setTint(tintedDrawable, color)
//    return tintedDrawable
//}
//
//@CheckResult fun Drawable.tint(context: Context, @ColorRes color: Int): Drawable {
//    return tint(context.getColorCompat(color))
//}
//
//@JvmOverloads
//@Dimension(unit = Dimension.PX) fun Number.dpToPx(
//        metrics: DisplayMetrics = Resources.getSystem().displayMetrics
//): Float {
//    return toFloat() * metrics.density
//}
//
//@JvmOverloads
//@Dimension(unit = Dimension.DP) fun Number.pxToDp(
//        metrics: DisplayMetrics = Resources.getSystem().displayMetrics
//): Float {
//    return toFloat() / metrics.density
//}
//
//
////RANDOM
//val randomNumber = (0..100).random() // generated random from 0 to 100 included
//fun getRandomNumber(toNumber: Int) = (0..toNumber).random()
/////////////////////////
