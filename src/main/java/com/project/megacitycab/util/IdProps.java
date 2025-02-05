package com.project.megacitycab.util;

public class IdProps {
    private String id;

    public IdProps() {
    }

    public IdProps(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdProps{" +
                "id='" + id + '\'' +
                '}';
    }
}
