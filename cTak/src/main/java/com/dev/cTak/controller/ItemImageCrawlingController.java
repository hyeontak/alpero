package com.dev.cTak.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dev.cTak.service.ItemCrawlingService;

@Controller
public class ItemImageCrawlingController {

   private final ItemCrawlingService itemCrawlingService;
   
   public ItemImageCrawlingController(ItemCrawlingService itemCrawlingService) {
      this.itemCrawlingService = itemCrawlingService;
   }
   
   @GetMapping("/crawlingimage")
   public String crawling(Model model) throws Exception{
      /**************************************************************************/
      /**************               START                   **************/
      
      String[] list = {
    		    "MJP022A 01455 415",
    	        "MJP022A 01455 025",
    	        "MJS201A J0049 057",
    	        "MKC171A 00219 415",
    	        "MJD102X 07259 035",
    	        "MJS204A J0046 100",
    	        "MJS201A J0049 452",
    	        "MJP169A J0046 415",
    	        "MJC328E 00572 035",
    	        "MJS212A J0061 982",
    	        "MKC171A 00219 035",
    	        "MJP022A 01455 100",
    	        "MKA202A 00219 415",
    	        "MJT355A J0027 133",
    	        "MSC001A 00626 415",
    	        "FKC334A Y1014 415",
    	        "MWL272E 00111 450",
    	        "FOC320A 03793 424",
    	        "MKP056A Y3007 415",
    	        "MCX039A 00198 001",
    	        "MSC001A 00626 015",
    	        "MJD102X 07259 415",
    	        "MWL150E 06177 100",
    	        "MJS056A 00050 415",
    	        "MWL150E 03113 480",
    	        "MJT086A 03377 055",
    	        "MJS205A J0053 452",
    	        "MFD054G 00198 001",
    	        "MJS010A 01454 055",
    	        "FJS135A J0051 025",
    	        "MKC310F Y3007 982",
    	        "MWL010E 03113 100",
    	        "MFD180B 03050 415",
    	        "MTC159A 00626 015",
    	        "MTU191B F0017 996",
    	        "MKA458A Y3006 035",
    	        "MWL374C 05371 133",
    	        "MFD261A 07989 215",
    	        "MKA469B Y1506 424",
    	        "FKC207A 00219 415",
    	        "MJQ008H 00535 461",
    	        "FFD120B 05584 100",
    	        "MKC171A 00219 100",
    	        "MKC001A 00011 415",
    	        "FKA001A 00011 035",
    	        "MKC311F Y3007 982",
    	        "MFD180A 03050 100"

      };
      
      for(String value : list) {
          
    	  
          //Document document = Jsoup.connect(url).timeout(30000).get();
          //Elements contents = document.select("._1Qhom");

          
          //contents = document.select(".thumbnail");
          //System.out.println(contents.select("a").attr("href"));
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
          
      
      imageCrawling(list);
      
      return "items";
      
   }
   
   private static void imageCrawling(String[] list) {
	   String savePath = "D:/Downloads/crawling/thom/";
       
	   try {
		   createDirectoryIfNotExists(savePath);
	        for (String model : list) {
	        	// 모델명 사이의 공백을 %20으로 치환합니다.
	            String encodedModel = URLEncoder.encode(model, StandardCharsets.UTF_8.toString()).replace("+", "%20");

	            // 모델명으로 이미지 URL을 구성합니다.
	            String imageUrl = "https://sineorb3.openhost.cafe24.com/Design/web19/web/upload/WATCH/THOM%20BROWNE/" + encodedModel + "_01.jpg";

	            // 이미지 다운로드 메서드 호출
	            downloadImage(imageUrl, savePath);
	            System.out.println(imageUrl);
	            
	            // 추가로 이미지_02.jpg, 이미지_03.jpg 등도 조회하고 싶다면 아래와 같이 반복문으로 조회할 수 있습니다.
	            for (int i = 2; i <= 5; i++) {
	                imageUrl = "https://sineorb3.openhost.cafe24.com/Design/web19/web/upload/WATCH/THOM%20BROWNE/" + encodedModel + "_0" + i + ".jpg";
	                downloadImage(imageUrl, savePath);
		            System.out.println(imageUrl);
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
	   try {
	        URL url = new URL(imageUrl);
	        try (InputStream is = url.openStream()) {
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
	                // 이미지 다운로드 중 에러가 발생하면 무시하고 다음 이미지를 다운로드합니다.
	                e.printStackTrace();
	            }
	        }
	    } catch (Exception e) {
	        // 이미지가 없는 경우도 무시하고 다음 작업을 진행합니다.
	        System.out.println("Image not found: " + imageUrl);
	    }
       
   }
   
}
 