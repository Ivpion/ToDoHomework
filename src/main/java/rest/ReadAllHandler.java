package rest;

import com.google.gson.Gson;
import dao.Dao;
import model.ToDoModel;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReadAllHandler extends AbstractHandler {

    private Dao dao;
    private Gson gson;
    public ReadAllHandler(Dao dao) {
        this.dao = dao;
        gson = new Gson();
    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        List<ToDoModel> list = dao.readAll();

        PrintWriter out = response.getWriter();

        out.println(gson.toJson(list));
        out.flush();
        baseRequest.setHandled(true);


    }
}
