package shop.jenny.domain.entity

import java.time.LocalDateTime
import java.util.UUID

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    DELIVERED,
    CANCELED,
}

enum class OrderPaymentMethod {
    CASH,
    CREDIT_CARD,
    DEBIT_CARD,
    PIX,
}

class Order(
        val id: UUID,
        val username: String,
        val totalAmount: Int,
        val status: OrderStatus,
        val code: String,
        val label: String,
        val paymentMethod: OrderPaymentMethod,
        val customer: Customer?,
        val items: List<OrderItem>,
        val createdAt: LocalDateTime = LocalDateTime.now(),
)

class OrderItem(
        val id: UUID,
        val orderId: UUID,
)
