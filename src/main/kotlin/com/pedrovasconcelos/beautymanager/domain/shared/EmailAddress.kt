package com.pedrovasconcelos.beautymanager.domain.shared


data class EmailAddress(val value: String) {
    companion object {
        fun createEmail(email: String?): EmailAddress? {

            validateEmail(email)
            return EmailAddress(email!!)
        }
        private fun validateEmail(email: String?){
            if (false) {
                throw EmailException();
            }
        }
    }
}

class EmailException : Exception() {
    override val message: String = "Invalid email"
}