package com.dh.usersService.service;



import com.dh.usersService.model.UserDTO;
import com.dh.usersService.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository IUserRepository;

//    private final List<User> userRepository;


//    private msBillsRepository msBillsRepository;

//    public UserService(msBillsRepository msBillsRepository) {
//        this.msBillsRepository = msBillsRepository;
//        this.userRepository = List.of(new User(1, "Tomas", "Pereyra", "tomas.pereyra@digitalhouse.com"));
//    }

//    public User findById(Integer id){
//        User user = userRepository.stream().filter(_user -> Objects.equals(_user.getId(), id)).findFirst().orElse(null);
//        SubscriptionDTO subscriptionDTO = msBillsRepository.findByUserId(id);
//        if (user != null)
//            user.setSubscription(subscriptionDTO);
//
//        return user;
//    }

    public UserDTO findbyId(String id){
        return IUserRepository.findById(id);

    }


}