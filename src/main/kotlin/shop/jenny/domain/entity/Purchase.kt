package shop.jenny.domain.entity

import java.time.LocalDateTime
import java.util.UUID

enum class PurchaseStatus {
    PENDING,
    APPROVED,
    REJECTED,
    DONE,
}

enum class PaymentType {
    CREDIT_CARD,
    DEBIT_CARD,
    PIX,
    MONEY,
    BOLETO,
}

class Purchase(
        val id: UUID,
        val username: String,
        val supplierId: UUID,
        val totalAmount: Int,
        val status: PurchaseStatus,
        val items: List<PurchaseItem>,
        val deliveredAt: LocalDateTime? = null,
        val freightValue: Int = 0,
        val paymentType: PaymentType? = null,
        val note: String? = null,
// val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    internal class Builder(
            val username: String,
            val supplier: Supplier,
            val amount: Int,
    ) {
        private var initStatus: PurchaseStatus = PurchaseStatus.PENDING
        private val items = mutableListOf<PurchaseItem.Builder>()
        private var paymentType: PaymentType? = null
        private var freightValue: Int = 0
        private var note: String? = null

        fun setPaymentType(paymentType: PaymentType) = apply { this.paymentType = paymentType }

        fun setFreightValue(freightValue: Int) = apply { this.freightValue = freightValue }

        fun setStatus(status: PurchaseStatus) = apply { this.initStatus = status }

        fun setNote(note: String) = apply { this.note = note }

        fun addItem(item: PurchaseItem.Builder): Builder {
            items.add(item)
            return this
        }

        fun build(): Purchase {
            val purchaseId = UUID.randomUUID()
            val purchaseItems = items.map { it.setPurchase(purchaseId).build() }
            if (purchaseItems.isEmpty())
                    throw IllegalStateException("Purchase must have at least one item")

            if (amount <= 0) throw IllegalStateException("Purchase amount must be greater than 0")

            if (supplier.id != supplier.id)
                    throw IllegalStateException("Supplier must be the same as the purchase")

            var deliveredAt: LocalDateTime? = null
            if (initStatus == PurchaseStatus.DONE) deliveredAt = LocalDateTime.now()

            return Purchase(
                    purchaseId,
                    username,
                    supplier.id,
                    amount,
                    initStatus,
                    purchaseItems,
                    deliveredAt,
                    freightValue,
                    paymentType,
                    note,
            )
        }
    }
}

class PurchaseItem(
        val id: UUID,
        val purchaseId: UUID,
        val quantity: Int = 1,
        val status: PurchaseStatus,
        val guaranteeExpiration: LocalDateTime = LocalDateTime.now(),
) {
    internal class Builder(
            val quantity: Int,
    ) {
        private lateinit var purchaseId: UUID

        fun setPurchase(purchaseId: UUID) = apply { this.purchaseId = purchaseId }

        fun build(): PurchaseItem {
            if (quantity <= 0)
                    throw IllegalStateException("Purchase item quantity must be greater than 0")

            if (!this::purchaseId.isInitialized)
                    throw IllegalStateException("Purchase item must have a purchase")

            return PurchaseItem(UUID.randomUUID(), purchaseId, quantity, PurchaseStatus.PENDING)
        }
    }
}
