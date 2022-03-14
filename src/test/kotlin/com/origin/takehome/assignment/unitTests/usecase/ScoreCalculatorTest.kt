package com.origin.takehome.assignment.unitTests.usecase

import com.origin.takehome.assignment.domain.model.*
import com.origin.takehome.assignment.domain.usecase.ScoreCalculator
import org.junit.jupiter.api.Test

class ScoreCalculatorTest {

    private val scoreCalculator = ScoreCalculator()

    @Test
    fun `should execute`() {
        val input = PersonalInformation(
            61,
            2,
            House(
                OwnershipStatus.OWNED
            ),
            0,
            MaritalStatus.MARRIED,
            arrayOf(false, true, false),
            Vehicle(2018)
        )
        val expectedResult = RiskScore("regular", "ineligible", "regular", "ineligible")

        val result = scoreCalculator.calculate(input)

        assert(expectedResult == result)
    }

    @Test
    fun `should execute correctly when the user doesn't have a vehicle`() {
        val input = PersonalInformation(
            35,
            2,
            null,
            0,
            MaritalStatus.MARRIED,
            arrayOf(false, true, false),
            Vehicle(2018)
        )
        val expectedResult = RiskScore("regular", "ineligible", "ineligible", "regular")

        val result = scoreCalculator.calculate(input)

        assert(expectedResult == result)
    }

    @Test
    fun `should execute correctly when the user doesn't have a house and vehicle`() {
        val input = PersonalInformation(
            35,
            2,
            null,
            222000,
            MaritalStatus.MARRIED,
            arrayOf(false, true, false),
            null
        )
        val expectedResult = RiskScore("ineligible", "economic", "ineligible", "regular")

        val result = scoreCalculator.calculate(input)

        assert(expectedResult == result)
    }

    @Test
    fun `should execute correctly when the user doesn't have a vehicle and has a mortgaged home`() {
        val input = PersonalInformation(
            35,
            2,
            House(
                OwnershipStatus.MORTGAGED
            ),
            222000,
            MaritalStatus.MARRIED,
            arrayOf(false, true, false),
            null
        )
        val expectedResult = RiskScore("ineligible", "economic", "economic", "regular")

        val result = scoreCalculator.calculate(input)

        assert(expectedResult == result)
    }
}
