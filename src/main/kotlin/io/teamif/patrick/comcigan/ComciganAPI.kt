/*
 * Copyright (C) 2020 PatrickKR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact me on <mailpatrickkr@gmail.com>
 */

package io.teamif.patrick.comcigan

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import io.teamif.patrick.comcigan.ComciganRegex.search
import java.io.StringReader
import java.net.URL
import java.nio.charset.Charset
import kotlin.jvm.Throws

/**
 * A Comcigan API wrapper written in Kotlin
 *
 * @author PatrickKR
 */
class ComciganAPI private constructor() {
    companion object {
        /**
         * Gets [ComciganAPI] instance
         */
        @JvmStatic
        val INSTANCE: ComciganAPI
            get() {
                if (INTERNAL == null) {
                    INTERNAL = ComciganAPI()
                }
                return requireNotNull(INTERNAL)
            }
        @JvmStatic
        private var INTERNAL: ComciganAPI? = null
        @JvmStatic
        internal val CHARSET = "EUC-KR"
        @JvmStatic
        private val ROUTE: String
        @JvmStatic
        internal val PREFIX: String
        @JvmStatic
        private val ORIGINAL_ID: String
        @JvmStatic
        internal val DAILY_ID: String
        @JvmStatic
        internal val TEACHER_ID: String
        @JvmStatic
        internal val SUBJECT_ID: String
        @JvmStatic
        internal val BASE_URL: String
        @JvmStatic
        internal val SEARCH_URL: String

        init {
            val root = "http://comci.kr:4082"
            val script = requireNotNull(ComciganRegex.SCRIPT_REGEX.search(("$root/st").open()))
            ROUTE = requireNotNull(ComciganRegex.ROUTE_REGEX.search(script))
            PREFIX = requireNotNull(ComciganRegex.PREFIX_REGEX.search(script))
            ORIGINAL_ID = requireNotNull(ComciganRegex.ORIGINAL_ID_REGEX.search(script))
            DAILY_ID = requireNotNull(ComciganRegex.DAILY_ID_REGEX.search(script))
            TEACHER_ID = requireNotNull(ComciganRegex.TEACHER_ID_REGEX.search(script))
            SUBJECT_ID = requireNotNull(ComciganRegex.SUBJECT_ID_REGEX.search(script))
            BASE_URL = "$root${ROUTE.substring(1..8)}"
            SEARCH_URL = "$BASE_URL${ROUTE.substring(8)}"
        }

        internal val String.json: JsonElement
            get() {
                return requireNotNull(JsonParser.parseReader(JsonReader(StringReader(open(Charsets.UTF_8))).apply {
                    isLenient = true
                }))
            }

        private fun String.open(charset: Charset = Charset.forName(CHARSET)): String {
            return URL(this).readText(charset)
        }
    }

    /**
     * Creates a new ComciganSchool element by searching a keyword
     *
     * @param name a keyword to search
     * @throws NoSuchElementException when no search results found
     * @throws IllegalArgumentException when more than one results found
     * @see [ComciganSchool]
     */
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    fun newSchool(name: String): ComciganSchool {
        return ComciganSchool(name)
    }
}