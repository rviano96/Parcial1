package delivery.web;

import delivery.business.Exceptions.BusinessException;
import delivery.business.Exceptions.NotFoundException;
import delivery.business.IRestaurantBusiness;
import delivery.model.Restaurant;
import delivery.web.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(Constants.URL_BASE_RESTAURANTS)
public class RestaurantRestController {
    //TODO
    // -Servicio para agregar un restaurant- STATUS: DONE
    // -Servicio para modificar un restaurant- STATUS:  DONE
    // -Servicio para eliminar un restaurant- STATUS: DONE
    // -Servicio para consultar el restaurant con mas puntaje- STATUS: IMPROVE
    // -Servicio para consultar los restaurantes abiertos en un determinado horario status: DONE
    // -Servicio para consultar comida de menor/mayor precio
    // -Servicio para consultar los restaurantes que tiene disponible x comida- STATUS: DONE
    // -Servicio para consultar la direccion de X restaurant- STATUS: DONE
    @Autowired
    private IRestaurantBusiness restaurantBusiness;

    @PostMapping("")
    public ResponseEntity<String> insert(@RequestBody Restaurant restaurant) {
        try {
            restaurantBusiness.save(restaurant);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", Constants.URL_BASE_RESTAURANTS + "/" +restaurant.getId());

            return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<String>update(@RequestBody Restaurant restaurant) {
        try {
            restaurantBusiness.save(restaurant);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>delete(@PathVariable("id") int id) {

        try {
            restaurantBusiness.remove(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("")
    public ResponseEntity<List<Restaurant>> list() {
        try {
            return new ResponseEntity<List<Restaurant>>(restaurantBusiness.list() ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/comida")
    public ResponseEntity<List<Restaurant>> findByFoodsName(@RequestParam( value = "comida") String food) {

        try {
            return new ResponseEntity<List<Restaurant>>(restaurantBusiness.findByFoodsName(food) ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<List<Restaurant>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant>load(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<Restaurant>(restaurantBusiness.load(id) ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<Restaurant>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/mejorPuntaje")
    public ResponseEntity<Restaurant> findFirstByRating() {
        try {
            return new ResponseEntity<Restaurant>(restaurantBusiness.findFirstByOrderByRatingDesc() ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<Restaurant>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/direccion")
    public ResponseEntity<String> findAddressByRestaurantName(@RequestParam(value="nombre") String restaurantName) {
        try {
            return new ResponseEntity<String>(restaurantBusiness.findAddressByName(restaurantName) ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/abiertos")
    public ResponseEntity<List<Restaurant>> findByOpeningTimeGreaterThanOrEqualTo(@RequestParam(value="hora") String hour) {
        try {
            return new ResponseEntity<List<Restaurant>>(restaurantBusiness.findAllByOpeningTimeLessThan(hour) ,HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<List<Restaurant>>(HttpStatus.NOT_FOUND);
        }

    }
}
