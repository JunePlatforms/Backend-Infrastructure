package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Model.User;

import java.util.Optional;

public interface UserRepository {

//    Long createUser(UserDTO userDTO);

    Long updateUser(Long id, User user);

    Optional<User> findUserByEmail(String email);

    User save(User user);
}
