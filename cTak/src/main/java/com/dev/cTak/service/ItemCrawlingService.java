package com.dev.cTak.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.dev.cTak.vo.ItemVo;

import jakarta.annotation.PostConstruct;

@Service
public class ItemCrawlingService {

	private static String URL = "https://fromvi.com/product/detail.html?product_no=1194&cate_no=1&display_group=";
	
	@PostConstruct
	public ItemVo getItemsData() throws IOException{
		
		
		
		Jsoup.connect("https://fromvi.com").get();
		
		Connection.Response loginPageResponse = Jsoup.connect("https://fromvi.com/exec/front/Member/login/")
				.timeout(3000)
				.data("member_id","alpero1122","member_passwd","pepperlavu0315")
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
				.method(Connection.Method.POST)
				.execute();

		Map<String, String> cookies = loginPageResponse.cookies();

		//List<ItemVo> itemList = new ArrayList<ItemVo>();
		
		Document document = Jsoup.connect(URL).cookies(loginPageResponse.cookies()).get();
		
        Elements contents = document.select(".infoArea");
        ItemVo items = new ItemVo();
//		for(Element element : contents) {
//			
//		}
		items.setTitle(contents.select("h3").text());
		items.setPrice1(contents.select("#span_product_price_custom").text());
		items.setPrice2(contents.select("#span_product_price_text").text());
		
		return items;
	}
}