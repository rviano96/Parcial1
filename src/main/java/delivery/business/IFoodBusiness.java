package delivery.business;


import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.model.Food;

import java.util.List;

public interface IFoodBusiness {
    //TODO
    // - Definir los metodos necesarios para poder implementar la funcionalidad requerida en el TP sobre lo que es comida

    // ----- Progreso -----
    // - Definir excepciones segun sean necesarias - DONE
    // - Crear, Modificar, eliminar, consultar comidas - DONE

    public List<Food> list() throws BusinessException;

    /**
     *@description: Muestra la comida con id = idFood
     * @param idFood: id de la comida a consultar
     * @returns comida con id = idFood
     * @throws BusinessException
     * @throws NotFoundException
     */
    public Food load(int idFood) throws BusinessException, NotFoundException;

    /**
     * @description  Guarda una comida
     * @param food: Comida a guardar
     * @returns Comida
     * @throws BusinessException
     */

    public Food save(Food food) throws BusinessException;

    /**
     * @description: Elimina un restaurant
     * @param idFood: id del restaurant a eliminar
     * @returns nothing
     * @throws BusinessException
     * @throws NotFoundException
     */
    public void remove(int idFood) throws BusinessException, NotFoundException;

    /**
     * @description Devuelve la lista de comidas de X restaurante
     * @return lista de Food
     * @param restaurantName: El nombre del restaurant a buscar
     * @throws BusinessException
     */
    public List<Food> findFoodByRestaurantName(String restaurantName) throws BusinessException, NotFoundException;
}
