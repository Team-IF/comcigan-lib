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

import java.util.regex.Pattern

/**
 * Regex handler for Comcigan API.
 * Provides regex used for api, and some functions
 * for the ease of use.
 *
 * @author PatrickKR
 */
object ComciganRegex {
    @JvmStatic
    val SCRIPT_REGEX = "(?<=<script language='JavaScript' type='text\\/JavaScript'>).*?(?=<\\/script>)".toPattern(8)
    @JvmStatic
    val ROUTE_REGEX = "\\./\\d{6}\\?\\d{5}l".toPattern()
    @JvmStatic
    val PREFIX_REGEX = "(?<=')\\d+_(?=)(?=')".toPattern()
    @JvmStatic
    val ORIGINAL_ID_REGEX = "(?<=원자료=자료\\.)자료\\d+".toPattern()
    @JvmStatic
    val DAILY_ID_REGEX = "(?<=일일자료=자료\\.)자료\\d+".toPattern()
    @JvmStatic
    val TEACHER_ID_REGEX = "(?<=성명=자료\\.)자료\\d+".toPattern()
    @JvmStatic
    val SUBJECT_ID_REGEX = "(?<=자료\\.)자료\\d+(?=\\[sb])".toPattern()

    /**
     * Returns the first matching group
     *
     * @param string the input for regex matching
     * @return matched string. null when no matching group found.
     */
    @JvmStatic
    fun Pattern.search(string: String): String? {
        val matcher = matcher(string)

        return if (matcher.find()) matcher.group()
        else null
    }
}