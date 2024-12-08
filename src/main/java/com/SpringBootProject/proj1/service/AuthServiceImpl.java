package com.SpringBootProject.proj1.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringBootProject.enums.OrderStatus;
import com.SpringBootProject.enums.userRole;
import com.SpringBootProject.proj1.Entitys.Order;
import com.SpringBootProject.proj1.Entitys.User;
import com.SpringBootProject.proj1.Repositry.OrderRepository;
import com.SpringBootProject.proj1.Repositry.UserRepository;
import com.SpringBootProject.proj1.dto.SignupRequest;
import com.SpringBootProject.proj1.dto.UserDto;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepositry;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setRole(userRole.CUSTOMER);
        User createdUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setEmail(createdUser.getEmail());
        userDto.setName(createdUser.getName());
        userDto.setUserRoles(createdUser.getRole());

        Order order=new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepositry.save(order);
        return userDto;
    }
    @Override
    public boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(userRole.ADMIN);
        if (null == adminAccount) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(userRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}