package com.origin.takehome.assignment.infrastructure.configuration

import org.slf4j.MDC
import java.util.UUID

internal const val LABEL_CORRELATION_ID = "cid"

fun mdcPut(cid: String?) = MDC.put(LABEL_CORRELATION_ID, cid ?: UUID.randomUUID().toString())
fun mdcClear() = MDC.clear()
fun mdcGet(): String = MDC.get(LABEL_CORRELATION_ID)
