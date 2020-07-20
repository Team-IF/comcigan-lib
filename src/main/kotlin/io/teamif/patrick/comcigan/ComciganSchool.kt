package io.teamif.patrick.comcigan

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList

class ComciganSchool(name: String) {
    private val schoolName: String
    private val schoolCode: String
    private val schoolUrl: String
    private val schoolData: ArrayList<ArrayList<ArrayList<ArrayList<ComciganTuple>>>>
    init {
        (URL("${ComciganAPI.SEARCH_URL}${URLEncoder.encode(name, ComciganAPI.CHARSET)}").openConnection() as HttpURLConnection).run {
            InputStreamReader(inputStream).use {
                BufferedReader(it).use { reader ->
                    ComciganAPI.GSON.fromJson(reader.readLines().joinToString(), ComciganSearchObject.ComciganSearchElement::class.java).run {
                        when (schools.count()) {
                            0 -> throw NoSuchElementException("No schools have been searched by the name passed.")
                            1 -> schools.first()
                            else -> throw IllegalArgumentException("More than one school is searched by the name passed.")
                        }.run {
                            schoolName = elements[2] as String
                            schoolCode = elements[3] as String
                            schoolUrl = "${ComciganAPI.BASE_URL}?${Base64.getUrlEncoder().encode("${ComciganAPI.PREFIX}${schoolCode}_0_1".toByteArray()).decodeToString()}"
                            schoolData = arrayListOf(arrayListOf(arrayListOf(arrayListOf(ComciganTuple("", "", "")))))

                            refresh()
                        }
                    }
                }
            }
        }
    }

    private fun refresh() {
        (URL(schoolUrl).openConnection() as HttpURLConnection).run {
            InputStreamReader(inputStream).use {
                BufferedReader(it).use { reader ->
                    TODO("use $reader")
                }
            }
        }
    }
}