package ru.kpfu.itis.asadullin.model.entity;

public class Master {
    private int masterId;
    private String name;

    public Master(int masterId, String name) {
        this.masterId = masterId;
        this.name = name;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
