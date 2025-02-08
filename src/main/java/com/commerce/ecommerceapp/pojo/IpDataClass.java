package com.commerce.ecommerceapp.pojo;

import lombok.Data;

@Data
public class IpDataClass {
	
	/*{
  	ip=106.222.228.86, 	
	city=Hyderābād, 
	region=Telangana, 
	country=IN, 
	loc=17.3840,78.4564, 
	org=AS24560 Bharti Airtel Ltd., Telemedia Services, 
	postal=500001, 
	timezone=Asia/Kolkata
	}
*/
	private String ip;
	
	private String city;
	
	private String region;
	
	private String country;
	
	private String loc;
	
	private String org;
	
	private String postal;
	
	private String timezone;


}
