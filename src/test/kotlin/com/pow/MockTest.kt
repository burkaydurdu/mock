package com.pow

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

@MicronautTest
class MockTest(private val application: EmbeddedApplication<*>,
               @Client("/") private val client: RxHttpClient) : StringSpec({

    "test the server is running" {
        assert(application.isRunning)
    }

    "test the base route" {
        val request = HttpRequest.GET<String>("/")
        val body = client.toBlocking().retrieve(request)

        assertNotNull(body)
        assertEquals("Simple Mock API", body);
    }
})
