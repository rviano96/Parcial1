package delivery.business.implementation;

import delivery.business.IRestaurantBusiness;
import delivery.persistance.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantBusiness implements IRestaurantBusiness {
    //TODO
    // -Implementar metodos de la interfaz IRestaurantBusiness
    // -Siempre usar el log cuando ocurra una excepcion. Solo usar logs en clases de negocio!!
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RestaurantRepository retaurantDao;
}
