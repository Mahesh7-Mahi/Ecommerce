package com.commerce.ecommerceapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.commerce.ecommerceapp.entity.Vehicle;
import com.commerce.ecommerceapp.pojo.VehicleApiData;

@SuppressWarnings("deprecation")
@Repository
public class VehicleRepo {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	static class VehicleRowMapper implements RowMapper<Vehicle>{
		@Override
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Vehicle vehicle = new Vehicle();
			
			vehicle.setYear(rs.getInt("year"));
			vehicle.setBrand(rs.getString("brand"));
			vehicle.setModel(rs.getString("model"));
			vehicle.setVehicleId(rs.getInt("vehicle_id"));
			vehicle.setTrim(rs.getString("trim"));
			
			return vehicle;
		}
	}
	
	public List<Vehicle> dynamicVehicleSearch(VehicleApiData vehicleApiData) {
		
		StringBuilder sqlBuilder = new StringBuilder("select * from vehicles where");
		
		List<Object> parameterList = new ArrayList<Object>();
		
	    int wordSearch = 0;
		
		if(vehicleApiData.getYear() != 0) {
			wordSearch = wordSearch+1;
			sqlBuilder.append(" year= ?");
			parameterList.add(vehicleApiData.getYear());
		}
		if(vehicleApiData.getBrand() != null) {
			if(wordSearch == 0) {
				sqlBuilder.append(" brand= ?");
			}else {
				sqlBuilder.append(" and brand= ?");
			}	
			parameterList.add(vehicleApiData.getBrand());
			
		}
		if(vehicleApiData.getModel() != null) {
			if(wordSearch == 0) {
				sqlBuilder.append(" model= ?");
			}else {
				sqlBuilder.append(" and model= ?");
			}	
			parameterList.add(vehicleApiData.getModel());
		}
		
		List<Vehicle> vehicles = jdbcTemplate.query(sqlBuilder.toString(), parameterList.toArray(), new VehicleRowMapper());
		
		return vehicles;
	}

}
