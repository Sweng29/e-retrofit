package com.retrofit.app.service;

import com.retrofit.app.dto.ManufacturerDTO;
import com.retrofit.app.model.Manufacturer;
import com.retrofit.app.payload.request.ERetrofitPageRequest;
import com.retrofit.app.payload.request.ManufacturerPayload;
import java.util.List;

public interface ManufacturerService {
    ManufacturerDTO createManufacturer(ManufacturerPayload manufacturerPayload);
    ManufacturerDTO updateManufacturer(ManufacturerPayload manufacturerPayload);
    ManufacturerDTO findManufacturerById(Long manufacturerId);
    Manufacturer findManufacturerGivenId(Long manufacturerId);
    ManufacturerDTO deleteManufacturerById(Long manufacturerId);
    List<ManufacturerDTO> findAllManufacturers(ERetrofitPageRequest filterAttributes);
}
