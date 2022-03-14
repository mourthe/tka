package com.origin.takehome.assignment.infrastructure.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return if (ofClass.enclosingClass != null &&
        ofClass.enclosingClass.kotlin.objectInstance?.javaClass == ofClass
    ) {
        ofClass.enclosingClass
    } else {
        ofClass
    }
}

inline fun <reified T : Any> T.logger(): Logger = LoggerFactory.getLogger(unwrapCompanionClass(T::class.java))
