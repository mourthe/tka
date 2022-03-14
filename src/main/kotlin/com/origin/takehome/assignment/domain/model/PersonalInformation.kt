package com.origin.takehome.assignment.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PersonalInformation(
    @JsonProperty(value = "age", required = true)
    val age: Int,
    @JsonProperty(value = "dependents", required = true)
    val dependents: Int,
    @JsonProperty(value = "house", required = false)
    val house: House? = null,
    @JsonProperty(value = "income", required = true)
    val income: Int,
    @JsonProperty(value = "marital_status", required = true)
    val maritalStatus: MaritalStatus,
    @JsonProperty(value = "risk_questions", required = true)
    val riskQuestions: Array<Boolean>,
    @JsonProperty(value = "vehicle", required = false)
    val vehicle: Vehicle? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonalInformation

        if (age != other.age) return false
        if (dependents != other.dependents) return false
        if (house != other.house) return false
        if (income != other.income) return false
        if (maritalStatus != other.maritalStatus) return false
        if (!riskQuestions.contentEquals(other.riskQuestions)) return false
        if (vehicle != other.vehicle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = age
        result = 31 * result + dependents
        result = 31 * result + house.hashCode()
        result = 31 * result + income
        result = 31 * result + maritalStatus.hashCode()
        result = 31 * result + riskQuestions.contentHashCode()
        result = 31 * result + vehicle.hashCode()
        return result
    }
}

data class House(
    @JsonProperty(value = "ownership_status", required = true)
    val ownershipStatus: OwnershipStatus
)

data class Vehicle(
    @JsonProperty(value = "year", required = true)
    val year: Int
)

enum class MaritalStatus {
    @JsonProperty(value = "married")
    MARRIED,

    @JsonProperty(value = "single")
    SINGLE
}

enum class OwnershipStatus {
    @JsonProperty(value = "mortgaged")
    MORTGAGED,

    @JsonProperty(value = "owned")
    OWNED
}

data class RiskScore(
    @JsonProperty(value = "auto")
    val auto: String,
    @JsonProperty(value = "disability")
    val disability: String,
    @JsonProperty(value = "home")
    val home: String,
    @JsonProperty(value = "life")
    val life: String
)
