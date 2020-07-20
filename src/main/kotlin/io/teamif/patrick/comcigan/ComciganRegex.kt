package io.teamif.patrick.comcigan

import java.util.regex.Pattern

/**
 * Regex handler for Comcigan API.
 * Provides regex used for api, and some functions
 * for the ease of use.
 *
 * @author PatrickKR
 */
internal object ComciganRegex {
    @JvmStatic
    val SCRIPT_REGEX = "(?<=<script language.*?).*?(?=<\\/script>)".toPattern()
    @JvmStatic
    val ROUTE_REGEX = "\\./\\d{6}\\?\\d{5}l".toPattern()
    @JvmStatic
    val PREFIX_REGEX = "(?<=')\\d+_(?=)'".toPattern()
    @JvmStatic
    val ORIGINAL_ID_REGEX = "(?<=원자료=자료\\.자료)\\d+".toPattern()
    @JvmStatic
    val DAILY_ID_REGEX = "(?<=일일자료=자료\\.자료)\\d+".toPattern()
    @JvmStatic
    val TEACHER_ID_REGEX = "(?<=성명=자료\\.자료\\)d+".toPattern()
    @JvmStatic
    val SUBJECT_ID_REGEX = "(?<=자료.자료)\\d+(?=\\[sb])".toPattern()

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