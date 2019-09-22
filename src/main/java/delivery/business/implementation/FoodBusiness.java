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
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FoodRepository foodDao;


    @Override
    public List<Food> list() throws BusinessException {
        try {
            return foodDao.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public Food load(int idFood) throws BusinessException, NotFoundException {
        Optional<Food> op;

        try{
            op = foodDao.findById(idFood);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())

            throw new NotFoundException("No se encuentra la comida con id=" + idFood);
        return op.get();
    }

    @Override
    public Food save(Food food) throws BusinessException {
        try {
            return foodDao.save(food);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void remove(int idFood) throws BusinessException, NotFoundException {
        Optional<Food> op;

        try{
            op = foodDao.findById(idFood);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra la comida con id=" + idFood);
        try {
            foodDao.deleteById(idFood);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }

    }

    @Override
    public List<Food> findFoodByRestaurantName(String restaurantName) throws BusinessException, NotFoundException {
        Optional<List<Food>> op;
        try{
            op = foodDao.findFoodByRestaurantName(restaurantName);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("Ningun hay comidas para el restaurant: " + restaurantName);
        return op.get();
    }


}
