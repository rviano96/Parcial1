package delivery.web;

import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.business.IFoodBusiness;
import delivery.model.Food;
import delivery.web.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.URL_BASE_FOODS)
public class FoodRestController {
    //TODO
    // -Servicio para agregar una comida - DONE
    // -Servicio para modificar una comida - DONE
    // -Servicio para eliminar una comida - DONE
    // -Servicio para consultar el listado de comida del restaurant X (no se si hacerlo aca o en el controller de restaurant)
    @Autowired
    private IFoodBusiness iFoodBusiness;

    @PostMapping("")
    public ResponseEntity<String> insert(@RequestBody Food food) {
        try {
            iFoodBusiness.save(food);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", Constants.URL_BASE_FOODS + "/" +food.getId());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<String>update(@RequestBody Food food) {
        try {
            iFoodBusiness.save(food);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>delete(@PathVariable("id") int id) {

        try {
            iFoodBusiness.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> load(@PathVariable("id") int idFood){
       try {
           return new ResponseEntity(iFoodBusiness.load(idFood),HttpStatus.OK);
       }catch (BusinessException e){
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       } catch (NotFoundException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("")
    public ResponseEntity<List<Food>> list() {
        try {
            return new ResponseEntity<>(iFoodBusiness.list() ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
