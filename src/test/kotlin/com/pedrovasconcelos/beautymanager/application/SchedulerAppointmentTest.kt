package com.pedrovasconcelos.beautymanager.application

import com.pedrovasconcelos.beautymanager.application.commands.scheduler.CreateAppointment
import com.pedrovasconcelos.beautymanager.application.commands.scheduler.SchedulerAppointmentHandler
import com.pedrovasconcelos.beautymanager.domain.centers.Center
import com.pedrovasconcelos.beautymanager.domain.centers.CenterRepository
import com.pedrovasconcelos.beautymanager.domain.centers.Employee
import com.pedrovasconcelos.beautymanager.domain.centers.addCustomer
import com.pedrovasconcelos.beautymanager.domain.customers.Customer
import com.pedrovasconcelos.beautymanager.domain.customers.createCustomer
import com.pedrovasconcelos.beautymanager.domain.scheduler.AppointmentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertNotNull

@Suppress("unused")
@Testcontainers
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchedulerAppointmentTest {

    companion object {
        private val logger = LoggerFactory.getLogger(SchedulerAppointmentTest::class.java)

        @Container
        private val mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.4.6"))
            .withCreateContainerCmdModifier { cmd ->
                cmd.hostConfig!!.withMemory(512 * 1024 * 1024)
                    .withCpuCount(2)
            }

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            logger.info("Setting MongoDB URI: ${mongoDBContainer.replicaSetUrl}")
            logger.info("MongoDB container is running: ${mongoDBContainer.isRunning}")
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }

    @Autowired
    private lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    private lateinit var centerRepository: CenterRepository

    @Autowired
    private lateinit var schedulerAppointmentHandler: SchedulerAppointmentHandler

    private lateinit var centerId: UUID
    private lateinit var employeeId: UUID
    private lateinit var customerId: UUID

    @BeforeEach
    fun setup() {
        centerId = UUID.randomUUID()
        employeeId = UUID.randomUUID()
        customerId = UUID.randomUUID()

        val center = Center(
            id = centerId,
            name = "Test Beauty Center",
            employees = listOf(Employee(id = employeeId, name = "John Doe", role = "Hairdresser", active = true, centerId = centerId)),
            customers = listOf(Customer(id = customerId, name = "Jane Doe", centerId = centerId, active = true, email = null, phone = null)),
            active = true,
            email = "test@test.com"
        )
        centerRepository.saveCenter(center)
    }

    @Test
    fun `should create appointment when everything is ok`(){
        //Arrange
        val command = CreateAppointment(
            centerId = centerId,
            customerId = customerId,
            employeeId = employeeId,
            start = LocalDateTime.of(2024, 6, 1, 10, 0),
            duration = 60,
            service = "Haircut"
        )

        //Act
        val result = schedulerAppointmentHandler.handler(command)
        val appointment = appointmentRepository.getCenterAppointments(centerId).getOrNull()?.first()

        //Assert
        assert(result.isRight())
        assertNotNull(appointment, "Appointment not found")

    }

    @Test
    fun `should not created appointment while employee is busy`(){
        //Arrange
        setupAppointmentBusyEmployee()
        val command = CreateAppointment(
            centerId = centerId,
            customerId = customerId,
            employeeId = employeeId,
            start = LocalDateTime.of(2024, 6, 1, 10, 59),
            duration = 60,
            service = "Haircut"
        )

        //Act
        val result = schedulerAppointmentHandler.handler(command)
        val appointment = appointmentRepository.getCenterAppointments(centerId).getOrNull()?.first()

        //Assert
        assert(result.isLeft())
        Assertions.assertNotEquals(appointment?.customer?.id, customerId)
    }

    private fun setupAppointmentBusyEmployee(){
        val anotherCustomer = createCustomer("Another Customer", centerId).getOrNull()!!
        val center = centerRepository.getCenter(centerId).getOrNull()!!
        val updatedCenter = center.addCustomer(anotherCustomer).getOrNull()!!
        centerRepository.saveCenter(updatedCenter)
        val anotherCommand = CreateAppointment(
            centerId = centerId,
            customerId = anotherCustomer.id,
            employeeId = employeeId,
            start = LocalDateTime.of(2024, 6, 1, 10, 0),
            duration = 60,
            service = "Haircut"
        )
       schedulerAppointmentHandler.handler(anotherCommand)
    }
}

