package utils;

import model.ResponseAPI;
import model.ToDoModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class RestUtils {

    public static HttpServletResponse setResponse(HttpServletResponse response, int statusCode){
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(statusCode);
        return response;
    }

    public static int getPageSelector(HttpServletRequest request) throws IOException {
        int pageSelector = Integer.parseInt(request.getParameter("count"));
        return pageSelector;
    }

    public static ToDoModel getToDoModel(HttpServletRequest request) throws IOException {
        InputStreamReader isr = new InputStreamReader(request.getInputStream());
        ToDoModel model = JsonUtils.getGSON().fromJson(isr, ToDoModel.class);
        return model;
    }

    public static void sendResponse(HttpServletResponse response, Object model, String errorMes) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseAPI responseObj = new ResponseAPI(null, model);
        if (model == null){
            responseObj = new ResponseAPI(errorMes, model);
        }

        out.println(JsonUtils.getGSON().toJson(responseObj));
        out.flush();
    }



}
