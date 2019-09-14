package delivery.business.implementation;

import delivery.business.IFoodBusiness;
import delivery.persistance.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodBusiness implements IFoodBusiness {
    //TODO
    // -Implementar metodos de la interfaz IFoodBusiness
    // -Siempre usar el log cuando ocurra una excepcion. Solo usar logs en clases de negocio!!
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FoodRepository foodDao;
}
