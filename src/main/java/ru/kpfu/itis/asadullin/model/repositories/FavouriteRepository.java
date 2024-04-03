package ru.kpfu.itis.asadullin.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.asadullin.model.entity.Favourite;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    boolean existsByUserIdAndArticleId(int userId, int articleId);

    void deleteByUserIdAndArticleId(int userId, int articleId);
}
