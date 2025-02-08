package com.commerce.ecommerceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.commerce.ecommerceapp.pojo.IpDataClass;


@Service
public class IpInfoService {
	
	
	@Value("${ipAddress.token}")
	private String token;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	public IpDataClass ipAddressInfo(String ipAddress) {
		
		//https://ipinfo.io/8.8.8.8?token=937300642d7128
		
		ipAddress = "106.222.228.86";
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String ipBody="";
		
		HttpEntity<Object> httpEntity = new HttpEntity<>(ipBody,headers);
		
		String url= "https://ipinfo.io/"+ipAddress+"?"+token;
		
		ResponseEntity<IpDataClass> responseEntity = restTemplate.exchange(url,HttpMethod.GET,httpEntity,IpDataClass.class);
		
		return responseEntity.getBody();
		
		/*{
		  	ip=106.222.228.86, 	
			city=Hyderābād, 
			region=Telangana, 
			country=IN, 
			loc=17.3840,78.4564, 
			org=AS24560 Bharti Airtel Ltd., 
			Telemedia Services, 
			postal=500001, 
			timezone=Asia/Kolkata
			}
		*/
		
		
		
	}

}
