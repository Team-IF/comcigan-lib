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
 * Regex handler for Comcigan API.
 * Provides regex used for api, and some functions
 * for the ease of use.
 *
 * @author PatrickKR
 */

internal val SCRIPT_REGEX = "(?<=<script language='JavaScript' type='text\\/JavaScript'>).*?(?=<\\/script>)".toPattern(8)

internal val ROUTE_REGEX = "\\./\\d{6}\\?\\d{5}l".toPattern()

internal val PREFIX_REGEX = "(?<=')\\d+_(?=)(?=')".toPattern()

internal val ORIGINAL_ID_REGEX = "(?<=원자료=자료\\.)자료\\d+".toPattern()

internal val DAILY_ID_REGEX = "(?<=일일자료=자료\\.)자료\\d+".toPattern()

internal val TEACHER_ID_REGEX = "(?<=성명=자료\\.)자료\\d+".toPattern()

internal val SUBJECT_ID_REGEX = "(?<=자료\\.)자료\\d+(?=\\[sb])".toPattern()