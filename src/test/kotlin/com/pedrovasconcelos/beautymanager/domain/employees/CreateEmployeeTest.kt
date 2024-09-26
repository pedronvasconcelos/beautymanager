import arrow.core.Either
import com.pedrovasconcelos.beautymanager.domain.centers.EmployeeError
import com.pedrovasconcelos.beautymanager.domain.centers.createEmployee
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class CreateEmployeeTest {

    @Test
    fun `should return Employee when name and role are valid`() {
        // Arrange
        val name = "Alice"
        val role = "Developer"
        val centerId = UUID.randomUUID()

        // Act
        val result = createEmployee(name, role, centerId)

        // Assert
        assertThat(result).isInstanceOf(Either.Right::class.java)
        val employee = (result as Either.Right).value
        assertThat(employee.name).isEqualTo(name)
        assertThat(employee.role).isEqualTo(role)
        assertThat(employee.centerId).isEqualTo(centerId)
        assertThat(employee.active).isTrue
    }

    @Test
    fun `should return EmployeeNotValid when name is blank`() {
        // Arrange
        val name = ""
        val role = "Developer"
        val centerId = UUID.randomUUID()

        // Act
        val result = createEmployee(name, role, centerId)

        // Assert
        assertThat(result).isInstanceOf(Either.Left::class.java)
        val error = (result as Either.Left).value
        assertThat(error).isEqualTo(EmployeeError.EmployeeNotValid)
    }

    @Test
    fun `should return EmployeeNotValid when role is blank`() {
        // Arrange
        val name = "Alice"
        val role = ""
        val centerId = UUID.randomUUID()

        // Act
        val result = createEmployee(name, role, centerId)

        // Assert
        assertThat(result).isInstanceOf(Either.Left::class.java)
        val error = (result as Either.Left).value
        assertThat(error).isEqualTo(EmployeeError.EmployeeNotValid)
    }

    @Test
    fun `should return EmployeeNotValid when both name and role are blank`() {
        // Arrange
        val name = ""
        val role = ""
        val centerId = UUID.randomUUID()

        // Act
        val result = createEmployee(name, role, centerId)

        // Assert
        assertThat(result).isInstanceOf(Either.Left::class.java)
        val error = (result as Either.Left).value
        assertThat(error).isEqualTo(EmployeeError.EmployeeNotValid)
    }
}
