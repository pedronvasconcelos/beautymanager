package com.pedrovasconcelos.beautymanager.domain.customers

import arrow.core.Either
import arrow.core.Either.*
import arrow.core.getOrElse
import arrow.core.right

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class CustomerTest {

    @Test
    fun `should return Customer when name and centerId are valid`() {
        // Arrange
        val name = "John Doe"
        val centerId = UUID.randomUUID()
        val email = "john.doe@example.com"
        val phone = "1234567890"

        // Act
        val result = createCustomer(name, centerId, email, phone)

        // Assert
        assertThat(result).isInstanceOf(Right::class.java)
        val customer = (result as Right<Customer>).value
        assertThat(customer).isNotNull
        assertThat(customer.name).isEqualTo(name)
        assertThat(customer.centerId).isEqualTo(centerId)
        assertThat(customer.active).isTrue
        assertThat(customer.email?.value).isEqualTo(email)
        assertThat(customer.phone).isEqualTo(phone)
    }

    @Test
    fun `should return InvalidName when name is blank`() {
        // Arrange
        val name = ""
        val centerId = UUID.randomUUID()
        val email = "jane.doe@example.com"
        val phone = "0987654321"

        // Act
        val result = createCustomer(name, centerId, email, phone)

        // Assert
        assertThat(result).isInstanceOf(Left::class.java)
        val error = (result as Left).value
        assertThat(error).isNotNull();
    }

    @Test
    fun `should return InvalidEmail when email is invalid`() {
        // Arrange
        val name = "Jane Doe"
        val centerId = UUID.randomUUID()
        val email = "jane.doe@invalid"
        val phone = "0987654321"

        // Act
        val result = createCustomer(name, centerId, email, phone)

        // Assert
        assertThat(result).isInstanceOf(Left::class.java)
        val error = (result as Left).value
        assertThat(error).isNotNull();
    }

    @Test
    fun `should return InvalidPhone when phone is invalid`() {
        // Arrange
        val name = "Mike Smith"
        val centerId = UUID.randomUUID()
        val email = "mike.smith@example.com"
        val phone = "abcde12345"

        // Act
        val result = createCustomer(name, centerId, email, phone)

        // Assert
        assertThat(result).isInstanceOf(Left::class.java)
        val error = (result as Left).value
        assertThat(error).isNotNull();
    }


    @Test
    fun `updatePhone should return updated Customer when phone is valid`() {
        // Arrange
        val originalCustomer = createCustomer("Charlie", UUID.randomUUID(), "charlie@old.com", "7778889999").onRight { it }.getOrElse { throw RuntimeException("Test failed") }
        val newPhone = "0001112222"

        // Act
        val result = updatePhone(originalCustomer, newPhone)

        // Assert
        assertThat(result).isInstanceOf(Right::class.java)
        val updatedCustomer = (result as Right<Customer>).value
        assertThat(updatedCustomer.phone).isEqualTo(newPhone)
        assertThat(updatedCustomer.name).isEqualTo(originalCustomer.name)
        assertThat(updatedCustomer.email).isEqualTo(originalCustomer.email)
        assertThat(updatedCustomer.active).isEqualTo(originalCustomer.active)
        assertThat(updatedCustomer.id).isEqualTo(originalCustomer.id)
        assertThat(updatedCustomer.centerId).isEqualTo(originalCustomer.centerId)
    }

}
