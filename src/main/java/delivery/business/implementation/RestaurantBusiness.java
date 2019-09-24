package delivery.business.implementation;

import delivery.model.DTO.RestaurantDto;
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
    /**
     * FORMATO DE LOGS
     * dd-MM-YYYY  HH:mm:ss.SSS - [called from] [method executed]  [results] [params]?
     */

    private Logger log = LoggerFactory.getLogger(RestaurantBusiness.class.getName());
    @Autowired
    private RestaurantRepository restaurantDao;

    public RestaurantBusiness() {
    }

    @Override
    public List<Restaurant> list() throws BusinessException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            List<Restaurant> listaRestaurant = restaurantDao.findAll();
            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ " + listaRestaurant.toString() + " ]" );
            return listaRestaurant;
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] ["+ e.getMessage() + "]", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Restaurant load(int idRestaurant) throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            op = restaurantDao.findById(idRestaurant);
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] ["+ e.getMessage() + "] [ idRestaurant: "+ idRestaurant + "]", e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ No se encontro el restaurante ese id ] [ idRestaurant: " + idRestaurant +  "]");
            throw new NotFoundException("No se encuentra el restaurant con id = " + idRestaurant);
        }
        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ " + op.toString() + " ] [idRestaurant: "+ idRestaurant + "]");
        return op.get();
    }

    @Override
    public Restaurant save(Restaurant restaurant) throws BusinessException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {

            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ Se guardo correctamente: "+ restaurant.toString() + "] [" + restaurant.toString() + "]" );
            return restaurantDao.save(restaurant);
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [" + restaurant.toString() + "]", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void remove(int idRestaurant) throws BusinessException, NotFoundException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        Restaurant rest = load(idRestaurant);
        try {
            restaurantDao.deleteById(idRestaurant);
            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ " + rest.toString() + " fue borrado exitosamente] [" + "idRestaurant: "+ idRestaurant +"]");
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [ idRestaurant: " + idRestaurant + "]", e);
            throw new BusinessException(e);
        }
    }

    @Override//MODIFICAR!!!
    public List<Restaurant> findByRating() throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;
        List<Restaurant> restaurants;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            op = restaurantDao.findFirstByOrderByRatingDesc();
            restaurants = restaurantDao.findByRating(op.get().getRating());
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ]", e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [No hay ningun restaurante cargado]" );
            throw new NotFoundException("No hay ningun restaurante cargado");
        }

        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [restaurantes con mayor rating: " + restaurants.toString() + " ]" );
        return restaurants;
    }

    @Override
    public List<Restaurant> findByFoodsName(String food) throws BusinessException, NotFoundException {
        Optional<List<Restaurant>> op = null;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            op = restaurantDao.findByFoodsName(food);
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [food: " + food + "]", e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [No se encontro ningun restaurant con esa comida. ] [ food:" + food + "]" );
            throw new NotFoundException("No se encontro ningun restaurant con comida = " + food);
        }

        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [restaurantes con esa comida " + op.get().toString() + " ] [food: " + food + "]" );

        return op.get();
    }

    @Override
    public List<RestaurantDto> findAddressByName(String restaurantName) throws BusinessException, NotFoundException {
        Optional<List<Restaurant>> op = null;
        List<RestaurantDto> restDto = new ArrayList<>();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        JSONObject jo;
        try {
            op = restaurantDao.findAddressByName(restaurantName);
            for( Restaurant restList: op.get()){
                RestaurantDto restDto2 = new RestaurantDto();
                restDto2.setAddress(restList.getAddress());
                restDto.add(restDto2);
            }
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [name: " + restaurantName + "]", e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [No se encontro ningun restaurant con ese nombre. ] [ name:" + restaurantName + "]" );
            throw new NotFoundException("No hay restaurante con nombre: " + restaurantName);
        }

        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [direcciones de restaurants con ese nombre " + restDto.toString() + " ] [ name:" + restaurantName + "]" );

        return restDto;
    }

    @Override
    public List<Restaurant> findAllByOpeningTimeLessThan(String hour) throws BusinessException, NotFoundException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
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
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [hour: " + hour + "]", e);
            throw new BusinessException(e);
        }
        if (list.isEmpty()) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [No se encontro ningun restaurant abierto a esa hora ] [ hour:" + hour + "]" );
            throw new NotFoundException("Ningun restaurant esta abierto a la hora: " + hour);
        }

        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ Restaurants abiertos a esa hora" + list.toString() + " ] [ hour: " + hour + "]" );

        return list;
    }

    @Override
    public Restaurant update(Restaurant restaurant) throws BusinessException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try{
            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ Se modifico correctamente el restaurante: "+ restaurant.toString() + "][ restaurant: " + restaurant.toString() + "]" );
            return save(restaurant);
        }catch (Exception e){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [ restaurant: " + restaurant.toString() + "]", e);
            throw new BusinessException(e);
        }

    }
}
