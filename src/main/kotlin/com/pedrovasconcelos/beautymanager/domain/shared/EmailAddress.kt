package com.pedrovasconcelos.beautymanager.domain.shared

import java.util.regex.Matcher
import java.util.regex.Pattern


data class EmailAddress(val value: String) {
    companion object {
        const val EMAIL_PATTERN: String = "^(?![!#$%&'*+/=?^_`{|}~])[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
                "@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+" +
                "[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|" +
                "[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|" +
                "[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])$"

        fun createEmail(email: String?): EmailAddress? {
            if(email == null) return null
            validateEmail(email)
            return EmailAddress(email)
        }
        private fun validateEmail(email: String){
            val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher: Matcher = pattern.matcher(email)
            if(!matcher.matches()){
                throw EmailException()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmailAddress) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }
}

class EmailException : ValidationException("Invalid email")