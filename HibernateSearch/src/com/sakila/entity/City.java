package com.sakila.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
@Table(name = "city")
public class City  implements Serializable {
	@Id
	@GeneratedValue
	@Field
	@DocumentId
	@Column(name="city_id")
	private Integer cityId;
	
	private String city;
	
	/*@Column(name="country_id")
	private Integer countryId;*/
	
	@Field
	@Column(name="last_update")
	private Long lastUpdate;

	
	@ManyToOne(optional=false)
	@JoinColumn(name = "country_id", nullable=false, updatable=false)
	private Country country;
	
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	


	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", city=" + city + ", countryId="
				 + ", lastUpdate=" + lastUpdate + "]";
	}
	
	
}
