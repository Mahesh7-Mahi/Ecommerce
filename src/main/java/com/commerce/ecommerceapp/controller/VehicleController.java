package com.commerce.ecommerceapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ecommerceapp.entity.Vehicle;
import com.commerce.ecommerceapp.pojo.VehicleApiData;
import com.commerce.ecommerceapp.service.VehicleDynamicSerchingService;
import com.commerce.ecommerceapp.utils.ResponseData;

@RestController
@RequestMapping("/customers")
public class VehicleController {
	
	@Autowired
	private VehicleDynamicSerchingService vehicleDynamicSerchingService;
	
	@PostMapping("/vehicle/search")
	public ResponseEntity<?> vehicleSearchController(@RequestBody VehicleApiData vehicleApiData) {
		
		List<Vehicle> vehicles = vehicleDynamicSerchingService.dynamicVehicleSearchingService(vehicleApiData);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put(ResponseData.RESULT, ResponseData.SUCCESS);
		response.put(ResponseData.DATA, vehicles);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
