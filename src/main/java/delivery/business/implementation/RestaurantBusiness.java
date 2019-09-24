package delivery.business.implementation;

import org.json.*;
import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.business.IRestaurantBusiness;
import delivery.model.Restaurant;
import delivery.persistance.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantBusiness implements IRestaurantBusiness {
    //TODO
    // -Implementar metodos de la interfaz IRestaurantBusiness
    // -Siempre usar el log cuando ocurra una excepcion. Solo usar logs en clases de negocio!!
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RestaurantRepository restaurantDao;

    public RestaurantBusiness() {
    }

    @Override
    public List<Restaurant> list() throws BusinessException {
        try {
            List<Restaurant> listaRestaurant = restaurantDao.findAll();
            log.info("[RestaurantBusiness.java] [list()] La lista de restaurantes es");
            for (int i = 0; i < listaRestaurant.size(); i++) {
                log.info("[RestaurantBusiness.java] [list()] Restaurante[" + i + "]: " + listaRestaurant.get(i).toString());
            }
            return listaRestaurant;
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Restaurant load(int idRestaurant) throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;

        try {
            op = restaurantDao.findById(idRestaurant);
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()) {
            log.error("[RestaurantBusiness.java] [load()] No se encontro el restaurante con id =  " + idRestaurant);
            throw new NotFoundException("No se encuentra el restaurant con id = " + idRestaurant);
        }
        log.info("[RestaurantBusiness.java] [load()] Se encontro el restaurante: " + op.get().toString());
        return op.get();
    }

    @Override
    public Restaurant save(Restaurant restaurant) throws BusinessException {
        try {
            log.info("[RestaurantBusiness.java] [save()] Se guardo correctamente el restaurante: " + restaurant);
            return restaurantDao.save(restaurant);
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void remove(int idRestaurant) throws BusinessException, NotFoundException {

        Restaurant rest = load(idRestaurant);
        try {
            restaurantDao.deleteById(idRestaurant);
            log.info("[RestaurantBusiness.java] [remove()] El restaurante " + rest.getName() + " fue borrado exitosamente");
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Restaurant findFirstByOrderByRatingDesc() throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;

        try {
            op = restaurantDao.findFirstByOrderByRatingDesc();
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No hay ningun restaurante cargado");
        log.info("[RestaurantBusiness.java] [findFirstByOrderByRatingDesc()] Se encontro el restaurante con mayor rating y es: " + op.get().toString());

        return op.get();
    }

    @Override
    public List<Restaurant> findByFoodsName(String food) throws BusinessException, NotFoundException {
        Optional<List<Restaurant>> op = null;

        try {
            op = restaurantDao.findByFoodsName(food);
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encontro ningun restaurant");
        log.info("[RestaurantBusiness.java] [findByFoodsName()] Se encontro el restaurant" + op.get().toString() + "con la comida " + food);

        return op.get();
    }

    @Override
    public String findAddressByName(String restaurantName) throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;
        JSONObject jo;
        try {
            op = restaurantDao.findAddressByName(restaurantName);
            /*jo = new JSONObject(
                    "{\"address\":\""+op.get().getAddress()+"\"}"
            );*/

        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No hay restaurante con nombre: " + restaurantName);
        log.info("[RestaurantBusiness.java] [findAddressByName()] Se encontro la direccion " + op.get().getAddress() + " para el restaurante " + restaurantName);

        // System.out.println(jo);
        return op.get().getAddress();
    }

    @Override
    public List<Restaurant> findAllByOpeningTimeLessThan(String hour) throws BusinessException, NotFoundException {

        List<Restaurant> list = new ArrayList<>();
        try {
            LocalTime localTime = LocalTime.parse(hour);
            for (Restaurant restaurant : restaurantDao.findAll()) {
                if ((localTime.isAfter(restaurant.getOpeningTime()) && localTime.isBefore(restaurant.getClosingTime()))
                        ||
                        ((restaurant.getOpeningTime().isAfter(restaurant.getClosingTime()) && !(restaurant.getOpeningTime().isAfter(localTime) && localTime.isAfter(restaurant.getClosingTime()))))) {
                    list.add(restaurant);
                }
            }
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (list.isEmpty()) {
            log.error("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] La lista esta vacia");
            throw new NotFoundException("Ningun restaurant esta abierto a la hora: " + hour);
        }

        log.info("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] Los restaurantes abiertos en la hora "+hour+" son ");
        for(int i = 0 ; i < list.size() ; i++){
            log.info("[RestaurantBusiness.java] [findAllByOpeningTimeLessThan()] Restaurante[ " + i + "]: "+ list.get(i).toString());
        }
        return list;
    }

    @Override
    public Restaurant update(Restaurant restaurant) throws BusinessException {
        try {

            //Restaurant rest = load(restaurant.getId());
            //if(restaurant.)
            log.info("[RestaurantBusiness.java] [update()] Se actualizo el restaurante"+restaurant.toString());
            return restaurantDao.save(restaurant);
        } catch (Exception e) {
            log.error("[RestaurantBusiness.java] [update()] "+e.getMessage(), e);
            throw new BusinessException(e);
        }
    }
}
