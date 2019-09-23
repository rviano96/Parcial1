package delivery;

import delivery.model.Food;
import delivery.model.Restaurant;
import delivery.persistance.FoodRepository;
import delivery.persistance.RestaurantRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DeliveryApplication  implements CommandLineRunner {

    public static void main(String[] args) {
            SpringApplication.run(DeliveryApplication.class, args);
    }

    @Autowired
    private FoodRepository foodDao;
    @Autowired
    private RestaurantRepository restaurantDao;
    @Override
    public void run(String... args) throws Exception {

        /*Restaurant r;
        Food f;
        LocalTime opening = LocalTime.of(18, 30);
        LocalTime closing = LocalTime.of(01, 00);
        for(int i = 0; i <= 10; i++){
            r = new Restaurant();
            f = new Food();
            List<Food> foodList= new ArrayList<>();
            r.setId(i+1);
            r.setAddress("Avenida siemprevivas " + (i+1)*100);
            r.setName("Restaurant " + (i+1));
            r.setRating(i/2);
            r.setOpeningTime(opening);
            r.setClosingTime(closing);
            for(int j = 0; j < 3; j++){
                f.setId(i+1);
                f.setName("Comida " + (i+1) + " " + (j+1));
                f.setPrice(i*10);
                f.setQuantity(i*5);
                f.setUnit("Porcion");
                foodDao.save(f);
                foodList.add(f);
            }
            restaurantDao.save(r);
        }*/

    }
}

