package manuall.newproject.controller

import manuall.newproject.service.FeedbackService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feedback")
@CrossOrigin("http://localhost:3000")
class FeedbackController (
    val feedbackService: FeedbackService
) {
}