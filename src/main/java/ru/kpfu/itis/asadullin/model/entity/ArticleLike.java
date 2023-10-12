package ru.kpfu.itis.asadullin.model.entity;

public class ArticleLike {
    private int userId;
    private int articleId;

    public ArticleLike(int userId, int articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
