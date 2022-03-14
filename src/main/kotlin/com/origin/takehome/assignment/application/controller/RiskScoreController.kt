package com.origin.takehome.assignment.application.controller

import com.origin.takehome.assignment.domain.model.PersonalInformation
import com.origin.takehome.assignment.domain.usecase.ScoreCalculator
import com.origin.takehome.assignment.infrastructure.configuration.logger
import com.origin.takehome.assignment.infrastructure.configuration.mdcClear
import com.origin.takehome.assignment.infrastructure.configuration.mdcPut
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("api/v1/")
@CrossOrigin(origins = ["\${cross.origin}"], maxAge = 3600)
class RiskScoreController(private val scoreCalculator: ScoreCalculator) {

    @PostMapping("calculate")
    fun calculate(@RequestBody payload: PersonalInformation): ResponseEntity<Any> {
        mdcPut(null)
        logger().info("starting request $payload")
        return try {
            val riskScore = scoreCalculator.calculate(payload)
            ResponseEntity(riskScore, HttpStatus.OK)
        } catch (ex: Exception) {
            logger().error(ex.stackTraceToString())
            ResponseEntity("A internal error has happen. Try again later.", HttpStatus.INTERNAL_SERVER_ERROR)
        } finally {
            logger().info("request ended")
            mdcClear()
        }
    }
}
