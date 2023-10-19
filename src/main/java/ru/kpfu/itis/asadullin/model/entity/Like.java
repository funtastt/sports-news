package ru.kpfu.itis.asadullin.model.entity;

public class Like {
    private int likeId, userId, targetId;
    boolean isArticle;

    public Like(int likeId, int userId, int targetId, boolean isArticle) {
        this.likeId = likeId;
        this.userId = userId;
        this.targetId = targetId;
        this.isArticle = isArticle;
    }

    public Like(int userId, int targetId, boolean isArticle) {
        this.userId = userId;
        this.targetId = targetId;
        this.isArticle = isArticle;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public boolean isArticle() {
        return isArticle;
    }

    public void setArticle(boolean article) {
        isArticle = article;
    }
}
