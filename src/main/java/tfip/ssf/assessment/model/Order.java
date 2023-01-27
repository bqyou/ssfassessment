package tfip.ssf.assessment.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Order {

    @NotNull(message = "You must select the type of pizza")
    private String pizza;

    @NotNull(message = "You must select the size of the pizza")
    private String size;

    @NotNull(message = "Please enter the quantity")
    @Min(value = 1, message = "Minimum order is 1 pizza")
    @Max(value = 10, message = "Maximum order is 10 pizza")
    private Integer quantity;

    public String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
