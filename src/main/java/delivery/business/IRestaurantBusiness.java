package delivery.business;


import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.model.Restaurant;

import java.time.LocalTime;
import java.util.List;

public interface IRestaurantBusiness {
    //TODO
    // - Definir metodos para implementar:
    //      - Consultar el restaurante con mayor puntaje DONE
    //      - Consultar el listado de restaurantes abiertos en determinado horario.
    //      - Consultar el restaurante que tiene disponible X comida. DONE
    //      - Consultar la dirección de X restaurante DONE
    //      - Consultar comida de mayor/menor precio segun se requiera en la request.
    //      - Crear restaurants DONE
    //      - Modificar restaurants DONE
    //      - Eliminar restaurants DONE
    // - Definir excepciones segun sean necesarias

    /**
     * @description: Lista todos los restaurantes
     * @returns lista de restaurantes
     * @throws BusinessException
     */

    public List<Restaurant> list() throws BusinessException;

    /**
     *@description: Muestra el restaurante con id = idRestaurant
     * @param idRestaurant: id del restaurante
     * @returns restaurant con id = idRestaurant
     * @throws BusinessException
     * @throws NotFoundException
     */
    public Restaurant load(int idRestaurant) throws BusinessException, NotFoundException;

    /**
     * @description  Guarda un restaurant
     * @param restaurant: Restaurant a guardar
     * @returns nothing
     * @throws BusinessException
      */

    public Restaurant save(Restaurant restaurant) throws BusinessException;

    /**
     * @description: Elimina un restaurant
     * @param idRestaurant: id del restaurant a eliminar
     * @returns nothing
     * @throws BusinessException
     * @throws NotFoundException
     */
    public void remove(int idRestaurant) throws BusinessException, NotFoundException;


    public Restaurant findFirstByOrderByRatingDesc() throws BusinessException, NotFoundException;

    /**
     * @description Busca los restaurants que tengan la comida "food"
     * @param food comida que se desea buscar.
     * @return lista de restaurants con la comida "food"
     * @throws BusinessException
     * @throws NotFoundException
     */
    public List<Restaurant> findByFoodsName(String food) throws BusinessException, NotFoundException;

    /**
     * @descrition Devuelve la direccion del restaurant con nombre "restaurantName"
     * @param restaurantName nombre del restaurant a buscar
     * @return Direccion del restaurant con nomnre "restaurantName"
     * @throws BusinessException
     * @throws NotFoundException
     */
    public String findAddressByName(String restaurantName) throws BusinessException, NotFoundException;

    /**
     * @Description Devuelve una lista de restaurants que esten abiertos en el horario que le paso como parametro
     * @param hour
     * @return
     * @throws BusinessException
     * @throws NotFoundException
     */
    public List<Restaurant> findAllByOpeningTimeLessThan(String hour) throws BusinessException, NotFoundException;

}
