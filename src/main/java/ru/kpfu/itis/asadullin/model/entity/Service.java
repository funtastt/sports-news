package ru.kpfu.itis.asadullin.model.entity;

public class Service {
    private int serviceId;
    private String name;
    private int durationMinutes;

    public Service(int serviceId, String name, int durationMinutes) {
        this.serviceId = serviceId;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
