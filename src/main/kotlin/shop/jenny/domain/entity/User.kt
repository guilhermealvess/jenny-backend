package shop.jenny.domain.entity

import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.UUID

enum class UserStatus {
    ACTIVE_PENDING,
    ACTIVE,
    INACTIVE,
    BLOCKED,
}

class User(
        val id: UUID,
        val name: String,
        var username: String,
        val password: String,
        val email: String,
        var status: UserStatus,
        var activatedAt: LocalDateTime?,
) {
    internal class Builder(
            private val name: String,
            private val username: String,
            private val password: String,
            private val email: String
    ) {
        fun build(): User {
            return User(
                    UUID.randomUUID(),
                    name,
                    username,
                    encodePassword(password),
                    email,
                    UserStatus.ACTIVE_PENDING,
                    null
            )
        }
    }

    fun activate() {
        if (this.status == UserStatus.ACTIVE) throw IllegalStateException("User is already active")
        this.status = UserStatus.ACTIVE
        this.activatedAt = LocalDateTime.now()
    }
}

fun encodePassword(password: String): String {
    val salt = genereteRandomString(20)
    val hashed = encodeSHA256(salt + password)
    return "sha256:$salt:$hashed"
}

fun genereteRandomString(length: Int): String {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length).map { source.random() }.joinToString("")
}

fun encodeSHA256(source: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val encodedBytes = digest.digest(source.toByteArray())
    return encodedBytes.joinToString("") { "%02x".format(it) }
}
