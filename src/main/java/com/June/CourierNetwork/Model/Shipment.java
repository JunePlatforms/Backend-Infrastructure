package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    private Long id;
    private ShipmentStatus status;
    private ShipmentType type;
    private byte[] shipmentManifest;
    private byte[] airwayInvoice;
    private Date departureDate;
    private Date arrivalDate;
    private List<ProductDetails> productDetails;
    private Date createdOn;
    private Date updatedOn;
}
