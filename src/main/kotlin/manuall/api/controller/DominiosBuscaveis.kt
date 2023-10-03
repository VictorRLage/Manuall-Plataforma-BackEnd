package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

interface DominiosBuscaveis<T> {

    @GetMapping("/all")
    @SecurityRequirement(name = "Bearer")
    fun buscarTodos(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
    ): ResponseEntity<List<T>>
}