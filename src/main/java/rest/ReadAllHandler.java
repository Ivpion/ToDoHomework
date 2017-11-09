package rest;

import dao.Dao;
import model.ToDoModel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import utils.RestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReadAllHandler extends AbstractHandler {

    private Dao dao;

    public ReadAllHandler(Dao dao) {
        this.dao = dao;

    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response = RestUtils.setResponse(response, HttpServletResponse.SC_OK);
        List<ToDoModel> list = dao.readAll();
        RestUtils.sendResponse(response, list, null);
        baseRequest.setHandled(true);


    }
}
