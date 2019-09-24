package delivery.persistance;

import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.model.Restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    //TODO
    // -Definir metodos que sean necesarios segun vimos en clase.

    Optional <Restaurant> findFirstByOrderByRatingDesc();
    Optional <List<Restaurant>> findByFoodsName(String food) ;
    Optional <List<Restaurant>> findAddressByName(String restaurantName);
    List<Restaurant> findByRating(double rating);

}
