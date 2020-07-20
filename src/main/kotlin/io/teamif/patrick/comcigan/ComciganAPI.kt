package io.teamif.patrick.comcigan

import com.google.gson.Gson
import io.teamif.patrick.comcigan.ComciganRegex.search
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * A Comcigan API wrapper written in Kotlin
 */
class ComciganAPI {
    companion object {
        @JvmStatic
        internal val GSON = Gson()
        @JvmStatic
        internal val CHARSET = "EUC-KR"
        @JvmStatic
        private val ROUTE: String
        @JvmStatic
        internal val PREFIX: String
        @JvmStatic
        private val ORIGINAL_ID: Int
        @JvmStatic
        private val DAILY_ID: Int
        @JvmStatic
        private val TEACHER_ID: Int
        @JvmStatic
        private val SUBJECT_ID: Int
        @JvmStatic
        internal val BASE_URL: String
        @JvmStatic
        internal val SEARCH_URL: String
        init {
            val root = "http://comci.kr:4082"
            (URL("$root/st").openConnection() as HttpURLConnection).run {
                InputStreamReader(inputStream).use {
                    BufferedReader(it).use { reader ->
                        val script = requireNotNull(
                            ComciganRegex.SCRIPT_REGEX.search(reader.readLines().joinToString(System.lineSeparator()))
                        )
                        ROUTE = requireNotNull(ComciganRegex.ROUTE_REGEX.search(script))
                        PREFIX = requireNotNull(ComciganRegex.PREFIX_REGEX.search(script))
                        ORIGINAL_ID = requireNotNull(ComciganRegex.ORIGINAL_ID_REGEX.search(script)?.toIntOrNull())
                        DAILY_ID = requireNotNull(ComciganRegex.DAILY_ID_REGEX.search(script)?.toIntOrNull())
                        TEACHER_ID = requireNotNull(ComciganRegex.TEACHER_ID_REGEX.search(script)?.toIntOrNull())
                        SUBJECT_ID = requireNotNull(ComciganRegex.SUBJECT_ID_REGEX.search(script)?.toIntOrNull())
                        BASE_URL = "$root${ROUTE.substring(1..8)}"
                        SEARCH_URL = "$BASE_URL${ROUTE.substring(8)})"
                    }
                }
            }
        }
    }
}