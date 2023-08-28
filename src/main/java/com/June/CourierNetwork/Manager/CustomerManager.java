package com.June.CourierNetwork.Manager;

import com.June.CourierNetwork.Model.Customer;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;



public class CustomerManager {

    @Autowired
    Customer customer;

    //    This will be set to like a silhouette of a profile pic upon first attempting to add a PP.
    private String defaultProfilePic;

    public String addProfilePicture(String newProfilePic) {
        String succcessfulSave;

        if (Objects.equals(customer.getProfilePic(), defaultProfilePic)) {
            customer.setProfilePic(newProfilePic);
            succcessfulSave = "Profile Picture Saved Successfully";
        } else {
            String oldProfilePic = customer.getProfilePic();
            customer.setProfilePic(newProfilePic);
            succcessfulSave = "Profile Picture Saved Successfully";
        }
        return succcessfulSave;
    }

    public void removeProfilePic(String profilePic) {

        if (!Objects.equals(customer.getProfilePic(), defaultProfilePic)) {
            customer.setProfilePic(defaultProfilePic);
        }

    }

    public void passwordReset(Long Id,String oldpassword,String newPassword){


    }

}