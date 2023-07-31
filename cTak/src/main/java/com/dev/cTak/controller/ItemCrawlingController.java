package com.dev.cTak.controller;

import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dev.cTak.service.ItemCrawlingService;
import com.dev.cTak.vo.ItemVo;

@Controller
public class ItemCrawlingController {

	private final ItemCrawlingService itemCrawlingService;
	
	public ItemCrawlingController(ItemCrawlingService itemCrawlingService) {
		this.itemCrawlingService = itemCrawlingService;
	}
	
	@GetMapping("/crawling")
	public String crawling(Model model, String url) throws Exception{
		
		//url = "https://fromvi.com/product/detail.html?product_no=1194&cate_no=1&display_group=";
		
		/**************************************************************************/
		/**************					START						 **************/
		
		Jsoup.connect("https://fromvi.com").get();
		
		Connection.Response loginPageResponse = Jsoup.connect("https://fromvi.com/exec/front/Member/login/")
				.timeout(3000)
				.data("member_id","alpero1122","member_passwd","pepperlavu0315")
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
				.method(Connection.Method.POST)
				.execute();
		
		Map<String, String> cookies = loginPageResponse.cookies();
		Document document = Jsoup.connect(url).cookies(loginPageResponse.cookies()).get();
		
        Elements contents = document.select(".infoArea");
        ItemVo items = new ItemVo();
		
        items.setTitle(contents.select("h3").text());
		items.setPrice1(contents.select("#span_product_price_custom").text());
		items.setPrice2(contents.select("#span_product_price_text").text());
		
		
		/**************************************************************************/
		/**************************************************************************/
		System.out.println(url);
		System.out.println(items.getTitle());
		System.out.println(items.getPrice1());
		//ItemVo itemList = itemCrawlingService.getItemsData();
		
		//model.addAttribute("items", itemList);
		
		return "items";
	}
}
 