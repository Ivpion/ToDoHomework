package dao;


import exception.AppDBException;
import model.ToDoModel;

import java.util.List;

public interface Dao {

    ToDoModel create(ToDoModel toDoModel) throws AppDBException;

    ToDoModel read(int id);

    ToDoModel update(ToDoModel toDoModel) throws AppDBException;

    ToDoModel delete(ToDoModel toDoModel) throws AppDBException;

    DataContainer getContainer();

    List<ToDoModel> readAll();

    List<ToDoModel> readFromTo(int from, int to);

    int getQuantityOfPages(int pageSelector);


}
