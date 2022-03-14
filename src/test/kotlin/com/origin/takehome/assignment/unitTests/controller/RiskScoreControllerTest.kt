package com.origin.takehome.assignment.unitTests.controller

import com.origin.takehome.assignment.application.controller.RiskScoreController
import com.origin.takehome.assignment.domain.model.*
import com.origin.takehome.assignment.domain.usecase.ScoreCalculator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class RiskScoreControllerTest {

    private val scoreCalculator: ScoreCalculator = mockk()
    private val riskScoreController = RiskScoreController(scoreCalculator)

    private val input = PersonalInformation(
        35,
        2,
        House(
            OwnershipStatus.OWNED
        ),
        0,
        MaritalStatus.MARRIED,
        arrayOf(false, true, false),
        Vehicle(2018)
    )

    @Test
    fun `should return http OK with the correct payload when calculating the score`() {
        val expectedBody = RiskScore("regular", "ineligible", "economic", "regular")
        every { scoreCalculator.calculate(input.copy()) } returns expectedBody.copy()

        val result = riskScoreController.calculate(input)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.hasBody())
        assert(result.body == expectedBody)
    }

    @Test
    fun `should return 500 when a unexpected exception is thrown`() {
        every { scoreCalculator.calculate(input.copy()) } throws Exception()

        val result = riskScoreController.calculate(input)

        assert(result.statusCode == HttpStatus.INTERNAL_SERVER_ERROR)
        assert(result.hasBody())
        assert(result.body == "A internal error has happen. Try again later.")
    }
}
