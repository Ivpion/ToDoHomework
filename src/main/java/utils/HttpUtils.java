package utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpUtils {

    public static void sendResponse(HttpExchange httpExchange, String body, int httpCode) throws IOException {
        try(OutputStream os = httpExchange.getResponseBody()){
            byte[] bytes = body.getBytes();
            httpExchange.sendResponseHeaders(httpCode, bytes.length);
            os.write(bytes);
        }
    }
}
