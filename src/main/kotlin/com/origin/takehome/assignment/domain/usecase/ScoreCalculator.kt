package com.origin.takehome.assignment.domain.usecase

import com.origin.takehome.assignment.domain.model.MaritalStatus
import com.origin.takehome.assignment.domain.model.OwnershipStatus
import com.origin.takehome.assignment.domain.model.PersonalInformation
import com.origin.takehome.assignment.domain.model.RiskScore
import org.springframework.stereotype.Component
import java.time.Year

@Component
class ScoreCalculator {

    fun calculate(info: PersonalInformation): RiskScore {
        val baseScore = info.riskQuestions.count { question -> question }
        val startingScore = allLinesQuestions(info) + baseScore

        val autoScore = calculateAutoScore(startingScore, info)
        val disabilityScore = calculateDisabilityScore(startingScore, info)
        val homeScore = calculateHomeScore(startingScore, info)
        val lifeScore = calculateLifeScore(startingScore, info)

        return RiskScore(autoScore, disabilityScore, homeScore, lifeScore)
    }

    private fun allLinesQuestions(info: PersonalInformation): Int {
        var score = 0
        when {
            info.age < 30 -> score -= 2
            40 >= info.age -> score--
        }
        if (info.income > 200000) {
            score--
        }
        return score
    }

    private fun calculateAutoScore(startingScore: Int, info: PersonalInformation): String {
        if (info.vehicle == null) {
            return "ineligible"
        }

        var score = 0
        if (info.vehicle.year >= Year.now().value - 5) {
            score++
        }
        return gradeToLabel(startingScore + score)
    }

    private fun calculateDisabilityScore(startingScore: Int, info: PersonalInformation): String {
        if (info.income == 0 || info.age > 60) {
            return "ineligible"
        }

        var score = 0
        if (info.house?.ownershipStatus == OwnershipStatus.MORTGAGED) {
            score++
        }
        if (info.dependents > 0) {
            score++
        }
        if (info.maritalStatus == MaritalStatus.MARRIED) {
            score--
        }

        return gradeToLabel(startingScore + score)
    }

    private fun calculateHomeScore(startingScore: Int, info: PersonalInformation): String {
        if (info.house == null) {
            return "ineligible"
        }
        var score = 0
        if (info.house.ownershipStatus == OwnershipStatus.MORTGAGED) {
            score++
        }
        return gradeToLabel(startingScore + score)
    }

    private fun calculateLifeScore(startingScore: Int, info: PersonalInformation): String {
        var score = 0
        if (info.age > 60) {
            return "ineligible"
        }
        if (info.dependents > 0) {
            score++
        }
        if (info.maritalStatus == MaritalStatus.MARRIED) {
            score++
        }

        return gradeToLabel(startingScore + score)
    }

    private fun gradeToLabel(score: Int): String {
        return when {
            score <= 0 -> "economic"
            score <= 2 -> "regular"
            else -> "responsible"
        }
    }
}
