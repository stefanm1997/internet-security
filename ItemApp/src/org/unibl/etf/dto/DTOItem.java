package org.unibl.etf.dto;

import java.io.Serializable;

public class DTOItem implements Serializable {

	private String code, name, producer;
	private int idArtikl;
	private double price;

	public DTOItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DTOItem(String code, String name, String producer, double price, int idArtikl) {
		super();
		this.code = code;
		this.name = name;
		this.producer = producer;
		this.idArtikl = idArtikl;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public int getIdArtikl() {
		return idArtikl;
	}

	public void setIdArtikl(int idArtikl) {
		this.idArtikl = idArtikl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "DTOArtikl [code=" + code + ", name=" + name + ", producer=" + producer + ", idArtikl=" + idArtikl
				+ ", price=" + price + "]";
	}

}
