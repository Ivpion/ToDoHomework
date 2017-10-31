package rest;

import com.google.gson.Gson;
import dao.Dao;
import exception.AppDBException;
import model.ToDoModel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DeleteHandler extends AbstractHandler {

    private Dao dao;
    private Gson gson;
    public DeleteHandler(Dao dao) {
        this.dao = dao;
        gson = new Gson();
    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        InputStreamReader isr = new InputStreamReader(request.getInputStream());
        ToDoModel deletedModel = gson.fromJson(isr, ToDoModel.class);

        ToDoModel returnModel = null;
        try {
            returnModel = dao.delete(deletedModel);
        } catch (AppDBException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(returnModel));
        out.flush();
        baseRequest.setHandled(true);

    }
}
