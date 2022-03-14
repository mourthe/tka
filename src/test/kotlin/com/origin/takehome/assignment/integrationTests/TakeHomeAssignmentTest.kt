package com.origin.takehome.assignment.integrationTests

import com.origin.takehome.assignment.domain.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(
    classes = [],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class TakeHomeAssignmentTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `Should return the expected result`() {
        val payload = PersonalInformation(
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
        val expectedResult = RiskScore("regular", "ineligible", "economic", "regular")

        val response = restTemplate.postForEntity<RiskScore>("/api/v1/calculate", payload)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(expectedResult, response.body)
    }
}
