package shop.jenny.domain.entity

import java.util.*

class Supplier(
        val id: UUID,
        val name: String,
        val slug: String,
        val url: String? = null,
        val note: String? = null
) {
    internal class Builder(private val name: String) {
        private var url: String? = null
        private var note: String? = null

        fun setUrl(url: String) = apply { this.url = url }

        fun setNote(note: String) = apply { this.note = note }

        fun build(): Supplier {
            return Supplier(
                    UUID.randomUUID(),
                    name.lowercase(),
                    slugfy(name.lowercase()),
                    url,
                    note
            )
        }
    }
}

fun slugfy(name: String): String {
    return name.lowercase().replace(" ", "-")
}
