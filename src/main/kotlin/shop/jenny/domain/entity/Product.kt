package sop.jenny.domain.entity

import java.time.LocalDateTime
import java.util.UUID

enum class ProductStatus {
    ACTIVE,
    INACTIVE,
    DELETED,
}

class Product(
        val id: UUID,
        val name: String,
        val tag: String,
        val description: String? = null,
        val categories: List<String> = emptyList(),
        val status: ProductStatus,
        val imagesUrl: List<String> = emptyList(),
        val pricing: List<Price> = emptyList(),
)

class Price(
        val id: UUID,
        val productId: UUID,
        val username: String,
        val price: Int,
        createdAt: LocalDateTime = LocalDateTime.now(),
)
