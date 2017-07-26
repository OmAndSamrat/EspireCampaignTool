package com.espire.campaign.microsite;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;


class Geo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6845770377919134036L;
	private String ip;
	private String country_code;
	private String country_name;
	private String region_code;
	
	private String region_name;
	private String city;
	private String zip_code;
	private String time_zone;
	
	private Double latitude;
	private Double longitude;
	
	private int metro_code;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getRegion_code() {
		return region_code;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getMetro_code() {
		return metro_code;
	}

	public void setMetro_code(int metro_code) {
		this.metro_code = metro_code;
	}
	
	
	
}

public class IptoLangConvertor {
	public static Geo getLatLong(String ipAddress) {
		Geo geo = new Geo();
			ObjectMapper objectmapper = new ObjectMapper();
			try {
				geo = objectmapper.readValue(new URL("http://freegeoip.net/json/"+ipAddress), Geo.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return geo;
	}

	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		getLatLong("");
	}
}