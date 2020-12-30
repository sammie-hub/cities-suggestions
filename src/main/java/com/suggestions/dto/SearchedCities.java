package com.suggestions.dto;

public class SearchedCities  {
	
	private String name;
	private float longitude;
	private float latitude;
	private String score;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}
