package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
public class ShipmentDTO {

    private Long id;
    private ShipmentStatus status;
    private ShipmentType type;
    private String shipmentManifestFileName;
    private String airwayInvoiceFileName;
    private Date departureDate;
    private Date arrivalDate;
    private Date createdOn;
    private Date updatedOn;
}
