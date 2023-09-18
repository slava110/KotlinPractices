package com.slava_110.website

import com.slava_110.website.api.api
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(CIO, port = 8080) {
        routing {
            api()
        }
    }.start(wait = true)
}