package mafia.wizard.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import mafia.wizard.config.NotFoundException
import mafia.wizard.entities.User
import org.springframework.security.core.context.SecurityContextHolder

fun getActorName(): String {
    val user = SecurityContextHolder.getContext().authentication.principal as User
    return user.userName ?: throw NotFoundException("empty Username")
}

val jackson = ObjectMapper()
    .registerModule(JavaTimeModule())
    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)

inline fun <reified T> String.fromJson(): T? = jackson.readValue(this, T::class.java)

inline fun <reified T> String.fromJsonArray(): List<T>? =
    jackson.readValue(this, object : TypeReference<MutableList<T>>() {}) as MutableList<T>

inline fun <reified T> T?.toJson(): String = jackson.writeValueAsString(this)