package com.sakila.entity;

import java.io.Serializable;
import java.sql.Date;
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

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;



@Entity
@Indexed
@Table(name = "actor")
public class Actor implements Serializable {
	
	private static final long serialVersionUID = 8777826307070828182L;
	
	@Id
	@GeneratedValue
	@Column(name="actor_id")
	@Field
	@DocumentId
	private Integer actorId;
	
	@Field
	@Column(name="first_name")
	private String firstName;
	
	@Field
	@Column(name="last_name")
	private String lastName;
	

	@Column(name="last_update")
	private Date lastUpdated;

	@IndexedEmbedded
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "film_actor", catalog = "sakila", joinColumns = { 
			@JoinColumn(name = "actor_id", nullable = false, updatable = false)},inverseJoinColumns = { @JoinColumn(name = "film_id", 
					nullable = false, updatable = false) })
	private Set<Film> films;
		
	public Integer getActorId() {
		return actorId;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public Set<Film> getFilms() {
		return films;
	}

	public void setFilms(Set<Film> films) {
		this.films = films;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}	

	@Override
	public String toString() {
		return "Actor [id=" + actorId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", lastUpdated=" + lastUpdated + "]";
	}

}
