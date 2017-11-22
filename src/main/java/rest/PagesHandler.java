package rest;

import dao.Dao;
import model.ToDoModel;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import utils.RestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PagesHandler extends AbstractHandler {

    private Dao dao;

    public PagesHandler(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        response = RestUtils.setResponse(response, HttpServletResponse.SC_OK);
        int pageSelector = RestUtils.getPageSelector(request);
        int nums = dao.getQuantityOfPages(pageSelector);
        RestUtils.sendResponse(response, nums, null);
        baseRequest.setHandled(true);

    }
}
