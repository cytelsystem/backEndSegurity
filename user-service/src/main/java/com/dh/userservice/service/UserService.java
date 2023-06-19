package com.dh.userservice.service;

import com.dh.userservice.model.SubscriptionDTO;
import com.dh.userservice.model.User;
import com.dh.userservice.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final List<User> userRepository;
    private SubscriptionRepository subscriptionRepository;

    public UserService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = List.of(new User(1, "Tomas", "Pereyra", "tomas.pereyra@digitalhouse.com"));
    }

    public User findById(Integer id){
        User user = userRepository.stream().filter(_user -> Objects.equals(_user.getId(), id)).findFirst().orElse(null);
        SubscriptionDTO subscriptionDTO = subscriptionRepository.findByUserId(id);
        if (user != null)
            user.setSubscription(subscriptionDTO);

        return user;
    }
}
