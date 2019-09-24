package delivery.persistance;

import delivery.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository  extends JpaRepository<Food, Integer> {
    //TODO
    // -Definir metodos que sean necesarios segun vimos en clase

    Optional <List<Food>> findFoodByRestaurantNameOrderByPriceAsc(String restaurantName) ;

}
