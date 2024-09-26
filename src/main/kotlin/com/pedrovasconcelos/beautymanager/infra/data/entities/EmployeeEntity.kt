import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "centers")
data class CenterDocument(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val active: Boolean,
    val email: String,
    val employees: List<UUID> = emptyList()
)


@Document(collection = "employees")
data class EmployeeEmbeddedDocument(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val role: String,
    val active: Boolean,
    val centerId: UUID
)