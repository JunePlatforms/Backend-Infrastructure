package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import com.June.CourierNetwork.Model.UpdateUserRequest;

public interface UserService {

    UserDTO findUserById(Long userId);

    void deleteUser(Long userId);

    int updateUser(Long id, UpdateUserRequest user);

    int updateUserPassword(Long id, UpdatePasswordRequest passwordRequest);

}
