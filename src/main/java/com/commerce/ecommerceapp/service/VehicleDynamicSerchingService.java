package com.commerce.ecommerceapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ecommerceapp.entity.Vehicle;
import com.commerce.ecommerceapp.pojo.VehicleApiData;
import com.commerce.ecommerceapp.repository.VehicleRepo;

@Service
public class VehicleDynamicSerchingService {
	
	@Autowired
	private VehicleRepo vehicleRepo;
	
	public List<Vehicle> dynamicVehicleSearchingService(VehicleApiData vehicleApiData) {
		
		return vehicleRepo.dynamicVehicleSearch(vehicleApiData);
		
	}

}
