import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "centers")
data class CenterDocument(
    @Id
    val id: String,
    val name: String,
    val active: Boolean,
    val email: String,
    val employees: List<EmployeeEmbeddedDocument> = emptyList(),
    val customers: List<CustomerDocument> = emptyList()
)


data class EmployeeEmbeddedDocument(
    val id: String,
    val name: String,
    val role: String,
    val active: Boolean,
    val centerId: String
)

data class CustomerDocument(
    val id: String,
    val name: String,
    val centerId: String,
    val active: Boolean,
    val email: String?,
    val phone: String?
)