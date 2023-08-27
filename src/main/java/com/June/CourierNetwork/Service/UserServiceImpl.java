package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import com.June.CourierNetwork.Model.UpdateUserRequest;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;
    @Override
    public UserDTO findUserById(Long userId) {
        UserDTO userDTO = new UserDTO();
        val user = userRepository.findUserById(userId);

        if (user.isPresent()) {
            if (user.get().getRole().equals(Role.COURIER)) {
                Optional<Courier> courier = courierRepository.findByUserId(userId);
                courier.orElseThrow().setUser(user.orElseThrow());
                userDTO.setCourier(courier.orElseThrow());
            }
        }else {
            return null;
        }
        return userDTO;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public int updateUser(Long id, UpdateUserRequest userDetails) {
        return userRepository.updateUser(id, userDetails);
    }

    @Override
    public int updateUserPassword(Long id, UpdatePasswordRequest passwordRequest) {
        val oldPassword = userRepository.getUserPassword(id);

        if (oldPassword.isPresent() && (oldPassword.get().getOldPassword().equals(passwordRequest.getOldPassword()))){
            return userRepository.updateUserPassword(id, passwordRequest);
        }
        return 0;
    }
}
