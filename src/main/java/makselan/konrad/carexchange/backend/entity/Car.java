package makselan.konrad.carexchange.backend.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Car extends AbstractEntity{

    public static final List<String> MAKES = new ArrayList<>(
            Arrays.asList("Alfa Romeo", "Audi", "BMW", "KIA", "Fiat", "Ford", "Honda", "Hyundai", "Mercedes", "Nissan", "Opel",
                    "Peugeot", "Porsche", "Renault", "Seat", "Skoda", "Suzuki", "Toyota", "Volkswagen", "Volvo")
    );

    @NotEmpty
    @NotNull
    private String make;

    @NotEmpty
    @NotNull
    private String model;

    @Positive
    @NotNull
    private int year;

    @NotEmpty
    @NotNull
    private String color;

    @Positive
    @NotNull
    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWhoPosted;

    public Car() {
    }

    public Car(String make, String model, int year, String color, int price, User userWhoPosted) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
        this.userWhoPosted = userWhoPosted;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getUserWhoPosted() {
        return userWhoPosted;
    }

    public void setUserWhoPosted(User user) {
        this.userWhoPosted = user;
    }
}
