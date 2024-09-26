package com.pedrovasconcelos.beautymanager.domain.shared

open class RepositoryError : BaseError() {
    override val message: String = "Error while accessing the repository"
    class ErrorSavingEmployee : RepositoryError() {
        override val message: String = "Error while saving employee"
    }
    object EmployeeNotFound : RepositoryError() {
        override val message: String = "Employee not found"
    }

    object EmployeeAlreadyExists : RepositoryError() {
        override val message: String = "Employee already exists"
    }
}

