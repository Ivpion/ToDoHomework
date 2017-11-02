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

public class UpdateHandler extends AbstractHandler{

    private Dao dao;

    public UpdateHandler(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response = RestUtils.setResponse(response, HttpServletResponse.SC_OK);
        ToDoModel updateModel = RestUtils.getToDoModel(request);
        ToDoModel ret = null;
        try {
            ret = dao.update(updateModel);
        } catch (AppDBException e) {
            RestUtils.getPrintWriter(response, null, e.getMessage());
            e.printStackTrace();
        }
        RestUtils.getPrintWriter(response,ret, null);
        baseRequest.setHandled(true);


    }
}
