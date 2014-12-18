package com.sakila.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "country")
public class Country implements Serializable {

	@Id
	@GeneratedValue
	@Field(store=Store.YES)
	@DocumentId
	@Column(name="country_id")
	private Integer countryId;
	
	@Field(store=Store.YES)
	private String country;
	
	@Field
	@Column(name="last_update")
	private Long lastUpdate;

	/*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "city", catalog = "sakila", joinColumns = { 
			@JoinColumn(name = "country_id", nullable = false, updatable = false)},inverseJoinColumns = { @JoinColumn(name = "city_id", 
	
					nullable = false, updatable = false) })*/
	@IndexedEmbedded
	@OneToMany(targetEntity = City.class, fetch = FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name="country_id") // join column is in table for Order
	private Set<City> cities;
	
	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", country=" + country
				+ ", lastUpdate=" + lastUpdate + "]";
	}
	
	
}
