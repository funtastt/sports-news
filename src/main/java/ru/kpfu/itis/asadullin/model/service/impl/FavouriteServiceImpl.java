package ru.kpfu.itis.asadullin.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.asadullin.model.entity.Favourite;
import ru.kpfu.itis.asadullin.model.repositories.FavouriteRepository;
import ru.kpfu.itis.asadullin.model.service.FavouriteService;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public boolean isFavoured(Favourite favourite) {
        return favouriteRepository.existsByUserIdAndArticleId(favourite.getUserId(), favourite.getArticleId());
    }

    @Override
    public void toggleFavourite(Favourite favourite) {
        if (isFavoured(favourite)) {
            favouriteRepository.deleteByUserIdAndArticleId(favourite.getUserId(), favourite.getArticleId());
        } else {
            favouriteRepository.save(favourite);
        }
    }

    @Override
    public int getFavouritesCount(int articleId) {
        return favouriteRepository.findAll().stream().filter(
                favourite -> favourite.getFavouriteId() == articleId
        ).toArray().length;
    }
}
