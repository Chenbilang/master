package com.cbl.aa.entity;

public class Spot {

	private Long id;
	private String province;
	private int province_id;
	private String  b_city;
	private int b_city_id;
	private String city;
	private int city_id;
	private String city_img;
	private String spot;
	private int spot_id;
	private String spot_url;
	private int spot_ct;
	private String spot_time;
	private int spot_gd;
	private int spot_bd;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getProvince_id() {
		return province_id;
	}
	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}
	public String getB_city() {
		return b_city;
	}
	public void setB_city(String b_city) {
		this.b_city = b_city;
	}
	public int getB_city_id() {
		return b_city_id;
	}
	public void setB_city_id(int b_city_id) {
		this.b_city_id = b_city_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public String getCity_img() {
		return city_img;
	}
	public void setCity_img(String city_img) {
		this.city_img = city_img;
	}
	public String getSpot() {
		return spot;
	}
	public void setSpot(String spot) {
		this.spot = spot;
	}
	public int getSpot_id() {
		return spot_id;
	}
	public void setSpot_id(int spot_id) {
		this.spot_id = spot_id;
	}
	public String getSpot_url() {
		return spot_url;
	}
	public void setSpot_url(String spot_url) {
		this.spot_url = spot_url;
	}
	public int getSpot_ct() {
		return spot_ct;
	}
	public void setSpot_ct(int spot_ct) {
		this.spot_ct = spot_ct;
	}
	public String getSpot_time() {
		return spot_time;
	}
	public void setSpot_time(String spot_time) {
		this.spot_time = spot_time;
	}
	public int getSpot_gd() {
		return spot_gd;
	}
	public void setSpot_gd(int spot_gd) {
		this.spot_gd = spot_gd;
	}
	public int getSpot_bd() {
		return spot_bd;
	}
	public void setSpot_bd(int spot_bd) {
		this.spot_bd = spot_bd;
	}
	@Override
	public String toString() {
		return "Spot [id=" + id + ", province=" + province + ", province_id="
				+ province_id + ", b_city=" + b_city + ", b_city_id="
				+ b_city_id + ", city=" + city + ", city_id=" + city_id
				+ ", city_img=" + city_img + ", spot=" + spot + ", spot_id="
				+ spot_id + ", spot_url=" + spot_url + ", spot_ct=" + spot_ct
				+ ", spot_time=" + spot_time + ", spot_gd=" + spot_gd
				+ ", spot_bd=" + spot_bd + "]";
	}
	
}
