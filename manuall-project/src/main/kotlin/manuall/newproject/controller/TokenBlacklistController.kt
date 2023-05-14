package manuall.newproject.controller

import manuall.newproject.service.TokenBlacklistService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tokenBlacklist")
class TokenBlacklistController (
    val tokenBlacklistService: TokenBlacklistService
) {
}