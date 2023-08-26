package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.UserDTO;

public interface UserService {

    UserDTO findUserById(Long userId);
}
