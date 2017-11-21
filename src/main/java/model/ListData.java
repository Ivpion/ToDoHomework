package model;

import java.util.List;

public class ListData {
   private List items;
   private int total;

    public ListData(List itemsList, int totalItems) {
        this.items = itemsList;
        this.total = totalItems;
    }

    public ListData() {
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
