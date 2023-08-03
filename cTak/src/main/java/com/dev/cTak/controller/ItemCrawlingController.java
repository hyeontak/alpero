package com.dev.cTak.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dev.cTak.service.ItemCrawlingService;

@Controller
public class ItemCrawlingController {

   private final ItemCrawlingService itemCrawlingService;
   
   public ItemCrawlingController(ItemCrawlingService itemCrawlingService) {
      this.itemCrawlingService = itemCrawlingService;
   }
   
   @GetMapping("/crawling")
   public String crawling(Model model) throws Exception{
      /**************************************************************************/
      /**************               START                   **************/
      
      String[] list = {
    		  "https://www.timemecca.co.kr/product/아페쎄-coezc-h26177-aab-white-oliver-올리버-남성-긴팔티-트랜드메카/56899/category/2570/display/1/",
    		  "https://www.timemecca.co.kr/product/아페쎄-coezc-h26177-iak-dark-navy-oliver-올리버-남성-긴팔티-트랜드메카/56900/category/2570/display/1/"
      };
      String savePath = "D://Downloads//crawling";
      
      for(String value : list) {
         String url = value;
         Document document = Jsoup.connect(url).timeout(30000).get();
         Elements contents = document.select(".productDetail");

         String imgEle = contents.select("div:eq(0) img").attr("src");
         
         downloadImage(imgEle,savePath);
         //System.out.println(contents.select("h1").text());
         
         //contents = document.select(".thumbnail");
         //System.out.println(contents.select(".prdList__item:eq(0) a").attr("href"));
         //System.out.println(contents.select("ul li:eq(1) span").text());
         
         
         //int size = contents.select("ul li").size();
         //for(int i=0; i<size; i++) {
             
             //System.out.print(contents.select("ul li span").text());
             //System.out.print(">");
             //System.out.println(contents.select("ul li:eq("+i+") span").text());
         //}

         //System.out.println("==================================");
         
      }
      
      return "items";
   }
   
   private static void downloadImage(String imageUrl, String savePath) throws IOException {
       URL url = new URL(imageUrl);
       InputStream is = url.openStream();
       OutputStream os = new FileOutputStream(savePath);

       byte[] b = new byte[2048];
       int length;
       while ((length = is.read(b)) != -1) {
           os.write(b, 0, length);
       }

       is.close();
       os.close();
   }
}
 