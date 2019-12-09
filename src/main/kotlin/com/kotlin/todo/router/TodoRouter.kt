package com.kotlin.todo.router

import com.kotlin.todo.handler.TodoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class TodoRouter(private val handler: TodoHandler) {

    @Bean
    fun routerFunction() = nest(path("/todos"),
            router {
                listOf(
                        GET("/", handler::getAll),
                        GET("/{id}", handler::getById),
                        POST("/", handler::save),
                        PUT("/{id}", handler::done),
                        DELETE("/{id}", handler::delete)
                )
            }
    )
}