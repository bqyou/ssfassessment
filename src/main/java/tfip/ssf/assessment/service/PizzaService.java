package tfip.ssf.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import tfip.ssf.assessment.model.Customer;

@Service
public class PizzaService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void saveOrder(final Customer customer) {
        redisTemplate.opsForValue().set(customer.getId(), customer.toJSON().toString());
    }

    public String findById(String id) {

        return (String) redisTemplate.opsForValue().get(id);
    }
}
