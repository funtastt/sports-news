package ru.kpfu.itis.asadullin.model.service;

import ru.kpfu.itis.asadullin.model.entity.Like;

public interface LikeService {
    boolean isLiked(Like like);
    void toggleLike(Like like);
    int getLikesCount(int targetId, boolean isArticle);
}
