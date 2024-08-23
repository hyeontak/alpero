package com.dev.cTak.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ItemVo {
	private String itemCode = "";		//상품코드
	private String shopCode = "";		//쇼핑몰코드
	private String title = ""; 			//제목
	private String price1 = "";			//권장판매가 
	private String price2 = "";			//도매가 
	private String alperoCode1 = "";			//알페로코드1 
	private String alperoCode2 = "";			//알페로코드2 
	
	private String optionText = "";			//옵션 종류
	private String beForeOoptionText = "";			//옵션 종류
	
	private String thumbnailLink = ""; 	//썸네일 이미지 경로
	private String detailLink = ""; 	//상세이미지 경로
	
	private String regDate = "";
	private String updDate = "";
	private String usr = "";
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice1() {
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
	}
	public String getPrice2() {
		return price2;
	}
	public void setPrice2(String price2) {
		this.price2 = price2;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
	
	public String getBeForeOoptionText() {
		return beForeOoptionText;
	}
	public void setBeForeOoptionText(String beForeOoptionText) {
		this.beForeOoptionText = beForeOoptionText;
	}
	public String getThumbnailLink() {
		return thumbnailLink;
	}
	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}
	public String getDetailLink() {
		return detailLink;
	}
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getAlperoCode1() {
		return alperoCode1;
	}
	public void setAlperoCode1(String alperoCode1) {
		this.alperoCode1 = alperoCode1;
	}
	public String getAlperoCode2() {
		return alperoCode2;
	}
	public void setAlperoCode2(String alperoCode2) {
		this.alperoCode2 = alperoCode2;
	}
	
}
