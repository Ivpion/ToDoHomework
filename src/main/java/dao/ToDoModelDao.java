package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import exception.AppDBException;
import model.ToDoModel;
import utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ToDoModelDao implements dao.Dao {
    private int idCounter = 1;
    private String filePath;
    private DataContainer container;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Type toDoListType = new TypeToken<List<ToDoModel>>() {}.getType();

    public ToDoModelDao(String filePath) {
        this.filePath = filePath;
        container = new DataContainer();
        String todoListJson = JsonUtils.readJsonFromFile(filePath);
        container.list = gson.fromJson(todoListJson, toDoListType);
    }

    @Override
    public ToDoModel create(ToDoModel toDoModel) throws AppDBException {
        int id = idCounter++;
        toDoModel.setId(id);
        container.list.add(toDoModel);
        String modelListInJson = gson.toJson(container.list, toDoListType);
        if (!JsonUtils.writeJsonToFile(filePath, modelListInJson)) {
            throw new AppDBException();
        }
        return toDoModel;
    }

    @Override
    public ToDoModel read(int id) {
        List<ToDoModel> list = container.list.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (list.size() == 0) {
            return null;

        }
        Optional<ToDoModel> firstModel = list.stream().findFirst();
        return firstModel.orElse(null);
    }

    @Override
    public List<ToDoModel> readAll() {
        return container.list;
    }

    @Override
    public ToDoModel update(ToDoModel updated) throws AppDBException {
        ToDoModel returnModel = null;
        for (ToDoModel doModel : container.list) {
            if (doModel.equals(updated)) {
                container.list.set(container.list.indexOf(doModel), updated);
                returnModel = updated;
            }
        }

        if (returnModel == null) throw new AppDBException("specified model is not found");

        String modelListInJson = gson.toJson(container.list, toDoListType);
        if (!JsonUtils.writeJsonToFile(filePath, modelListInJson)) {
            throw new AppDBException();
        }
        return returnModel;
    }


    @Override
    public ToDoModel delete(ToDoModel deleted) throws AppDBException {
        ToDoModel updateModel = null;
        for (ToDoModel doModel : container.list) {
            if (doModel.equals(deleted)) {
                updateModel = doModel;
                container.list.remove(doModel);
                break;
            }
        }
        if (updateModel == null) throw new AppDBException("specified model is not found");
        String modelListInJson = gson.toJson(container.list, toDoListType);
        if (!JsonUtils.writeJsonToFile(filePath, modelListInJson)) {
            throw new AppDBException();
        }
        return updateModel;
    }

    public DataContainer getContainer() {
        return container;
    }
}
