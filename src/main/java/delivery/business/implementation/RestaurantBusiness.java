package delivery.business.implementation;

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
            return restaurantDao.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Restaurant load(int idRestaurant) throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;

        try{
            op = restaurantDao.findById(idRestaurant);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())

            throw new NotFoundException("No se encuentra el restaurant con id=" + idRestaurant);
        return op.get();
    }

    @Override
    public Restaurant save(Restaurant restaurant) throws BusinessException {
        try {
            return restaurantDao.save(restaurant);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void remove(int idRestaurant) throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;

        try{
            op = restaurantDao.findById(idRestaurant);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra el restaurant con id=" + idRestaurant);
        try {
            restaurantDao.deleteById(idRestaurant);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Restaurant findFirstByOrderByRatingDesc() throws BusinessException, NotFoundException {
        Optional<Restaurant> op = null;

        try{
            op = restaurantDao.findFirstByOrderByRatingDesc();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encontro ningun restaurant");
        return op.get();
    }

    @Override
    public List<Restaurant> findByFoodsName(String food) throws BusinessException, NotFoundException {
        Optional<List<Restaurant>> op = null;

        try{
            op = restaurantDao.findByFoodsName(food);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("Ningun restaurant tiene disponible la comida: " + food);
        return op.get();
    }

    @Override
    public String findAddressByName(String restaurantName) throws BusinessException, NotFoundException {
        Optional <Restaurant> op = null;

        try{
            op = restaurantDao.findAddressByName(restaurantName);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No hay un registro de la direccion del restaurant con nombre: " + restaurantName);

        return op.get().getAddress();
    }

    @Override
    public List<Restaurant> findAllByOpeningTimeLessThan(String hour) throws BusinessException, NotFoundException {
        Optional <List<Restaurant>> op = null;

        try{
            LocalTime localTime = LocalTime.parse(hour);
            System.out.println(localTime);
            op = restaurantDao.findAllByOpeningTimeLessThanEqualAndClosingTimeGreaterThanEqual(localTime);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()){

            throw new NotFoundException("Ningun restaurant esta abierto a la hora: " + hour);
        }

        return op.get();
    }
}
