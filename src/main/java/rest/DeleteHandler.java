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


public class DeleteHandler extends AbstractHandler {

    private Dao dao;

    public DeleteHandler(Dao dao) {
        this.dao = dao;

    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response = RestUtils.setResponse(response, HttpServletResponse.SC_OK);
        ToDoModel deletedModel = RestUtils.getToDoModel(request);
        ToDoModel returnModel = null;
        try {
            returnModel = dao.delete(deletedModel);
        } catch (AppDBException e) {
            RestUtils.getPrintWriter(response, null, e.getMessage());
            e.printStackTrace();
        }
        RestUtils.getPrintWriter(response, returnModel, null);
        baseRequest.setHandled(true);

    }
}
