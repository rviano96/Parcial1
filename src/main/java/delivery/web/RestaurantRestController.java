package delivery.web;

import delivery.business.IRestaurantBusiness;
import delivery.web.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.URL_BASE_RESTAURANTS)
public class RestaurantRestController {
    //TODO
    // -Servicio para agregar un restaurant
    // -Servicio para modificar un restaurant
    // -Servicio para eliminar un restaurant
    // -Servicio para consultar el restaurant con mas puntaje
    // -Servicio para consultar los restaurantes abiertos en un determinado horario
    // -Servicio pra consultar el listado de comida del restaurant X (No se si hacerlo aca o en el controller de food)
    // -Servicio para consultar comida de menor/mayor precio
    // -Servicio para consultar los restaurantes que tiene disponible x comida
    // -Servicio para consultar la direccion de X restaurant
    @Autowired
    private IRestaurantBusiness restaurantBusiness;
}
