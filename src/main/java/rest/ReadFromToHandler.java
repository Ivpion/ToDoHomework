package rest;

import dao.Dao;
import model.ListData;
import model.ToDoModel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import utils.RestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReadFromToHandler extends AbstractHandler {

    private Dao dao;

    public ReadFromToHandler(Dao dao) {
        this.dao = dao;

    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response = RestUtils.setResponse(response, HttpServletResponse.SC_OK);
        int pageNum = Integer.parseInt(request.getParameter("page"));
        int pageSelector = Integer.parseInt(request.getParameter("count"));
        int from = (pageNum-1) * pageSelector;
        int to = from + pageSelector;
        List<ToDoModel> list = dao.readFromTo(from, to);
        ListData data = new ListData();
        data.setItems(list);
        data.setTotal(dao.readAll().size());
        RestUtils.sendResponse(response, data, null);
        baseRequest.setHandled(true);


    }
}
