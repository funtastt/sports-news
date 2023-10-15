package ru.kpfu.itis.asadullin.model.entity;

import java.sql.Timestamp;

public class Friend {
    private int userId, friendId;

    private Timestamp requestTime;

    public Friend(int userId, int friendId, Timestamp requestTime) {
        this.userId = userId;
        this.friendId = friendId;
        this.requestTime = requestTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }
}
