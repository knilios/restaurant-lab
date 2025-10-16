package ku.restaurant.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import ku.restaurant.entity.Restaurant;
import ku.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RestaurantService {


    private RestaurantRepository repository;


    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }


    public Restaurant create(Restaurant restaurant) {
        restaurant.setCreatedAt(Instant.now());
        Restaurant record = repository.save(restaurant);
        return record;
    }

    public Restaurant getRestaurantById(UUID id) {
        return repository.findById(id).get();
    }

}
