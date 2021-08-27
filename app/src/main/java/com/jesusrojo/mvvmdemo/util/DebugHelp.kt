package com.jesusrojo.mvvmdemo.util

import timber.log.Timber

// todo DEBUG
class DebugHelp {

    companion object{
        private const val jrTag = "##"
        var fullLog = ""

        fun l(classSimpleName: String, message: String) {
            fullLog += "\n$message"
            l("$classSimpleName $message $jrTag")
        }

        fun l(message: String) {
            fullLog += "\n$message"
            Timber.d("$message $jrTag")
        }

        fun le(message: String) {
            fullLog += "\n$message"
            Timber.e("$message $jrTag")
        }

//        fun lt(message: String) {
//            fullLog += "\n$message"
//            Timber.d("$message THREAD: ${Thread.currentThread().name} $jrTag")
//        }
    }
}