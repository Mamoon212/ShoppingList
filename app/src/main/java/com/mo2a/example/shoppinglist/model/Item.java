package com.mo2a.example.shoppinglist.model;

public class Item {
    private int id;
    private String name;
    private String color;
    private String quantity;
    private String size;
    private String dateAdded;

    public Item() {
    }

    public Item(String name, String color, String quantity, String size, String dateAdded) {
        this.name = name;
        this.color = color;
        this.quantity = quantity;
        this.size = size;
        this.dateAdded = dateAdded;
    }

    public Item(int id, String name, String color, String quantity, String size, String dateAdded) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.quantity = quantity;
        this.size = size;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
