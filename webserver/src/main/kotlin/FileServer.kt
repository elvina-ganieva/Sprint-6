import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param socket Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */

        while (true) {
            socket.accept().use {
                handle(it, fs)
            }
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {
        var clientRequest: List<String>
        var vPath: VPath
        var content: String?
        var response: String

        socket.getInputStream().use { inputStream ->
            inputStream.bufferedReader().use { reader ->
                clientRequest = reader.readLine().trim().split("\\s+".toRegex())
                vPath = VPath(clientRequest[1])
                content = fs.readFile(vPath)
                response = getResponse(clientRequest[2], content)
                write(socket, response)
            }
        }
    }

    private fun getResponse(pathToFile: String, content: String?): String {
        val status = if (content == null) "404 Not Found" else "200 OK"

        return "$pathToFile ${status}\r\n" +
                "Server: FileServer\r\n" +
                "\r\n${content}"
    }

    private fun write(socket: Socket, serverResponse: String) {
        PrintWriter(socket.getOutputStream()).use { writer ->
            writer.println(serverResponse)
            writer.flush()
        }
    }
}