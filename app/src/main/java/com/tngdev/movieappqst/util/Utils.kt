package com.tngdev.movieappqst.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object Utils {
    fun readFromAsssets(fileName: String, context: Context): String? {
        val returnString = StringBuilder()
        var fIn: InputStream? = null
        var isr: InputStreamReader? = null
        var input: BufferedReader? = null
        try {
            fIn = context.resources.assets
                .open(fileName)
            isr = InputStreamReader(fIn)
            input = BufferedReader(isr)
            var line: String? = ""
            while (input.readLine().also { line = it } != null) {
                returnString.append(line)
            }
        } catch (e: Exception) {
            e.message
        } finally {
            try {
                isr?.close()
                fIn?.close()
                input?.close()
            } catch (e2: Exception) {
                e2.message
            }
        }
        return returnString.toString()
    }
}