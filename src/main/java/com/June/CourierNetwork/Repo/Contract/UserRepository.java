package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import com.June.CourierNetwork.Model.UpdateUserRequest;
import com.June.CourierNetwork.Model.User;

import java.util.Optional;

public interface UserRepository {

//    Long createUser(UserDTO userDTO);

    int updateUser(Long id, UpdateUserRequest user);

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);

    Long save(User user);

    void deleteById(Long userId);

    Optional<User> findActiveUserByEmail(String email);

    int updateUserPassword(Long id, UpdatePasswordRequest passwordRequest);

    Optional<UpdatePasswordRequest> getUserPassword(Long id);

    Optional<User> findUserByCustomerNumber(String customerNumber);
}
