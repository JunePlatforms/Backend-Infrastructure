package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String profilePic;
    private PDDocument viewInvoice;




    public String getProfilePic() {
        return profilePic;
    }

    public PDDocument getInvoice(){return viewInvoice;}

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setViewInvoice(PDDocument viewInvoice) {
        this.viewInvoice = viewInvoice;
    }
}
