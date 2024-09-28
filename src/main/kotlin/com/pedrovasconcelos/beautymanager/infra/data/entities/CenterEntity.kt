import com.pedrovasconcelos.beautymanager.infra.data.entities.CustomerEmbedded
import com.pedrovasconcelos.beautymanager.infra.data.entities.EmployeeEmbeddedDocument
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
    val customers: List<CustomerEmbedded> = emptyList()
)


