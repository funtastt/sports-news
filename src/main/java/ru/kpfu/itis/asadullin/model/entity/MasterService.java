package ru.kpfu.itis.asadullin.model.entity;

public class MasterService {
    private int id;
    private int masterId;
    private int serviceId;

    public MasterService(int id, int masterId, int serviceId) {
        this.id = id;
        this.masterId = masterId;
        this.serviceId = serviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
