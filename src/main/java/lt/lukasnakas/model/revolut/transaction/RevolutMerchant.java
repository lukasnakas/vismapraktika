package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutMerchant {

	private String name;
	private String city;

	@JsonProperty("category_code")
	private String categoryCode;

	private String country;

	public RevolutMerchant() {
	}

	public RevolutMerchant(String name, String city, String categoryCode, String country) {
		this.name = name;
		this.city = city;
		this.categoryCode = categoryCode;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
