package com.ai.azure.vm.model;

public class VMModel {

	private int id;
	private String name;
	private String location;
	private String vmSize;
	private String resourceGroup;
	private String classification;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVmSize() {
		return vmSize;
	}

	public void setVmSize(String vmSize) {
		this.vmSize = vmSize;
	}

	public String getResourceGroup() {
		return resourceGroup;
	}

	public void setResourceGroup(String resourceGroup) {
		this.resourceGroup = resourceGroup;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
}
