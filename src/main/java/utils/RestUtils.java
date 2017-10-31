package utils;

import model.ToDoModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class RestUtils {

    public static HttpServletResponse setResponse(HttpServletResponse response, int statusCode){
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(statusCode);
        return response;
    }

    public static ToDoModel getToDoModel(HttpServletRequest request) throws IOException {
        InputStreamReader isr = new InputStreamReader(request.getInputStream());
        ToDoModel model = JsonUtils.getGSON().fromJson(isr, ToDoModel.class);
        return model;
    }

    public static PrintWriter getPrintWriter(HttpServletResponse response, Object model) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(JsonUtils.getGSON().toJson(model));
        out.flush();
        return out;
    }



}
