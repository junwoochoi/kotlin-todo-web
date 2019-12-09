package com.kotlin.todo.handler

import com.kotlin.todo.domain.todo.Todo
import com.kotlin.todo.domain.todo.TodoRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors.toList

@Component
class TodoHandler(private val repo: TodoRepository) {

    fun getAll(req: ServerRequest): Mono<ServerResponse> =
            repo.findAll()
                    .filter(Objects::nonNull)
                    .collect(toList())
                    .flatMap { ok().body(fromValue(it)) }

    fun getById(req: ServerRequest): Mono<ServerResponse> =
            repo.findById(req.pathVariable("id").toLong())
                    .flatMap { ok().body(fromValue(it)) }


    fun save(req: ServerRequest): Mono<ServerResponse> =
            repo.saveAll(req.bodyToMono(Todo::class.java))
                    .flatMap { created(URI.create("/todos/${it.id}")).build() }
                    .next()

    fun done(req: ServerRequest): Mono<ServerResponse> =
            repo.findById(req.pathVariable("id").toLong())
                    .filter(Objects::nonNull)
                    .flatMap {
                        it.done = true
                        it.modifiedAt = LocalDateTime.now()
                        repo.save(it)
                    }
                    .flatMap {
                        it?.let { ok().build() }
                    }
                    .switchIfEmpty(notFound().build())

    fun delete(req: ServerRequest): Mono<ServerResponse> =
            repo.deleteById(req.pathVariable("id").toLong())
                    .flatMap { ok().build() }

}
