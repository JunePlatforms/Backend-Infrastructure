package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.Shipment;
import com.June.CourierNetwork.Repo.Contract.ShipmentRepository;
import com.June.CourierNetwork.Service.Contract.EmailService;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.June.CourierNetwork.Service.Contract.ProductService;
import com.June.CourierNetwork.Service.Contract.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final FileUploadService fileUploadService;
    private final ProductService productService;
    private final EmailService emailService;

    @Value("${airway.invoice.upload.dir}")
    private String airWayInvoiceUploadDirectory;

    @Value("${shipment.manifest.upload.dir}")
    private String shipmentManifestUploadDirectory;


    @Override
    public void createShipment(ShipmentDTO shipmentDTO, MultipartFile airwayInvoice, MultipartFile shipmentManifest) throws IOException {
        val shipmentId = shipmentRepository.createShipment(shipmentDTO);
        fileUploadService.uploadShipmentManifest(shipmentManifest, shipmentId,shipmentManifestUploadDirectory);
        fileUploadService.uploadAirWayInvoice(airwayInvoice, shipmentId, airWayInvoiceUploadDirectory);
    }

    @Override
    public void updateShipment(Long shipmentId, ShipmentDTO shipmentDTO) {
        shipmentRepository.updateShipment(shipmentId, shipmentDTO);
    }

    @Override
    public void deleteShipment(Long shipmentId) {
        shipmentRepository.deleteShipment(shipmentId);
    }

    @Override
    public List<Shipment> getAllShipments() throws IOException {
        val shipmentDTOS = shipmentRepository.getAllShipments();
        List<Shipment> shipments = new ArrayList<>();

        for (ShipmentDTO shipmentDTO : shipmentDTOS) {
            val airwayInvoice = fileUploadService.getFile(shipmentDTO.getAirwayInvoiceFileName(), airWayInvoiceUploadDirectory);
            val shipmentManifest = fileUploadService.getFile(shipmentDTO.getShipmentManifestFileName(), shipmentManifestUploadDirectory);
            val productDetails = productService.findProductDetailsByShipmentId(shipmentDTO.getId());

            shipments.add(Shipment.builder()
                    .id(shipmentDTO.getId())
                    .type(shipmentDTO.getType())
                    .shipmentManifest(shipmentManifest)
                    .airwayInvoice(airwayInvoice)
                    .departureDate(shipmentDTO.getDepartureDate())
                    .arrivalDate(shipmentDTO.getArrivalDate())
                    .status(shipmentDTO.getStatus())
                    .productDetails(productDetails)
                    .build());
        }
        return shipments;
    }

    @Override
    public Shipment findShipmentById(Long shipmentId) throws IOException {
        val shipmentDTO = shipmentRepository.findShipmentById(shipmentId).orElseThrow();

        val shipmentManifest = fileUploadService.getFile(shipmentDTO.getShipmentManifestFileName(), shipmentManifestUploadDirectory);
        val airwayInvoice = fileUploadService.getFile(shipmentDTO.getAirwayInvoiceFileName(), airWayInvoiceUploadDirectory);
        val productDetails = productService.findProductDetailsByShipmentId(shipmentId);

        return Shipment.builder()
                .id(shipmentDTO.getId())
                .type(shipmentDTO.getType())
                .shipmentManifest(shipmentManifest)
                .airwayInvoice(airwayInvoice)
                .departureDate(shipmentDTO.getDepartureDate())
                .arrivalDate(shipmentDTO.getArrivalDate())
                .status(shipmentDTO.getStatus())
                .productDetails(productDetails)
                .build();
    }

    @Override
    public void setShipmentType(Long shipmentId, ShipmentType shipmentType) {

    }

    @Override
    public void updateShipmentStatus(Long shipmentId, ShipmentStatus status) throws IOException {
        shipmentRepository.updateShipmentStatus(shipmentId, status);

        if (status.equals(ShipmentStatus.ARRIVED) || status.equals(ShipmentStatus.SHIPPED)) {
            List<ProductDetails> productDetails = productService.findProductDetailsByShipmentId(shipmentId);
            productDetails.forEach(productDetail -> emailService.sendProductUpdateEmail(productDetail.getId()));
        }
    }

    @Override
    public String getAirwayInvoiceFileName(Long shipmentId) {
        return shipmentRepository.getAirwayInvoiceFileName(shipmentId);
    }


}
