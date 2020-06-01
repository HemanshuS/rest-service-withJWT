/**
 * 
 */
package com.demo.entities;

import java.io.Serializable;

/**
 * @author Himanshu
 *
 */

public class Appliance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3721032040992587845L;
	private String id;
	private String serialNo;
	private String model;
	private String brand;
	private String dateBought;
	private Status status;
	private Integer version;
	
	public Appliance() {
		super();
	}
	
	
	
	public Appliance(String id, String serialNo,String brand, String model, String dateBought, Status status) {
		super();
		this.id = id;
		this.serialNo = serialNo;
		this.model = model;
		this.brand = brand;
		this.dateBought = dateBought;
		this.status = status;
	}



	public String getId() {
		return id;
	}

	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDateBought() {
		return dateBought;
	}
	public void setDateBought(String dateBought) {
		this.dateBought = dateBought;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Integer getVersion() {
		return version ==  null?1:version ;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setId(String id) {
		this.id = id;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appliance other = (Appliance) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}
	
	
	
}
