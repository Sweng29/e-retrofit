package com.retrofit.app.controller.v1;

import com.retrofit.app.controller.BaseController;
import com.retrofit.app.dto.ManufacturerDTO;
import com.retrofit.app.payload.request.ERetrofitPageRequest;
import com.retrofit.app.payload.request.ManufacturerPayload;
import com.retrofit.app.service.ManufacturerService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/manufacturers")
@Api(
        tags = {"Manufacturer controller"},
        value = "Operations pertaining to manufacturer.")
public class ManufacturerController extends BaseController {

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN})
    public ManufacturerDTO createManufacturer(@RequestBody ManufacturerPayload manufacturerPayload)
    {
        return manufacturerService.createManufacturer(manufacturerPayload);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN})
    public ManufacturerDTO updateManufacturer(@RequestBody ManufacturerPayload manufacturerPayload)
    {
        return manufacturerService.updateManufacturer(manufacturerPayload);
    }

    @GetMapping("/{manufacturerId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN,ROLE_USER})
    public ManufacturerDTO findManufacturerById(
            @Valid @Min(value = 1,message = "Manufacturer id can not be less then 1")
            @PathVariable Long manufacturerId)
    {
        return manufacturerService.findManufacturerById(manufacturerId);
    }

    @DeleteMapping("/{manufacturerId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN,ROLE_USER})
    public ManufacturerDTO deleteManufacturerById(
            @Valid @Min(value = 1,message = "Manufacturer id can not be less then 1")
            @PathVariable Long manufacturerId)
    {
        return manufacturerService.deleteManufacturerById(manufacturerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN,ROLE_USER})
    public List<ManufacturerDTO> findAllManufacturers(@RequestBody
            ERetrofitPageRequest manufacturerFilterAttributes)
    {
        return manufacturerService.findAllManufacturers(manufacturerFilterAttributes);
    }

}
