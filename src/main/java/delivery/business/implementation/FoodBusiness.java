package delivery.business.implementation;

import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.business.IFoodBusiness;
import delivery.model.Food;
import delivery.persistance.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodBusiness implements IFoodBusiness {
    //TODO
    // -Implementar metodos de la interfaz IFoodBusiness - WIP
    // -Siempre usar el log cuando ocurra una excepcion. Solo usar logs en clases de negocio!!
    /**
     * FORMATO DE LOGS
     * dd-MM-YYYY  HH:mm:ss.SSS - [called from] [method executed]  [results] [params]?
     */
    private Logger log = LoggerFactory.getLogger(FoodBusiness.class.getName());
    @Autowired
    private FoodRepository foodDao;


    @Override
    public List<Food> list() throws BusinessException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            List<Food> foodList = foodDao.findAll();
            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ " + foodList.toString() + " ]" );
            return foodList;
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] ["+ e.getMessage() + "]", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Food load(int idFood) throws BusinessException, NotFoundException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        Optional<Food> op;

        try{
            op = foodDao.findById(idFood);
        }catch (Exception e){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] ["+ e.getMessage() + "] [ idFood: "+ idFood + "]", e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ No se encontro la comida con ese id ] [ idFood: " + idFood +  "]");
            throw new NotFoundException("No se encuentra la comida con id=" + idFood);
        }
        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ " + op.toString() + " ] [idFood: "+ idFood + "]");
        return op.get();
    }

    @Override
    public Food save(Food food) throws BusinessException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ Se guardo correctamente la comida : "+ food.toString() + "][ food: " + food.toString() + "]" );
            return foodDao.save(food);
        } catch (Exception e) {
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [ food: " + food.toString() + "]", e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void remove(int idFood) throws BusinessException, NotFoundException {
        Optional<Food> op;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try{
            op = foodDao.findById(idFood);
            foodDao.deleteById(idFood);
            log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ La comida " + op.toString() + " fue borrada exitosamente] [" + "idFood: "+ idFood +"]");
        }catch (Exception e){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [ idFood: " + idFood + "]", e);
            throw new BusinessException(e);
        }

    }

    @Override
    public List<Food> findFoodByRestaurantName(String restaurantName) throws BusinessException, NotFoundException {
        Optional<List<Food>> op;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try{
            op = foodDao.findFoodByRestaurantName(restaurantName);
        }catch (Exception e){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [restaurantName: " + restaurantName + "]", e);
            throw new BusinessException(e);
        }
        if (!op.isPresent()){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [No hay ninguna comida para el  restaurante con ese nombre ] [ restaurantName: " + restaurantName + "]" );
            throw new NotFoundException("No hay comidas para el restaurant: " + restaurantName);
        }

        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [comida de ese restaurant " + op.get().toString() + " ] [restaurantName: " + restaurantName + "]" );

        return op.get();
    }


}
