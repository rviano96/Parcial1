package delivery.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@DynamicUpdate
public class Restaurant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    //nombre
    @Column(nullable = false)
    private String name;
    //direccion
    @Column(nullable = false)
    private String address;
    // tipo LocalTime para solo almacenar hora, sin referencia de zona ni de fecha
    //hora de apertura
    @Column(nullable = false)
    private LocalTime openingTime;
    // hora de cierre
    @Column(nullable = false)
    private LocalTime closingTime;
    // puntuacion
    @Column(nullable = false)
    private double rating;
    // comidas
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
    @OneToMany(cascade = CascadeType.ALL, targetEntity=Food.class, mappedBy="restaurant", fetch = FetchType.EAGER)
    private List<Food> foods = new ArrayList<>();

    public List<Food> getFoodList() {
        return foods;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


}
