package shop.jenny.domain.entity

import java.util.UUID

class Customer(
        val id: UUID,
        val name: String,
        val email: String,
        val phoneNumber: String,
        val alias: String? = null,
        val note: String? = null,
)
