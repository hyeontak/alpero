package com.dev.cTak.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dev.cTak.vo.ItemVo;

@Controller
public class ItemCrawlingSellController {

    private String WEB_STR = "D:\\work\\chromedriver_win32\\chromedriver.exe";

    @GetMapping("/crawlingsell")
    public String crawling() {
        HashMap map = new HashMap();
        System.setProperty("webdriver.chrome.driver", "D:\\work\\chromedriver-win32\\chromedriver.exe");
        /*
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        */
        WebDriver driver = new ChromeDriver();
        List<ItemVo> list = new ArrayList<>();
        
        
        list = winusCrawling(driver);
        //list = okMallCrawling(driver);
        //list = officialMallCrawling(driver);
        //map.put("items",web_fromvi(driver));


        return "items";
    }

    private static List<ItemVo> winusCrawling(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();

        try{
        	
            String[] list = {
            		"https://www.gucci.com/kr/ko/pr/jewelry-watches/fine-jewelry/fine-bracelets/gg-running-cuff-with-diamonds-p-481662J85408000"


            };

            for(String url: list){
            	driver.get(url);
            	Thread.sleep(2000);

            	ItemVo item = new ItemVo();
                //new Actions(driver).sendKeys(Keys.END).perform();
            	//Thread.sleep(1000);
            	
            	
            	
            	boolean parsingStarted = false;
                List<Integer> parsedRelValues = new ArrayList<>();

            	List<WebElement> divElements = driver.findElements(By.cssSelector(".product-detail-carousel .slick-track > div"));

            	for (WebElement div : divElements) {
                    int relValue = Integer.parseInt(div.getAttribute("rel"));
                    
                    
                    if (!parsedRelValues.contains(relValue)) {
                        parsedRelValues.add(relValue);
                        
                    }
                    
                }
            	
            	Collections.sort(parsedRelValues);
            	
            	int Gnum=1;
            	
            	for(Integer value : parsedRelValues) {
            		WebElement element = driver.findElement(By.cssSelector(".product-detail-carousel .slick-track > div[rel='"+value+"']"));
            		
            		
            		//System.out.println(element.findElement(By.cssSelector("picture > img")).getAttribute("srcset"));
            		
                    //String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            		
            		String GImageSrc = element.findElement(By.cssSelector("picture > img")).getAttribute("srcset");


            		String GsavePath = "D:/Downloads/crawling/";
            		
            		System.out.println(GImageSrc);
            		System.out.println(GsavePath);
            		System.out.println(Gnum);
            		
            		downloadImage(GImageSrc, GsavePath, Gnum++);
            		
            	}
            	
            	
            	
            	
            	
            	
            	
            	/*
            	
            	try {
                    WebElement ele = driver.findElement(By.cssSelector(".SearchResultGrid_productsGrid > .SearchResultGrid_productsCell "));
                    System.out.println(num+"@"+ele.findElement(By.cssSelector("a")).getAttribute("href"));
                } catch (NoSuchElementException e) {
                    // 해당 선택자로 요소를 찾지 못한 경우의 예외 처리를 수행합니다.
                    System.out.println(num+"@");
                }
            	
                num++;
                
                */
                
                /*
                WebElement ele = driver.findElement(By.cssSelector(".new_product_zone > div"));
                
                //int size = ele.findElements(By.cssSelector(".item_img .item_img")).size();
                
                System.out.println("----------------------------");

                // 모든 .os_wrap 요소 가져오기
                List<WebElement> osWraps = driver.findElements(By.cssSelector(".os_wrap"));

                int itemBoxCount = 0;
                // .os_wrap 요소들에서 .item_box 요소들의 href 속성 추출
                for (WebElement osWrap : osWraps) {
                    // .item_box 요소들 가져오기
                    List<WebElement> itemBoxes = osWrap.findElements(By.cssSelector(".item_box"));

                    // .item_box 요소들에서 .item_img a 태그의 href 속성 추출
                    for (WebElement itemBox : itemBoxes) {
                        // .item_img a 태그 찾기
                        WebElement itemImgLink = itemBox.findElement(By.cssSelector(".item_img a"));

                        // href 속성 추출
                        String href = itemImgLink.getAttribute("href");

                        // 결과 출력
                        System.out.println("item_box " + (++itemBoxCount) + "의 href 속성: " + href);

                    }
                    // 100번째 .item_box까지만 추출
                    if (itemBoxCount >= 100) {
                    	break;
                    }
                }
                */
                
                
                //item.setTitle(ele.findElement(By.cssSelector(".new_product_zone > div:nth-child(1) > .os_wrap > .item_box:nth-child(4) > ")).getText());
                //item.setTitle(ele.findElement(By.cssSelector(".new_product_zone > div:nth-child(1) > .os_wrap > .item_box:nth-child(4) > ")).getAttribute("href"));
                
                //items.add(item);
            }
            

        }catch(TimeoutException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

        driver.close();
        driver.quit();
        
        System.out.println("----------------------------");

        return items;
    }
  
    private static List<ItemVo> okMallCrawling(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();

        try{
        	
            String[] list = {
            		"https://www.moncler.com/ko-kr/genius/shop-genius/moncler-x-frgmnt/acanthus-short-down-jacket-black-I209U1A00001M3235999.html"
            };

            for(String url: list){
            	driver.get(url);
            	Thread.sleep(1000);
            	
                ItemVo item = new ItemVo();
                
                new Actions(driver).sendKeys(Keys.END).perform();
                
                Thread.sleep(1000);
                
                
                /*
                WebElement ele = driver.findElement(By.cssSelector(".new_product_zone > div"));
                
                //int size = ele.findElements(By.cssSelector(".item_img .item_img")).size();
                
                System.out.println("----------------------------");

                // 모든 .os_wrap 요소 가져오기
                List<WebElement> osWraps = driver.findElements(By.cssSelector(".os_wrap"));

                int itemBoxCount = 0;
                // .os_wrap 요소들에서 .item_box 요소들의 href 속성 추출
                for (WebElement osWrap : osWraps) {
                    // .item_box 요소들 가져오기
                    List<WebElement> itemBoxes = osWrap.findElements(By.cssSelector(".item_box"));

                    // .item_box 요소들에서 .item_img a 태그의 href 속성 추출
                    for (WebElement itemBox : itemBoxes) {
                        // .item_img a 태그 찾기
                        WebElement itemImgLink = itemBox.findElement(By.cssSelector(".item_img a"));

                        // href 속성 추출
                        String href = itemImgLink.getAttribute("href");

                        // 결과 출력
                        System.out.println("item_box " + (++itemBoxCount) + "의 href 속성: " + href);

                    }
                    // 100번째 .item_box까지만 추출
                    if (itemBoxCount >= 100) {
                    	break;
                    }
                }
                */
                
                System.out.println("----------------------------");
                
                //item.setTitle(ele.findElement(By.cssSelector(".new_product_zone > div:nth-child(1) > .os_wrap > .item_box:nth-child(4) > ")).getText());
                //item.setTitle(ele.findElement(By.cssSelector(".new_product_zone > div:nth-child(1) > .os_wrap > .item_box:nth-child(4) > ")).getAttribute("href"));
                
                //items.add(item);
            }
            

        }catch(TimeoutException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

        driver.close();
        driver.quit();

        return items;
    }
    
    private static List<ItemVo> officialMallCrawling(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();
        
        String savePath = "D:/Downloads/crawling/";

        try{
        	
            String[] list = {
            		"https://www.moncler.com/ko-kr/women/accessories/hats-and-beanies/gabardine-baseball-cap-black-I20933B000030U162999.html",
            		"https://www.moncler.com/ko-kr/men/accessories/hats-and-beanies/fleece-baseball-cap-navy-blue-I20913B0001080448778.html",
            		"https://www.moncler.com/ko-kr/women/accessories/hats-and-beanies/logo-baseball-cap-silver-I20933B00029596T2915.html",
            		"https://www.moncler.com/ko-kr/women/accessories/bags-and-small-accessories/caradoc-tote-bag-black-I209B5D00006M2170999.html",
            		"https://www.moncler.com/ko-kr/women/accessories/bags-and-small-accessories/mini-caradoc-tote-bag-black-I209B5L00010M2170999.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/tops-and-t-shirts/logo-polo-shirt-off-white-I10938A0000589A0X033.html",
            		"https://www.moncler.com/ko-kr/men/ready-to-wear/polos-and-t-shirts/logo-polo-shirt-navy-blue-I10918A00015899UR77X.html",
            		"https://www.moncler.com/ko-kr/men/ready-to-wear/polos-and-t-shirts/logo-polo-shirt-optical-white-I10918A7030084556001.html",
            		"https://www.moncler.com/ko-kr/genius/shop-genius/moncler-x-frgmnt/logo-motif-t-shirt-black-I209U8C00002M3265999.html",
            		"https://www.moncler.com/ko-kr/genius/shop-genius/moncler-x-frgmnt/logo-t-shirt-light-blue-I209U8C00005M3265710.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/tops-and-t-shirts/crystal-logo-t-shirt-off-white-I20938C00014829HP033.html",
            		"https://www.moncler.com/ko-kr/men/ready-to-wear/polos-and-t-shirts/logo-t-shirt-optical-white-I20918C000478390T001.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweatshirts/padded-zipup-sweatshirt-white-I20938G0001489A2Y034.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweatshirts/padded-zipup-sweatshirt-navy-blue-I20938G0001489A2Y778.html",
            		"https://www.moncler.com/ko-kr/men/ready-to-wear/sweatshirts/teddy-zip-up-hoodie-beige-I20918G0002389A5V21E.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweatshirts/logo-patch-sweatshirt-white-I20938G00027899TR034.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweatshirts/teddy-zipup-hoodie-white-I20938G0003289A5V034.html",
            		"https://www.moncler.com/ko-kr/men/ready-to-wear/sweatshirts/logo-patch-sweatshirt-black-I20918G00048809KR999.html",
            		"https://www.moncler.com/ko-kr/genius/shop-genius/moncler-x-frgmnt/jersey-bermuda-shorts-black-I209U8H00003M2372999.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/pants-and-shorts/terrycloth-shorts-white-I10938H00022596LS034.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweaters-and-cardigans/padded-wool-cardigan-off-white-I10939B00006M1131217.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweaters-and-cardigans/padded-wool-cardigan-black-I20939B00011M1131999.html",
            		"https://www.moncler.com/ko-kr/women/ready-to-wear/sweaters-and-cardigans/padded-wool-cardigan-black-I20939B00020M1131999.html"
            		
            };

            for(String url: list){
            	driver.get(url);
            	Thread.sleep(1000);
            	
                new Actions(driver).sendKeys(Keys.END).perform();
            	Thread.sleep(1000);
            	
            	
                // 첫 번째 .product-container의 첫 번째 .product-image의 img src를 추출합니다.
                WebElement firstProductContainer = driver.findElement(By.cssSelector(".product-container:first-child"));
                
                WebElement firstProductImage = firstProductContainer.findElement(By.cssSelector(".product-col .product-image img"));
                String firstProductImageSrc = firstProductImage.getAttribute("src");

                // 첫 번째 .product-container의 첫 번째 .product-image의 img src를 출력합니다.
                System.out.println("썸네일");
                System.out.println(firstProductImageSrc);
                
                // 썸네일 이미지 URL 수정
                String modifiedThumbnailSrc = firstProductImageSrc.substring(0, 80) + "_X/" + firstProductImageSrc.substring(83);
                System.out.println(modifiedThumbnailSrc);

                downloadImage(modifiedThumbnailSrc, savePath, 1);
                
                
                
                // 두 번째 .product-container의 모든 .product-gallery 내의 .product-image의 img src를 추출합니다.
                WebElement secondProductContainer = driver.findElement(By.cssSelector(".product-container:nth-child(2)"));
                List<WebElement> productGalleryImages = secondProductContainer.findElements(By.cssSelector(".product-gallery .product-image img"));
                int num=1;
                for (WebElement image : productGalleryImages) {
                    String productGalleryImageSrc = image.getAttribute("src");
                    System.out.println("추가이미지 "+ (num++));
                    System.out.println(productGalleryImageSrc);
                    downloadImage(productGalleryImageSrc, savePath, num);
                }
                

                /*
                // 이미지 URL 값 출력
                for (int i = 0; i < imageElements.size(); i++) {
                    WebElement imageElement = imageElements.get(i);
                    String imageUrl = imageElement.getAttribute("src");
                    System.out.println(imageUrl);
                    
                    // 이미지 다운로드
                    downloadImage(imageUrl, savePath);
                }
                */
          
                
                System.out.println("---------------------------------");
                
                //imageCrawling(url);

            }
            

        }catch(TimeoutException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

        System.out.println("-------------------마지막-------------------");
        
        driver.close();
        driver.quit();

        return items;
    }
    
    private static void downloadImage(String imageUrl, String savePath, int num) throws IOException {
    	// URL에 프로토콜 추가
    	if (!imageUrl.startsWith("http:") && !imageUrl.startsWith("https:")) {
            imageUrl = "https:" + imageUrl;
        }
    	
    	URL url = new URL(imageUrl);
    	
        //String[] urlParts = imageUrl.split("/");
        //String imageName = urlParts[urlParts.length - 1];
    	
        //String newPath = imageUrl.replace("/30x45/", "/1024x1536/"); // 이미지 URL 크기 변경
        //newPath = newPath.replace("format=WEBP", "format=JPG"); // 이미지 확장자 변경
        //String imageName = newPath.substring(65, 80) + "_" + num + ".jpg";
        
    	System.out.println(url);
		String test = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
		String fileName = (test.substring(0, 17)).replace("_", "") + "_" + num + ".jpg";
		System.out.println("---test1----");
		System.out.println(url);

        
        //InputStream is = new URL(newPath).openStream();
		
		
		
		InputStream is = url.openStream();
		
        try {
			System.out.println("---test2----");
            //String fileName = imageName.replaceAll("[^a-zA-Z0-9.-]", "_");
            //String fileName = imageName.replaceAll("[^a-zA-Z0-9.-]", "_");
            try (OutputStream os = new FileOutputStream(savePath + fileName)) {
            	System.out.println("---test3----");
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                System.out.println("이미지 다운로드 완료: " + savePath + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            is.close();
        }
    }
    
    
    
}

