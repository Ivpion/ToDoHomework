package dao;

import model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataContainer {

    public List<ToDoModel> list;

    public DataContainer() {
        this.list = new ArrayList<>();
    }
}
