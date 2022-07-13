package org.example2

import kotlinx.coroutines.*
import java.net.ServerSocket

@OptIn(DelicateCoroutinesApi::class)
class CoroutineServer(port: Int) {
    private val connectionHandler = ConnectionHandler()

    init {
        ServerSocket(port).use { serverSocket ->
            while (true) {
                val socket = serverSocket.accept()

                GlobalScope.launch { withContext(Dispatchers.IO) { connectionHandler.handle(socket) } }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            CoroutineServer(2222)
        }
    }
}