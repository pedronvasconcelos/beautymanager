package com.pedrovasconcelos.beautymanager.domain.shared

class PhoneNumber(val value: String) {
    companion object {
        fun createPhoneNumber(phone: String?): PhoneNumber? {
            return phone?.let {
                val numbers = phone.filter { it.isDigit() }
                if (phone.isBlank() || numbers.length < 8) {
                    throw PhoneException()
                }
                PhoneNumber(phone)
            }
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneNumber) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }
}

class PhoneException : ValidationException("Invalid phone number")