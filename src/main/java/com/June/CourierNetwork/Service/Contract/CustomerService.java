package com.June.CourierNetwork.Service.Contract;

public interface CustomerService {

     default String updateProfilePic(Long Id, String profilePic){
        return profilePic;
    }
}
