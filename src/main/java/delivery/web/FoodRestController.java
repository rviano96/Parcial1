package delivery.web;

import delivery.business.IFoodBusiness;
import delivery.web.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.URL_BASE_FOODS)
public class FoodRestController {
    //TODO
    // -Servicio para agregar una comida
    // -Servicio para modificar una comida
    // -Servicio para eliminar una comida
    // -Servicio pra consultar el listado de comida del restaurant X (no se si hacerlo aca o en el controller de restaurant)
    @Autowired
    private IFoodBusiness iFoodBusiness;
}
