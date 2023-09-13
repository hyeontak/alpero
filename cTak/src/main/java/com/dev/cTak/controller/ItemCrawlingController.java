package com.dev.cTak.controller;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
    		  "https://www.adidas.co.kr/search?q=HT5760"
      };

      
      for(String value : list) {
          String url = value;
          
          Document document = Jsoup.connect(url).get();
          Elements contents = document.select("#glass-gdpr-default-consent-accept-button");
          
          contents = document.select(".sidebar___29cCJ");

          //contents = document.select(".thumbnail");
          System.out.println(contents.select(".product-description___1TLpA h1 span").text());
          //System.out.println(contents);
          //System.out.println(contents.select(".item_title > .prName_PrName").text());
          //System.out.println(contents.select("ul li:eq(1) span").text());
      }

      
      return "content/items";
      
   }
   
   private static void imageCrawling(String[] list) {
       String savePath = "D:/Downloads/crawling/";
       
       try {
           createDirectoryIfNotExists(savePath);
           for(String url : list) {
               Document document = Jsoup.connect(url).timeout(50000).get();
               Elements contents = document.select(".productDetail div:eq(0) img");

               for (Element imgElement : contents) {
                   String imgSrc = imgElement.attr("src");
                 
                   // URL에서 공백을 "%20"으로 치환하여 처리
                   imgSrc = imgSrc.replace(" ", "%20");
                 
                   System.out.println(imgSrc);
                   try {
                        downloadImage(imgSrc, savePath);
                    } catch (IOException e) {
                        // 이미지 다운로드 중 에러가 발생하면 무시하고 다음 이미지를 다운로드합니다.
                        e.printStackTrace();
                    }
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       
   }
   
   private static void createDirectoryIfNotExists(String directoryPath) throws IOException {
       Path path = Paths.get(directoryPath);
       if (!Files.exists(path)) {
           Files.createDirectories(path);
       }
   }
  
    private static void downloadImage(String imageUrl, String savePath) throws IOException {
       URL url = new URL(imageUrl);
       
       InputStream is = url.openStream();
       
       // 이미지 크기를 가져오기 위해 BufferedImage를 사용합니다.
        BufferedImage image = ImageIO.read(url);

        int height = image.getHeight();

        // 이미지의 높이가 1px 이하인 경우 다운로드를 하지 않습니다.
        if (height <= 1) {
            return;
        }
        
       String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
       
       // %20을 _로 치환합니다.
       fileName = fileName.replace("%20", "_");
       
       // 파일명에서 맨 뒤 세글자를 추출합니다.
       String suffix = fileName.substring(fileName.length() - 7, fileName.length() - 4);
       
       // 맨 뒤 세글자가 "_01", "_02", "_03"과 같은 형식이면 숫자만 추출하여 아라비아 숫자로 변환합니다.
       if (suffix.matches("_0[1-9]")) {
           String arabicNumber = suffix.substring(1);
           
           // 아라비아 숫자로 변환한 뒤 다시 두 자리로 만듭니다.
           arabicNumber = String.valueOf(Integer.parseInt(arabicNumber));
           
           fileName = fileName.substring(0, fileName.length() - 7) + "_" + arabicNumber + ".jpg";
       }
       
       
       try (OutputStream os = new FileOutputStream(savePath + fileName)) {
           byte[] b = new byte[2048];
           int length;
           while ((length = is.read(b)) != -1) {
               os.write(b, 0, length);
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           is.close();
       }
       
   }
   
    private static void okMallProductCrawling(String[] list, int num) {
       
       int section = num;               // 20,40,60,80,100 구간 설정
       int startIndex = (section - 1) * 20;
       int endIndex = Math.min(startIndex + 20, list.length);

       try {
           int maxRetries = 3;
           
           for (int i = startIndex; i < endIndex; i++) {
               String url = list[i];
               int retryCount = 0;
               boolean success = false;
               while (!success && retryCount < maxRetries) {
                   try {
                       Document document = Jsoup.connect(url).timeout(30000).get();
                       Elements contents = document.select(".category_wrap");
                      
                       System.out.print(contents.select(".show_category_list").text()+"@");
                      
                       contents = document.select(".prd_option");
                      
                       System.out.print(contents.select(".prd_name").text()+"@");
                       System.out.print(contents.select(".prd_name_more").text()+"@");
                       System.out.print(contents.select(".last_price .price").text()+"@");
                       System.out.print(contents.select(".value_price .price").text());
                       System.out.println();
                      
                       success = true;
                   } catch (IOException e) {
                       System.err.println("Error fetching URL: " + e.getMessage());
                       retryCount++;
                       Thread.sleep(3000);
                   }
               }
           }
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
   
    private static void okMallOptionCrawling(String[] list, int num) {

        int section = num;              // 20,40,60,80,100 구간 설정
        int startIndex = (section - 1) * 20;
        int endIndex = Math.min(startIndex + 20, list.length);

        try {
            int maxRetries = 3;
           
            for (int i = startIndex; i < endIndex; i++) {
                String url = list[i];
                int retryCount = 0;
                boolean success = false;
                while (!success && retryCount < maxRetries) {
                    try {
                        Document document = Jsoup.connect(url).timeout(30000).get();
                        Elements selectOptions = document.select("tr[name=selectOption]");
                       
                        
                        int count = 0;
                        // 각 .tr name="selectOption" 요소들에서 .t_center 요소들 추출
                        for (Element selectOption : selectOptions) {
                            // .t_center 요소들 가져오기
                            Elements tCenters = selectOption.select(".t_center");
        
                            // .t_center 요소들 중에서 네 번째 요소 제외하고 세 개씩만 출력
                            for (int j = 0; j < tCenters.size() - 1; j += 3) {
                                Element tCenter1 = tCenters.get(j);
                                Element tCenter2 = tCenters.get(j + 1);
                                Element tCenter3 = tCenters.get(j + 2);

                                System.out.print((i+1) + "@");
                                
                                ++count;
                                System.out.print(tCenter1.text() + "@");
                                ++count;
                                System.out.print(tCenter2.text() + "@");
                                ++count;
                                System.out.print(tCenter3.text());
                                
                                System.out.println();
                            }
                        }
                        success = true;
                    } catch (IOException e) {
                        System.err.println("Error fetching URL: " + e.getMessage());
                        retryCount++;
                        Thread.sleep(3000);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
   
}
 