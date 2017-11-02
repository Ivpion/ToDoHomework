package rest;


import dao.Dao;
import exception.AppDBException;
import model.ToDoModel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import utils.RestUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateHandler extends AbstractHandler {

    private Dao dao;

    public CreateHandler(Dao dao) {
        this.dao = dao;
    }


    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response = RestUtils.setResponse(response, HttpServletResponse.SC_CREATED);
        ToDoModel createModel = RestUtils.getToDoModel(request);
        ToDoModel returnModel = null;
        String error = null;
        try {
            returnModel = dao.create(createModel);
        } catch (AppDBException e) {
            error = e.getMessage();
            e.printStackTrace();
        }
        RestUtils.getPrintWriter(response,returnModel, error);
        baseRequest.setHandled(true);
    }
}

