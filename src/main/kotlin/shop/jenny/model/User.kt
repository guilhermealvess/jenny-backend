package shop.jenny.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class User {
    @Id var id: Long? = null
}
