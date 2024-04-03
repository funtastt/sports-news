package ru.kpfu.itis.asadullin.model.service;

import ru.kpfu.itis.asadullin.model.entity.Favourite;

public interface FavouriteService {
    boolean isFavoured(Favourite favourite);

    void toggleFavourite(Favourite favourite);

    int getFavouritesCount(int articleId);
}
