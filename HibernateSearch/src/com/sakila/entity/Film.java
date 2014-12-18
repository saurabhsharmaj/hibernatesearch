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
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "film")
public class Film  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@DocumentId
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name="film_id")
	private Integer filmId;
	private String title;
	private String description;
	
	@Field
	@Column(name="release_year")
	private Long year;
	
	@Field
	@Column(name="language_id")
	private Integer languageId;

	@Field
	@Column(name="original_language_id", nullable=true)
	private Integer originalLanguageId;

	@Field
	@Column(name="rental_duration")
	private Integer rentalDuration;

	@Field
	@Column(name="rental_rate")
	private Double rentalRate;
	private Integer length;

	@Field
	@Column(name="replacement_cost")
	private Double replacementCost;


	private String rating;

	@Field
	@Column(name="special_features")
	private String specialFeatures;


	@Column(name="last_update")
	private Long lastUpdate;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "film_actor", catalog = "sakila", joinColumns = { 
			@JoinColumn(name = "film_id", nullable = false, updatable = false)},inverseJoinColumns = { @JoinColumn(name = "actor_id", 
					nullable = false, updatable = false) })
	private Set<Actor> actors;
	
	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getOriginalLanguageId() {
		return originalLanguageId;
	}

	public void setOriginalLanguageId(Integer originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}

	public Integer getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(Integer rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public Double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(Double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Double getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(Double replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", title=" + title + ", description="
				+ description + ", year=" + year + ", languageId=" + languageId
				+ ", orginalLanguageId=" + originalLanguageId
				+ ", rentalDuration=" + rentalDuration + ", rentalRate="
				+ rentalRate + ", length=" + length + ", replacementCost="
				+ replacementCost + ", rating=" + rating + ", specialFeatures="
				+ specialFeatures + ", lastUpdate=" + lastUpdate + "]";
	}

}
