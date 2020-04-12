package it.alex.telegram.bot.controller;


import io.swagger.annotations.*;
import it.alex.telegram.bot.dto.CityDTO;
import it.alex.telegram.bot.dto.UpdateCityDataDTO;
import it.alex.telegram.bot.exception.BotCityNotFoundException;
import it.alex.telegram.bot.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Api(value = "Cities service")
public class CityController {

    private final CityService cityService;

    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new city ", notes = "Use this method if you want to add a new city")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully add city"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void addNewCity(@ApiParam(value = "New city data") @Valid @RequestBody final CityDTO newCityRequest) {
        cityService.addCity(newCityRequest);
}

//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Operations with city", notes = "Use this method, if you want use  operations with city data")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successfully work with city data"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//    })
//    public String operationOrder(@ApiParam(value = "Operations city") @Valid @RequestBody final String operationOrderRequest) {
//        return cityService.operationCity(operationOrderRequest);
//    }

    @GetMapping(value = "/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View city", notes = "Use this method, if you want to view city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get city"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public CityDTO getCity(@PathVariable final Long cityId) throws BotCityNotFoundException {
        return cityService.getCity(cityId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all cities", notes = "Use this method, if you want to view all cities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get all cities"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<CityDTO> getAllOrder() {
        return cityService.getAllCity();
    }

    @PutMapping(value = "/{cityId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update city data", notes = "Use this method, if you want to update city data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update city data"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void updateStatusOrder(@Valid @RequestBody UpdateCityDataDTO updateRequest, @PathVariable final Long cityId) throws BotCityNotFoundException {
        cityService.updateCity(cityId, updateRequest);
    }


    @DeleteMapping(value = "/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete city", notes = "Use this method, if you want to delete city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete city"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void delCity(@PathVariable final Long cityId) throws BotCityNotFoundException {
        cityService.delCity(cityId);
    }
}