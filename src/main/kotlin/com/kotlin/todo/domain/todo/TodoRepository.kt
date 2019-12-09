package com.kotlin.todo.domain.todo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : ReactiveCrudRepository<Todo, Long> {
}
