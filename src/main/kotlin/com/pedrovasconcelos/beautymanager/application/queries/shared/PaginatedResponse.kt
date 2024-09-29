package com.pedrovasconcelos.beautymanager.application.queries.shared

class PaginatedResponse<T>(
    val data: List<T>,
    val page: Int,
    val size: Int,
    val total: Int
) {
    val totalPages: Int = if (size == 0) 0 else (total + size - 1) / size
}