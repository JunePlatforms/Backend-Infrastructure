package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.Shipment;

import java.io.IOException;
import java.util.List;

public interface ShipmentService {
    void createShipment(ShipmentDTO shipmentDTO);

    void updateShipment(Long shipmentId, ShipmentDTO shipmentDTO);

    void deleteShipment(Long shipmentId);

    List<Shipment> getAllShipments() throws IOException;

    Shipment findShipmentById(Long shipmentId) throws IOException;

    void setShipmentType(Long shipmentId, ShipmentType shipmentType);

    void updateShipmentStatus(Long shipmentId, ShipmentStatus status);
    String getAirwayInvoiceFileName(Long shipmentId);

}
