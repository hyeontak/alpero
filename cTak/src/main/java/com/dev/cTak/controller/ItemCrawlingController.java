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
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=434524 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=434524 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=434524 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=481662 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=481662 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=481663 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=481663 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581817 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581817 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=627696 J4586 8067",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=627698 J8L50 8076",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=629904 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662106 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662106 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662121 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662430 J8191 8488",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702383 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702383 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702386 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702389 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702395 J84W2 9037",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702395 J85H0 8268",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729370 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729383 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729383 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729403 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729403 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729403 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744562 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744562 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744753 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=759354 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=759354 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=461997 08202 8170",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=479231 J8540 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=687118 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=687118 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=502088 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581809 J8CZ0 9068",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581842 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581842 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=603619 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=606641 J4586 8067",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=606711 J8L50 8076",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=629901 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=631761 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662108 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662108 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662110 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662110 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662132 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662429 J8196 8522",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=687118 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702393 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702394 J84W2 9037",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702394 J85H0 8268",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=703649 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=703649 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729363 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729373 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729373 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729402 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729402 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729402 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744423 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744545 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744599 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=745654 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=745654 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=758937 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=758937 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=660070 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=660070 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=152045 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=225985 I19A1 8061",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=325964 J85V5 8062",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=457122 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=457122 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=457127 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=457127 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=457127 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=525690 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=527095 J8F76 8521",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=527095 J8F77 8522",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581843 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=603608 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=606826 I0H11 8029",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=606826 J85V5 8062",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=607339 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=607339 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=627134 J4586 8067",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=627658 J8L50 8076",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=629827 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=629827 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=629828 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662140 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662177 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662177 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662184 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662188 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662194 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662194 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662194 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662256 J84W2 9037",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662256 J85H0 8268",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662428 J85H0 8268",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=679115 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=679262 I0H11 8029",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=679262 J85V5 8062",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702344 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702376 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702376 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702377 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702379 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702391 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=727729 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=727892 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=727892 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=727892 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729412 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729412 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729412 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729415 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729415 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729458 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729460 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744971 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=744971 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=745657 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=745696 JAAE4 9090",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=094074 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=094074 09850 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=481678 J8540 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=581830 J8540 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=582017 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=582033 J8CZ0 8062",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=609866 J4586 8067",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=609868 J8L50 8076",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=648604 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=652219 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=652219 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=652219 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662111 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662111 J8502 9000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662115 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662115 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=662427 J85H0 8268",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702380 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702390 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702390 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702691 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=702801 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=704246 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=704246 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729408 J8540 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729408 J8540 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=729408 J8568 9066",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=745649 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=745668 JAAE4 9090",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=748543 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=748543 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=759061 J8500 5702",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=761584 J8500 8000",
    		  "https://www.gucci.com/kr/ko/st/newsearchpage?searchString=524954 J8500 8000"

      };

      
      for(String value : list) {
          String url = value;
          
          Document document = Jsoup.connect(url).timeout(30000).get();
          Elements contents = document.select(".SearchResultGrid_productsCellr:eq(0)");

          
          //contents = document.select(".thumbnail");
          System.out.println(contents.select("a").attr("href"));
          //System.out.println(contents);
          //System.out.println(contents.select(".item_title > .prName_PrName").text());
          //System.out.println(contents.select("ul li:eq(1) span").text());
      }


          //int size = contents.select("ul li").size();
          //for(int i=0; i<size; i++) {
              
              //System.out.print(contents.select("ul li span").text());
              //System.out.print(">");
              //System.out.println(contents.select("ul li:eq("+i+") span").text());
          //}


      /*
       */
          
      
      //imageCrawling(list);

      
      //okMallProductCrawling(list,1);
      //okMallOptionCrawling(list,5);
      
      
      return "items";
      
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
 