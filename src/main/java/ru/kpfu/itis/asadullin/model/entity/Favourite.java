package ru.kpfu.itis.asadullin.model.entity;

public class Favourite {
    private int favouriteId, userId, articleId;

    public Favourite(int favouriteId, int userId, int articleId) {
        this.favouriteId = favouriteId;
        this.userId = userId;
        this.articleId = articleId;
    }

    public Favourite(int userId, int articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(int favouriteId) {
        this.favouriteId = favouriteId;
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
