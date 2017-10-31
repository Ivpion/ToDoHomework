package model;

public class ToDoModel {


    private int id;
    private String title;
    private boolean isCompleted;

    public ToDoModel(String title, boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "ToDoModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDoModel toDoModel = (ToDoModel) o;

        if (id != toDoModel.id) return false;
        return title != null ? title.equals(toDoModel.title) : toDoModel.title == null;
    }

}
