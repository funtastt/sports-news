package ru.kpfu.itis.asadullin.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.asadullin.model.entity.Like;
import ru.kpfu.itis.asadullin.model.repositories.LikeRepository;
import ru.kpfu.itis.asadullin.model.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public boolean isLiked(Like like) {
        return likeRepository.findByUserIdAndTargetIdAndIsArticle(like.getUserId(), like.getTargetId(), like.isArticle()) != null;
    }

    @Override
    public void toggleLike(Like like) {
        if (isLiked(like)) {
            likeRepository.delete(like);
        } else {
            likeRepository.save(like);
        }
    }

    @Override
    public int getLikesCount(int targetId, boolean isArticle) {
        return likeRepository.countLikes(targetId, isArticle);
    }
}
