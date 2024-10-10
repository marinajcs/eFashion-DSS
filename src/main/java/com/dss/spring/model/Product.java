package com.dss.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private double price;
	private String name;
	
	
	public Long getId() {
	    return id;
	}
	
	public double getPrice() {
	    return price;
	}
	
	public String getName() {
	    return name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
