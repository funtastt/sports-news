package ru.kpfu.itis.asadullin.model.entity;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private int masterId;
    private Timestamp time;
    private int serviceId;
    private String clientPhone;

    public Appointment(int appointmentId, int masterId, int serviceId, Timestamp time, String clientPhone) {
        this.appointmentId = appointmentId;
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.time = time;
        this.clientPhone = clientPhone;
    }

    public Appointment(int masterId, int serviceId, Timestamp time, String clientPhone) {
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.time = time;
        this.clientPhone = clientPhone;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", masterId=" + masterId +
                ", time=" + time +
                ", serviceId=" + serviceId +
                ", clientPhone='" + clientPhone + '\'' +
                '}';
    }
}
