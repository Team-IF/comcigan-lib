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

/**
 * A collection of data classes for [ComciganSchool]
 *
 * @author PatrickKR
 */
@Suppress("PropertyName", "unused", "MemberVisibilityCanBePrivate")
class SchoolData private constructor() {
    /**
     * A raw school data.
     * Contains all information.
     */
    class SchoolRawData(private val data: List<SchoolWeekData>) {
        val THIS_WEEK = data[0]
        val NEXT_WEEK = data[1]

        fun week(number: Int): SchoolWeekData {
            if (number < 1) throw IllegalArgumentException("Week number cannot be less than 1 (This Week).")
            if (number > 2) throw IllegalArgumentException("Week number cannot exceed 2 (Next Week).")
            return data[number - 1]
        }

        override fun toString(): String {
            return data.toString()
        }
    }

    /**
     * A school week data.
     * Contains all information in the week.
     */
    class SchoolWeekData(private val data: List<SchoolGradeData>) {
        fun grade(number: Int): SchoolGradeData {
            if (number < 1) throw IllegalArgumentException("Grade number cannot be less than 1.")
            if (number > data.count()) throw IllegalArgumentException("Grade number cannot exceed total.")
            return data[number - 1]
        }

        override fun toString(): String {
            return data.toString()
        }
    }

    /**
     * A school grade data.
     * Contains all information in the week for this grade.
     */
    class SchoolGradeData(private val data: List<SchoolClassroomData>) {
        fun classroom(number: Int): SchoolClassroomData {
            if (number < 1) throw IllegalArgumentException("Classroom number cannot be less than 1.")
            if (number > data.count()) throw IllegalArgumentException("Classroom number cannot exceed total.")
            return data[number - 1]
        }

        override fun toString(): String {
            return data.toString()
        }
    }

    /**
     * A school classroom data.
     * Contains all information in the week for this classroom.
     */
    class SchoolClassroomData(private val data: List<SchoolDayData>) {
        val MONDAY = data[0]
        val TUESDAY = data[1]
        val WEDNESDAY = data[2]
        val THURSDAY = data[3]
        val FRIDAY = data[4]
        val SATURDAY = data[5]

        fun day(number: Int): SchoolDayData {
            if (number < 1) throw IllegalArgumentException("Day number cannot be less than 1 (Monday).")
            if (number > 6) throw IllegalArgumentException("Day number cannot exceed 6 (Saturday).")
            return data[number - 1]
        }

        override fun toString(): String {
            return data.toString()
        }
    }

    /**
     * A school day data.
     * Contains all information in this day for the classroom.
     */
    class SchoolDayData(private val data: List<SchoolPeriodData>) {
        fun period(number: Int): SchoolPeriodData {
            if (number < 1) throw IllegalArgumentException("Period number cannot be less than 1.")
            if (number > data.count()) throw IllegalArgumentException("Period number cannot exceed total.")
            return data[number - 1]
        }

        override fun toString(): String {
            return data.toString()
        }
    }

    /**
     * A school period data.
     * Contains all information in this period for the classroom.
     */
    class SchoolPeriodData(val SUBJECT: String, val FULL_SUBJECT: String, val TEACHER: String) {
        override fun toString(): String {
            return "{\"SUBJECT\": \"$SUBJECT\", \"FULL_SUBJECT\": \"$FULL_SUBJECT\", \"TEACHER\": \"$TEACHER\"}"
        }
    }
}