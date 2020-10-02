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

import java.net.URL
import java.nio.charset.Charset
import java.util.regex.Pattern
import kotlin.jvm.Throws

/**
 * A Comcigan API wrapper written in Kotlin
 *
 * @author PatrickKR
 */
object ComciganAPI {
    @JvmStatic
    private val ROOT_URL = "http://comci.kr:4082"

    @JvmStatic
    internal val CHARSET = "EUC-KR"

    @JvmStatic
    private val SCRIPT: String

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
        SCRIPT = SCRIPT_REGEX.search(("$ROOT_URL/st").open())
        ROUTE = ROUTE_REGEX.search(SCRIPT)
        PREFIX = PREFIX_REGEX.search(SCRIPT)
        ORIGINAL_ID = ORIGINAL_ID_REGEX.search(SCRIPT)
        DAILY_ID = DAILY_ID_REGEX.search(SCRIPT)
        TEACHER_ID = TEACHER_ID_REGEX.search(SCRIPT)
        SUBJECT_ID = SUBJECT_ID_REGEX.search(SCRIPT)
        BASE_URL = "$ROOT_URL${ROUTE.substring(1..8)}"
        SEARCH_URL = "$BASE_URL${ROUTE.substring(8)}"
    }

    internal fun String.open(charset: Charset = Charset.forName(CHARSET)): String =
            URL(this).readText(charset)

    /**
     * Returns the first matching group
     *
     * @param string the input for regex matching
     * @return matched string. null when no matching group found.
     */
    @JvmStatic
    private fun Pattern.search(string: String): String =
            with(matcher(string)) {
                when {
                    find() -> group()
                    else -> throw NoSuchElementException("Regex not found")
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
    fun newSchool(name: String): ComciganSchool = ComciganSchool(name)
}