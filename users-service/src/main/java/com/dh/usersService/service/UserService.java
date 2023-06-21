package com.dh.usersService.service;



import com.dh.usersService.model.SubscriptionDTO;
import com.dh.usersService.model.User;
import com.dh.usersService.repository.msBillsRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final List<User> userRepository;


    private msBillsRepository msBillsRepository;

    public UserService(msBillsRepository msBillsRepository) {
        this.msBillsRepository = msBillsRepository;
        this.userRepository = List.of(new User(1, "Tomas", "Pereyra", "tomas.pereyra@digitalhouse.com"));
    }

//    public User findById(Integer id){
//        User user = userRepository.stream().filter(_user -> Objects.equals(_user.getId(), id)).findFirst().orElse(null);
//        SubscriptionDTO subscriptionDTO = msBillsRepository.findByUserId(id);
//        if (user != null)
//            user.setSubscription(subscriptionDTO);
//
//        return user;
//    }


}