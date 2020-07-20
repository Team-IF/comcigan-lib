package io.teamif.patrick.comcigan

class ComciganSearchObject {
    data class ComciganSearchElement(val schools: Array<ComciganSchoolElement>) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ComciganSearchElement

            if (!schools.contentEquals(other.schools)) return false

            return true
        }

        override fun hashCode(): Int {
            return schools.contentHashCode()
        }
    }

    data class ComciganSchoolElement(val elements: Array<Any>) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ComciganSchoolElement

            if (!elements.contentEquals(other.elements)) return false

            return true
        }

        override fun hashCode(): Int {
            return elements.contentHashCode()
        }
    }
}