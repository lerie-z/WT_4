package server.entity;

import java.sql.Timestamp;

public class UserOrder implements Identifiable {
    private int id;
    private String status;
    private Timestamp startTime;
    private Timestamp endTime;
    private int userId;
    private int apartmentId;

    public UserOrder() {
    }

    public UserOrder(int id, String status, Timestamp startTime, Timestamp endTime, int userId, int apartmentId) {
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.apartmentId = apartmentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
