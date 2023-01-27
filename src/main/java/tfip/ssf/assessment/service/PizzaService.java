package tfip.ssf.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import tfip.ssf.assessment.model.Customer;

@Service
public class PizzaService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void saveOrder(final Customer customer) {
        redisTemplate.opsForValue().set(customer.getId(), customer.toJSON().toString());
    }

    public JsonObject findById(String id) {

        return (JsonObject) redisTemplate.opsForValue().get(id);
    }
}
