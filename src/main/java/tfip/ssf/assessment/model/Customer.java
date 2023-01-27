package tfip.ssf.assessment.model;

import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Customer {

    @NotBlank(message = "Please fill in your name")
    @Size(min = 3, message = "Minimum characters is 3")
    private String name;

    @NotBlank(message = "Please fill in your address")
    private String address;

    @NotBlank(message = "Please fill in your phone number")
    @Size(min = 8, max = 8, message = "Phone number is 8 digit")
    private String phoneNumber;

    private Boolean rush = false;

    private String comments;

    private String id;

    private String pizza;

    private String size;

    private Integer quantity;

    private float pizzacost;

    private float cost;

    public float getPizzacost() {
        return pizzacost;
    }

    public void setPizzacost(float pizzacost) {
        this.pizzacost = pizzacost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static synchronized String generateId(int numOfChar) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numOfChar) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numOfChar);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getRush() {
        return rush;
    }

    public void setRush(Boolean rush) {
        this.rush = rush;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

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

    public JsonObject toJSON() {

        return Json.createObjectBuilder()
                .add("orderId", this.id)
                .add("name", this.name)
                .add("address", this.address)
                .add("phone", this.phoneNumber)
                .add("rush", this.rush)
                .add("comments", this.comments)
                .add("pizza", this.pizza)
                .add("size", this.size)
                .add("quantity", this.quantity)
                .add("total", this.cost)
                .build();
    }

}
