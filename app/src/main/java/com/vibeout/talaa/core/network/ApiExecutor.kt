package com.vibeout.talaa.core.network

import com.google.gson.Gson
import com.vibeout.talaa.core.network.dto.ApiEnvelope
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApiException(
    override val message: String,
    val code: String? = null,
    val httpStatus: Int? = null,
) : Exception(message)

class ApiExecutor(private val gson: Gson) {
    suspend fun <T> execute(block: suspend () -> Response<ApiEnvelope<T>>): T {
        try {
            val response = block()
            val body = response.body()
            if (response.isSuccessful && body?.success == true && body.data != null) return body.data
            if (response.isSuccessful && body?.success == true) {
                @Suppress("UNCHECKED_CAST")
                return Unit as T
            }
            val parsed = response.errorBody()?.string()?.let {
                runCatching { gson.fromJson(it, ApiEnvelope::class.java) }.getOrNull()
            }
            throw ApiException(
                message = parsed?.message?.takeIf { it.isNotBlank() }
                    ?: body?.message?.takeIf { it.isNotBlank() }
                    ?: response.message().ifBlank { "Request failed" },
                code = parsed?.error?.code ?: body?.error?.code,
                httpStatus = response.code(),
            )
        } catch (e: ApiException) {
            throw e
        } catch (e: IOException) {
            throw ApiException("No connection. Check your internet and try again.", "NETWORK_ERROR")
        } catch (e: HttpException) {
            throw ApiException(e.message(), httpStatus = e.code())
        } catch (e: Exception) {
            throw ApiException(e.message ?: "Unexpected error", "UNKNOWN")
        }
    }
}
