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

import java.util.ArrayList;
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
            op = foodDao.findFoodByRestaurantNameOrderByPriceAsc(restaurantName);
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

    @Override
    public List<Food> findFoodPriceByRestaurantName(String restaurantName, String option) throws BusinessException, NotFoundException {
        List<Food> listFoodOrderedDesc = findFoodByRestaurantName(restaurantName);
        List<Food> listFoodOption = new ArrayList<>();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try{
            switch (option){
                case "mayor":
                    // Como estan ordenados de menor a mayor obtengo el precio del utimo elemento de la lista (o sea el mayor precio)
                    double maxPrice = listFoodOrderedDesc.get(listFoodOrderedDesc.size() - 1).getPrice();
                    for(Food food: listFoodOrderedDesc){
                        if(food.getPrice() == maxPrice){
                            listFoodOption.add(food);
                        }
                    }
                    break;
                case    "menor":
                    // Como estan ordenados de menor a mayor obtengo el precio del primer elemento de la lista (o sea el menor precio)
                    double minPrice = listFoodOrderedDesc.get(0).getPrice();
                    for(Food food: listFoodOrderedDesc){
                        if(food.getPrice() == minPrice){
                            listFoodOption.add(food);
                        }
                    }
                    break;
                default:
                    log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [La opcion para ordenar solo puede ser mayor o menor ] [ restaurantName: " + restaurantName + ", option: " + option + "]" );
                    throw new BusinessException();

            }
        }catch (Exception e){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [ "+ e.getMessage() + " ] [restaurantName: " + restaurantName + ", option: " + option + "]", e);
            throw new BusinessException(e);
        }
        if(listFoodOrderedDesc.isEmpty()){
            log.error("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [No hay ninguna comida para el  restaurante con ese nombre ] [ restaurantName: " + restaurantName + ", option: " + option + "]" );
            throw new NotFoundException("No hay comidas para el restaurant: " + restaurantName);
        }
        log.info("[" + stackTraceElements[2] + "] ["+stackTraceElements[1] + "] [comida de ese restaurant ordenadas por el parametro " + listFoodOption.toString() + " ] [restaurantName: " + restaurantName + ", option: " + option + "]" );
        return listFoodOption;
    }


}
