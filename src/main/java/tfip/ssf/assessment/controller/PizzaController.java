package tfip.ssf.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tfip.ssf.assessment.model.Customer;
import tfip.ssf.assessment.model.Order;
import tfip.ssf.assessment.service.PizzaService;

@Controller
@RequestMapping
public class PizzaController {

    @Autowired
    private PizzaService pizzaSvc;

    @GetMapping(path = "/")
    public String showForm(Model model) {
        model.addAttribute("order", new Order());
        return "index2";
    }

    @PostMapping(path = "/pizza")
    public String processOrderForm(@Valid Order order, BindingResult bResult, Model model,
            HttpSession session) {
        if (bResult.hasErrors()) {
            return "index2";
        }
        System.out.println(order.getPizza());
        System.out.println(order.getSize());
        System.out.println(order.getQuantity());
        session.setAttribute("order", order);
        model.addAttribute("customer", new Customer());
        return "deliverydetails";
    }

    @PostMapping(path = "/pizza/order")
    public String saveDelivery(@Valid Customer customer, BindingResult bResult, Model model,
            HttpSession session) {
        if (bResult.hasErrors()) {
            return "deliverydetails";
        }
        Order o = (Order) session.getAttribute("order");
        customer.setId(Customer.generateId(8));
        customer.setPizza(o.getPizza());
        customer.setSize(o.getSize());
        customer.setQuantity(o.getQuantity());
        float cost = calcTotal(customer.getPizza(), customer.getSize(), customer.getQuantity(), customer.getRush());
        customer.setCost(cost);
        model.addAttribute("customer", customer);
        System.out.println(customer.getCost());
        System.out.println(customer.getId());
        pizzaSvc.saveOrder(customer);
        return "confirmation";

    }

    public float calcTotal(String type, String size, Integer quantity, Boolean rush) {
        float total = 0f;
        switch (size) {
            case "sm":
                total = 1f;
                break;
            case "md":
                total = 1.2f;
                break;
            case "lg":
                total = 1.5f;
                break;
        }
        if (type.equals("trioformaggio")) {
            total = total * 25;
        }
        if (type.equals("margherita")) {
            total = total * 22;
        }
        if (type.equals("bella") || type.equals("marinara") || type.equals("spianatacalabrese")) {
            total = total * 30;
        }

        total = total * quantity;

        if (rush) {
            return total + 2;
        } else {
            return total;
        }
    }

    @ResponseBody
    @RequestMapping(path = "/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrder(@PathVariable String id) {
        JsonObject order = pizzaSvc.findById(id);
        if (order != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(order.toString());
        } else {
            JsonObject error = Json.createObjectBuilder()
                    .add("message", "Order %s not found".formatted(id))
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(error.toString());
        }
    }

}
