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

@file:Suppress("unused", "PropertyName")

package io.teamif.patrick.comcigan

/**
 * A raw school data.
 * Contains all information.
 */
class SchoolRawData internal constructor(private val data: List<SchoolWeekData>) : ListableData<SchoolWeekData>(data) {
    val THIS_WEEK = data[0]
    val NEXT_WEEK = data[1]

    fun week(number: Int): SchoolWeekData {
        if (number < 1) throw IllegalArgumentException("Week number cannot be less than 1 (This Week).")
        return data[number - 1]
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SchoolRawData) {
            return false
        }

        return data.toTypedArray() contentDeepEquals other.data.toTypedArray()
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

/**
 * A school week data.
 * Contains all information in the week.
 */
class SchoolWeekData internal constructor(private val data: List<SchoolGradeData>) : ListableData<SchoolGradeData>(data) {
    fun grade(number: Int): SchoolGradeData {
        if (number < 1) throw IllegalArgumentException("Grade number cannot be less than 1.")
        if (number > data.count()) throw IllegalArgumentException("Grade number cannot exceed total.")
        return data[number - 1]
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SchoolWeekData) {
            return false
        }

        return data.toTypedArray() contentDeepEquals other.data.toTypedArray()
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

/**
 * A school grade data.
 * Contains all information in the week for this grade.
 */
class SchoolGradeData internal constructor(private val data: List<SchoolClassroomData>) : ListableData<SchoolClassroomData>(data) {
    fun classroom(number: Int): SchoolClassroomData {
        if (number < 1) throw IllegalArgumentException("Classroom number cannot be less than 1.")
        if (number > data.count()) throw IllegalArgumentException("Classroom number cannot exceed total.")
        return data[number - 1]
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SchoolGradeData) {
            return false
        }

        return data.toTypedArray() contentDeepEquals other.data.toTypedArray()
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

/**
 * A school classroom data.
 * Contains all information in the week for this classroom.
 */
class SchoolClassroomData internal constructor(private val data: List<SchoolDayData>) : ListableData<SchoolDayData>(data) {
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

    override fun equals(other: Any?): Boolean {
        if (other !is SchoolClassroomData) {
            return false
        }

        return data.toTypedArray() contentDeepEquals other.data.toTypedArray()
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

/**
 * A school day data.
 * Contains all information in this day for the classroom.
 */
class SchoolDayData internal constructor(private val data: List<SchoolPeriodData>) : ListableData<SchoolPeriodData>(data) {
    fun period(number: Int): SchoolPeriodData {
        if (number < 1) throw IllegalArgumentException("Period number cannot be less than 1.")
        if (number > data.count()) throw IllegalArgumentException("Period number cannot exceed total.")
        return data[number - 1]
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SchoolDayData) {
            return false
        }

        return data.toTypedArray() contentDeepEquals other.data.toTypedArray()
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

/**
 * A school period data.
 * Contains all information in this period for the classroom.
 *
 * @param subject       subject name (2-letter abbr)
 * @param fullSubject   subject name (full)
 * @param teacher       teacher name
 */
class SchoolPeriodData internal constructor(val subject: String, val fullSubject: String, val teacher: String) {
    internal companion object {
        internal val NULL = SchoolPeriodData("", "", "")
    }

    override fun toString(): String {
        return "{\"SUBJECT\": \"$subject\", \"FULL_SUBJECT\": \"$fullSubject\", \"TEACHER\": \"$teacher\"}"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SchoolPeriodData) {
            return false
        }

        return subject == other.subject && fullSubject == other.fullSubject && teacher == other.teacher
    }

    override fun hashCode(): Int {
        var result = subject.hashCode()
        result = 31 * result + fullSubject.hashCode()
        result = 31 * result + teacher.hashCode()
        return result
    }
}

open class ListableData<T> internal constructor(private val data: List<T>) : List<T> {
    override val size: Int
        get() = data.size

    override fun contains(element: T): Boolean {
        return data.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return data.containsAll(elements)
    }

    override fun indexOf(element: T): Int {
        return data.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return data.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return data.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<T> {
        return data.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<T> {
        return data.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        return data.subList(fromIndex, toIndex)
    }

    override fun get(index: Int): T {
        return data[index]
    }
}