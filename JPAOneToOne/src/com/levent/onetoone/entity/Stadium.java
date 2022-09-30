package com.levent.onetoone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Stadium {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int yearOfOpening;
	private int capacity;
	
	@OneToOne(mappedBy = "stadium", orphanRemoval = true)
	private Team team;
	
	public Stadium() {
		super();
	}
	
	public Stadium(String name, int yearOfOpening, int capacity, Team team) {
		super();
		this.name = name;
		this.yearOfOpening = yearOfOpening;
		this.capacity = capacity;
		this.team = team;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearOfOpening() {
		return yearOfOpening;
	}

	public void setYearOfOpening(int yearOfOpening) {
		this.yearOfOpening = yearOfOpening;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Stadium [id = " + id + ", name =" + name + ", Team = " + team.getName() + "]";
	}
}
