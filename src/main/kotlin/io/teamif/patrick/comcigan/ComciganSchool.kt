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

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import io.teamif.patrick.comcigan.ComciganAPI.openURL
import java.io.StringReader
import java.net.URLEncoder
import java.util.Base64

/**
 * A class representing comcigan school
 *
 * @param name a keyword to search
 * @throws NoSuchElementException when no search results found
 * @throws IllegalArgumentException when more than one results found
 * @see [ComciganAPI.newSchool]
 *
 * @author PatrickKR
 */
class ComciganSchool internal constructor(name: String) {
    val schoolName: String
    val schoolCode: String
    private val schoolUrls: List<String>
    lateinit var schoolData: SchoolRawData
        private set

    init {
        val json = "${ComciganAPI.SEARCH_URL}${URLEncoder.encode(name, ComciganAPI.CHARSET)}"
                .jsonFromURL.getAsJsonArray("학교검색")

        when (json.count()) {
            0 -> throw NoSuchElementException("No schools have been searched by the name passed.")
            1 -> json.first().asJsonArray
            else -> throw IllegalArgumentException("More than one school is searched by the name passed.")
        }.let { array ->
            schoolName = array[2].asString
            schoolCode = array[3].asString
            schoolUrls = listOf(schoolWeekURL(0), schoolWeekURL(1))

            refresh()
        }
    }

    /**
     * Refreshes the data by parsing the Comcigan Website
     */
    fun refresh() {
        schoolData = SchoolRawData(schoolUrls.map { url ->
            url.jsonFromURL.asJsonObject.run {
                val shortSubjects = getAsJsonArray(ComciganAPI.SUBJECT_ID).mapNotNull { it.asString }
                val longSubjects = getAsJsonArray("긴${ComciganAPI.SUBJECT_ID}").mapNotNull { it.asString }
                val teachers = getAsJsonArray(ComciganAPI.TEACHER_ID).mapNotNull { it.asString }

                val grades = getAsJsonArray(ComciganAPI.DAILY_ID).mapNotNull { it.asJsonArray }.drop(1).filter { it.count() > 0 }

                SchoolWeekData(grades.map { grade ->
                    val classrooms = grade.mapNotNull { it.asJsonArray }.drop(1)

                    SchoolGradeData(classrooms.map { classroom ->
                        val days = classroom.mapNotNull { it.asJsonArray }.drop(1)

                        SchoolClassroomData(days.map { day ->
                            val codes = day.mapNotNull { it.asString }.drop(1).dropLastWhile { it == "0" }

                            SchoolDayData(codes.map { code ->
                                when (code) {
                                    "0" -> SchoolPeriodData.NULL
                                    else -> {
                                        val subject = code.takeLast(2).toInt()
                                        val teacher = code.dropLast(2).toInt()

                                        SchoolPeriodData(
                                                shortSubjects[subject],
                                                longSubjects[subject],
                                                teachers[teacher]
                                        )
                                    }
                                }
                            })
                        })
                    })
                })
            }
        })
    }

    private fun schoolWeekURL(index: Int): String {
        val byteArray = "${ComciganAPI.PREFIX}${schoolCode}_0_$index".toByteArray()

        return "${ComciganAPI.BASE_URL}?${Base64.getUrlEncoder().encodeToString(byteArray)}".dropLastWhile {
            it == '='
        }
    }

    private val String.jsonFromURL: JsonObject
        get() = requireNotNull(
                JsonParser.parseReader(JsonReader(StringReader(openURL(Charsets.UTF_8))).apply {
                    isLenient = true
                })
        ) as JsonObject
}