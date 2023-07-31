package com.dev.cTak.vo;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemVo {
	private String title = ""; 			//제목
	private String price1 = "";			//권장판매가 
	private String price2 = "";			//도매가 
	
	private String sele = "";
	
	private String imgThumbLink = ""; 	//썸네일 이미지 경로
	private String imgDetailLink = ""; 	//상세이미지 경로
	
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
	public String getSele() {
		return sele;
	}
	public void setSele(String sele) {
		this.sele = sele;
	}
	public String getImgThumbLink() {
		return imgThumbLink;
	}
	public void setImgThumbLink(String imgThumbLink) {
		this.imgThumbLink = imgThumbLink;
	}
	public String getImgDetailLink() {
		return imgDetailLink;
	}
	public void setImgDetailLink(String imgDetailLink) {
		this.imgDetailLink = imgDetailLink;
	}
}
