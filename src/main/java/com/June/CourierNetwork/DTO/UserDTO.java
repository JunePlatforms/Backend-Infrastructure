package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
public class UserDTO {

    private Courier courier;
    private Administrator administrator;
    private WarehouseClerk warehouseClerk;
    private Customer customer;

    public UserDTO() {
        // creates an empty UserDTO
    }
}
