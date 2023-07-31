package com.dev.cTak.controller;

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
public class Alpero2Controller {

   private final ItemCrawlingService itemCrawlingService;
   
   public Alpero2Controller(ItemCrawlingService itemCrawlingService) {
      this.itemCrawlingService = itemCrawlingService;
   }
   
   @GetMapping("/alpero")
   public String crawling(Model model) throws Exception{
      /**************************************************************************/
      /**************               START                   **************/
      //url = url.replaceAll("%3A", ":");
      //url = url.replaceAll("%3F", "?");
      //url = url.replaceAll("%2F", "/");
      
      Jsoup.connect("https://fromvi.com").get();
      String[] list = {
    		  "https://www.fromvi.com/product/detail.html?product_no=591"
      };
      
      Connection.Response loginPageResponse = Jsoup.connect("https://fromvi.com/exec/front/Member/login/")
              .timeout(3000)
              .data("member_id","alpero1122","member_passwd","pepperlavu0315")
              .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
              .method(Connection.Method.POST)
              .execute();
      
      for(String value : list) {
         String url = value;
    	 Map<String, String> cookies = loginPageResponse.cookies();
         Document document = Jsoup.connect(url).cookies(loginPageResponse.cookies()).get();
         Elements contents = document.select(".infoArea");

         //contents = document.select(".thumbnail");


         //System.out.println(contents); 
         System.out.println(contents.select("h3").text());
         System.out.println(contents.select("tr:eq(3)").text());
         System.out.println(contents.select("tr:eq(4)").text());
    	 
         System.out.println(contents.select(".xans-product-option th").text());
         
         int size = contents.select(".xans-product-option th").size();
         System.out.println("옵션1:"+size);
         
    	 for(int i=0; i<size; i++) {
    		 
    		 int size2 = contents.select(".xans-product-option td option").size();
             System.out.println("옵션2:"+size2);
             
        	 //System.out.print(contents.select("ul li span").text());
        	 //System.out.println(contents.select("ul li:eq(" + i + ") span").text());
         }

         //System.out.println("test"+contents.select(".xans-product-option td select option:eq(2)").text());
         
         //System.out.println(contents.select("a").attr("href"));
    	 //System.out.println(contents.select("ul li:eq(1) span").text());
         
      }


      
      /**************************************************************************/
      /**************************************************************************/
        

      ItemVo itemList = itemCrawlingService.getItemsData();
      
      model.addAttribute("items", itemList);
      
      return "items";
   }
}
 