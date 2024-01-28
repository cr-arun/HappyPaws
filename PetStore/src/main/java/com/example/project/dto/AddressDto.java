package com.example.project.dto;

public class AddressDto {
	private String houseno;
	private String street;
	private String city;
	private String state;
	private String pincode;

	public AddressDto() {
		super();
	}

	public AddressDto(String houseno, String street, String city, String state, String pincode) {
		super();
		this.houseno = houseno;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public String getHouseno() {
		return houseno;
	}

	public void setHouseno(String houseno) {
		this.houseno = houseno;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return houseno + "," + street + "," + city + "," + state + "," + pincode + ".";
	}

}
