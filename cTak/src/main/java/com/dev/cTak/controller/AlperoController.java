package com.dev.cTak.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.dev.cTak.service.AlperoService;
import com.dev.cTak.vo.ItemVo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AlperoController {
	
	private final AlperoService alperoService;

	@Autowired
	public AlperoController(AlperoService alperoService) {
		this.alperoService = alperoService;
	};
	
	private String WEB_STR = "D:\\work\\chromedriver-win32\\chromedriver.exe";

    @GetMapping("/items")
    public String main(HttpServletRequest request) {
    	return "content/items";
    }

    @GetMapping("/selectList")
    @ResponseBody
    public List<ItemVo> selectList(){
    	List<ItemVo> list = new ArrayList<>();
    	ItemVo vo = new ItemVo();
    	
    	list = alperoService.selectItemList(vo);
    	
    	return list;
    }

    @GetMapping("/compareList")
    @ResponseBody
    public List<ItemVo> compareList(){
    	List<ItemVo> list = new ArrayList<>();
    	ItemVo vo = new ItemVo();
    	
    	list = alperoService.compareList(vo);
    	
    	return list;
    }

    @PostMapping("/curDataUpdate")
    @ResponseBody
    public int curDataUpdate(){
    	int result = 0;
    	
    	List<ItemVo> list = new ArrayList<>();
    	ItemVo vo = new ItemVo();
    	
    	list = alperoService.compareList(vo);
    	
    	for(ItemVo value : list) {
    		alperoService.curDataUpdate(value);
    	}
    	
    	return result;
    }
    
    
    @PostMapping("/alpero")
    @ResponseBody
    public List<ItemVo> crawling(@RequestBody String url) {
        System.setProperty("webdriver.chrome.driver", "D:\\work\\chromedriver-win32\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("--no-sandbox");
        //WebDriverManager.chromedriver().setup();
        
        WebDriver driver = new ChromeDriver(chromeOptions);
        List<ItemVo> list = new ArrayList<>();

        list = web_fromvi(driver);
        list.addAll(web_lunatic(driver));
//        list.addAll(crawlAtestarProduct(driver));
        
//        list = crawlAtestarProduct(driver);
        
        System.out.print("끝입니당!!!!!!!!!!!!!!!");

//		list = web_housemore(driver);
//		list = web_plala(driver);
//		list.addAll(web_housemore(driver));
        driver.quit();
        return list;
    }

    /*
     * 프롬 비아이
     * 
     * */
    private List<ItemVo> web_fromvi(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();

        try{
            driver.get("https://fromvi.com/member/login.html");

            driver.findElement(By.cssSelector("#member_id")).click();
            driver.findElement(By.cssSelector("#member_id")).sendKeys("alpero1122");

            driver.findElement(By.cssSelector("#member_passwd")).click();
            driver.findElement(By.cssSelector("#member_passwd")).sendKeys("pepperlavu0315");


            driver.findElement(By.cssSelector(".login fieldset a")).click();

            Thread.sleep(1000);

            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(3));
            
            String[] list = {
            		//"https://www.fromvi.com/product/detail.html?product_no=1112",
        			//"https://www.fromvi.com/product/detail.html?product_no=1149",
        			"https://www.fromvi.com/product/detail.html?product_no=1150",
        			"https://www.fromvi.com/product/detail.html?product_no=1152",
        			"https://www.fromvi.com/product/detail.html?product_no=1182",
        			"https://www.fromvi.com/product/detail.html?product_no=1197",
        			"https://www.fromvi.com/product/detail.html?product_no=1225",
        			"https://www.fromvi.com/product/detail.html?product_no=1249",
        			"https://www.fromvi.com/product/detail.html?product_no=351",
        			"https://www.fromvi.com/product/detail.html?product_no=357",
        			"https://www.fromvi.com/product/detail.html?product_no=379",
        			"https://www.fromvi.com/product/detail.html?product_no=380",
        			"https://www.fromvi.com/product/detail.html?product_no=642",
        			"https://www.fromvi.com/product/detail.html?product_no=643",
        			"https://www.fromvi.com/product/detail.html?product_no=644",
        			"https://www.fromvi.com/product/detail.html?product_no=645",
        			"https://www.fromvi.com/product/detail.html?product_no=825",
        			"https://www.fromvi.com/product/detail.html?product_no=925",
        			"https://www.fromvi.com/product/detail.html?product_no=927"
            };
            
            int cnt = 0;
            
            for(String url: list){
                ItemVo item = new ItemVo();
                driver.get(url);
                Thread.sleep(1000);
                WebElement ele = driver.findElement((By.cssSelector(".imgArea")));

                String imgThumbLink = ele.findElement(By.cssSelector(".keyImg > a > img")).getAttribute("src");
                
                String dtailLink = driver.findElement(By.cssSelector("#prdDetail")).getAttribute("innerHTML");
                
                item.setDetailLink(dtailLink);
                
                ele = driver.findElement((By.cssSelector("html")));
                String itemCode = ele.findElement(By.cssSelector("meta[property='product:productId']")).getAttribute("content");
                
                item.setItemCode(itemCode);
                item.setShopCode("A0001");
                item.setThumbnailLink(imgThumbLink);
                
                ele = driver.findElement((By.cssSelector(".infoArea")));
                String title = ele.findElement(By.cssSelector("h3")).getText();
                String price1 = ele.findElement(By.cssSelector("#span_product_price_custom")).getText();
                String price2 = ele.findElement(By.cssSelector("#span_product_price_text")).getText();

                item.setTitle(title);
                item.setPrice1(price1);
                item.setPrice2(price2);
                
                int optionCnt = ele.findElements(By.cssSelector(".xans-product-option tr")).size();
                if(optionCnt > 1){
                    List<WebElement> seleEle = ele.findElements(By.cssSelector("table > tbody > tr:nth-of-type(1) > td > select > option"));
                   
                    String optionList = "";
                    
                    for(WebElement value : seleEle){
                        if(!value.getAttribute("value").contains("*")){
                            value.click();
                            List<WebElement> optionEle = ele.findElements(By.cssSelector("table > tbody > tr:nth-of-type(2) > td > select > option"));

                            for(WebElement optionValue : optionEle){
                                if(!optionValue.getAttribute("value").contains("*")){
                                	optionList += value.getText() + ": " + optionValue.getText() + ", ";
                                }
                            }
                        }
                    }
                    optionList = optionList.substring(0,optionList.length()-2);
                    item.setOptionText(optionList);
                }else{
                	
                	boolean isElementPresent = isElementPresent(driver,By.cssSelector("#product_option_id1 > optgroup"));
                	List<WebElement> optionEle;
                	
                	if(isElementPresent) {
                		optionEle = ele.findElements(By.cssSelector("table > tbody > tr > td > select > optgroup > option"));
                	}else {
                		optionEle = ele.findElements(By.cssSelector("table > tbody > tr > td > select > option"));
                	}
                    
                    String optionList = "";
                    
                    int idx = 1;
                    if(optionEle.size() > 0) {
                    	for(WebElement value : optionEle){
                    		if(!value.getAttribute("value").contains("*")) {
                    			optionList += value.getText();
                    			if(idx < optionEle.size()) {
                    				optionList += ", ";
                    			}
                    			idx++;
                    		}
                    	}
                    	item.setOptionText(optionList);
                    }else {
                    	item.setOptionText("판매종료?");
                    }
                    
                }
                
                
                List<ItemVo> temp = new ArrayList<>();
                temp = alperoService.selectItemList(item);
                
                if(temp.size() > 0) {
                	alperoService.insertItemsTemp(item);
                }else {
                	alperoService.insertItems(item);
                }
                
                items.add(cnt, item);
                
                cnt++;
            }


        }catch(TimeoutException e){
            e.printStackTrace();
            System.out.println("에러!!!!!!!!!!! " + e.toString());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("에러!!!!!!!!!!! " + e.toString());
        }

        return items;
    }
    
    /*
     * 루나틱 비투비
     * 
     * */
    private List<ItemVo> web_lunatic(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();

        
        try{
        	driver.get("https://www.lunatic.co.kr/login?returnUrl=/");
             
        	WebElement ele = driver.findElement(By.cssSelector("#loginUid"));
        	
        	ele.click();
        	ele.sendKeys("alpero1122pepxylavu0315*");
        	
        	ele.sendKeys(Keys.CONTROL + "a"); // 전체 선택
        	ele.sendKeys(Keys.CONTROL + "c"); // 복사
        	
        	driver.findElement(By.cssSelector("#naverIdLogin_loginButton")).click();
        	
        	
        	loginNaver(driver);
        	
        	Thread.sleep(2000);
        		
        	String[] list = {
        			"https://www.lunatic.co.kr/product/036d4b40-8f51-46b9-9155-008d80ffe498",
        			"https://www.lunatic.co.kr/product/125c4901-b2e1-44fb-b9c9-cad5fee611b4",
        			"https://www.lunatic.co.kr/product/638a856d-8bcb-44d7-955c-7cdfa274509d",
        			"https://www.lunatic.co.kr/product/a1d0a7ac-f9ec-4ad8-9c5f-cb2fba89b0f7",
        			"https://www.lunatic.co.kr/product/98a5b00e-35ce-4952-9832-4be55b014099",
        			"https://www.lunatic.co.kr/product/ac6f8a4c-cd68-473a-94ad-618f36d4ec29",
        			"https://www.lunatic.co.kr/product/0a0ffae7-7a5f-478f-9a2b-404ee2e2ceb1",
        			"https://www.lunatic.co.kr/product/bdb785c1-272c-46ff-9986-c3c306cec61c",
        			"https://www.lunatic.co.kr/product/4b60e6da-3eb0-449d-9a69-2a62c4e3f770"
            };
            
        	ItemVo item = new ItemVo();
        	int cnt = 0;
        	
        	for(String url : list) {
        		driver.get(url);
        		
       		 	WebElement element = driver.findElement(By.cssSelector("#shopProductName"));
       		 	System.out.println(element.getText());
       		 	item.setTitle(element.getText());
       		 	
       		 	element = driver.findElement(By.cssSelector("meta[property='og:url']")); 

       		 	String itemCode = element.getAttribute("content").replace("https://www.lunatic.co.kr/product/", "");
       		 	
       		 	item.setItemCode(itemCode);
       		 	item.setShopCode("A0002");
       		 			
       		 	element = driver.findElement(By.cssSelector("#shopProductPrice .productPriceWithDiscountSpan"));
       		 	item.setPrice1(element.getText());
       		 	
       		 	element = driver.findElement(By.cssSelector("#shopProductPrice .productDiscountPriceSpan"));
       		 	item.setPrice2(element.getText());
    		 	
    		 	Boolean isElementPresent = isElementPresent(driver,By.cssSelector(".shopProductOptionListDiv"));
    		 	String option = "";
    		 	
    		 	if(isElementPresent) {
    		 		List<WebElement> arrList = 
    		 				driver.findElements(
    		 						By.cssSelector("#shopProductContentInfo .shopProductOptionListDiv div:nth-child(2) div div .custom-select-box-list-inner .custom-select-option"));
    		 		
    		 		List<WebElement> arrList2 = driver.findElements(
	 						By.cssSelector("#shopProductContentInfo .shopProductOptionListDiv div:nth-child(3) div div .custom-select-box-list-inner .custom-select-option"));
    		 		
    		 		for(WebElement str : arrList) {
    		 			
    		 			if(!str.getAttribute("class").contains("custom-select-option-hide")) {
    		 				if(str.getAttribute("data-combined-option-value-no") != null) {
    		 					if(str.getAttribute("data-soldout").equals("false")) {
    		 						driver.findElement(By.cssSelector("#shopProductContentInfo .shopProductOptionListDiv div:nth-child(2) div")).click();
    		 						
    		 						str.findElement(By.cssSelector("div")).click();
    		 					}
    		 					option += str.findElement(By.cssSelector("div")).getAttribute("innerHTML");
    		 					
    		 					if(arrList2.size() > 0) {
    		 						option += "_";
    		 					}else {
    		 						option += ", ";
    		 					}
    		 					
    		 					Thread.sleep(1000);
    		 					
    		 					for(WebElement str2 : arrList2) {
    		 						if(!str2.getAttribute("class").contains("custom-select-option-hide")) {
	    		 						if(str2.getAttribute("data-combined-option-value-no") != null) {
	    		 							option += str2.getAttribute("data-option-value") + ", ";
	    		 						}
    		 						}
    		 					}
    		 					if(arrList2.size() > 0) {
    		 						option += "| ";
    		 					}
    		 				}
    		 			}
    		 		}
    		 	}
    		 	item.setOptionText(option);
    		 	
    		 	element = driver.findElement(By.cssSelector("#shopProductImgsMainDiv div:nth-child(2) div:nth-child(2)"));
    		 	String imgName = element.getAttribute("imgsrc").endsWith(".jpg") 
    		 		    ? element.getAttribute("imgsrc").replace(".jpg", "_1000.jpg") 
    		 		    : element.getAttribute("imgsrc").replace(".png", "_1000.png");
    		 	item.setThumbnailLink("https://contents.sixshop.com/thumbnails" + imgName);
    		 	
    		 	element = driver.findElement(By.cssSelector(".bottom-info"));
    		 	
    		 	item.setDetailLink(element.getAttribute("innerHTML"));
    		 	
				List<ItemVo> temp = new ArrayList<>();
				temp = alperoService.selectItemList(item);
				
				if(temp.size() > 0) {
					alperoService.insertItemsTemp(item);
				}else {
					alperoService.insertItems(item);
				}
				
				items.add(cnt, item);
				
				cnt++;
        	}
        }catch(Exception e) {
        	
        }
        
        driver.close();
        driver.quit();
        
        return items;
    }
    
    
    /*
     * 하우스 모어
     * 
     * */
    private List<ItemVo> web_housemore(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();
        
        try {
        	driver.get("https://housemore.co.kr/member/login.html");

            driver.findElement(By.cssSelector("#member_id")).click();
            driver.findElement(By.cssSelector("#member_id")).sendKeys("alpero1122");

            driver.findElement(By.cssSelector("#member_passwd")).click();
            driver.findElement(By.cssSelector("#member_passwd")).sendKeys("pepperlavu0315");


            driver.findElement(By.cssSelector(".login fieldset a")).click();

            Thread.sleep(1000);
            
            String[] list = {
            		"https://housemore.co.kr/product/%EA%B2%80%ED%8A%B8%EB%A6%AC-%EC%98%A4%ED%8E%98%EB%9D%BC-4%EC%9D%B8%EC%8B%9D%EA%B8%B0-29p-%ED%99%88%EC%84%B8%ED%8A%B8-5color-%EC%B5%9C%EC%A2%85%ED%8C%90%EB%A7%A4%EA%B0%80-329000%EC%9B%90%EC%9D%B4%ED%95%98-%ED%8C%90%EB%A7%A4%EA%B8%88%EC%A7%80/2301/category/25/display/1/",
            		"https://housemore.co.kr/product/%EC%9D%B4%EC%B9%98%EB%A7%88%EC%B9%B4%EC%84%B8-%EC%98%A4%EB%B8%90%EB%83%84%EB%B9%84-%EA%B2%B8-%EC%98%A4%EB%B8%90%ED%8A%B8%EB%A0%88%EC%9D%B4/2244/category/24/display/1/"
            };
            
            ItemVo item = new ItemVo();
            int cnt = 0;
            
            for(String url : list) {
            	 driver.get(url);
                 Thread.sleep(1000);
                 WebElement ele = driver.findElement((By.cssSelector(".headingArea h2")));
                  
                 //item.setItemCode(itemCode);
                 item.setShopCode("A0003");
                 
                 item.setTitle(ele.getText());
                 System.out.println("title : " + ele.getText());

                 ele = driver.findElement(By.cssSelector(".keyImg .thumbnail a img"));
                 item.setThumbnailLink(ele.getAttribute("src"));
                 
                 ele = driver.findElement(By.cssSelector("#prdDetail .cont"));
                 item.setDetailLink(ele.getAttribute("innerHTML"));
                 
                 ele = driver.findElement(By.cssSelector("#span_product_price_custom"));
                 item.setPrice1(ele.getText());
                 System.out.println("price1 : " + ele.getText());

                 ele = driver.findElement(By.cssSelector("#span_product_price_text"));
                 item.setPrice2(ele.getText());
                 System.out.println("price1 : " + ele.getText());
                 
                 List<WebElement> arrList = driver.findElements(By.cssSelector("#product_option_id1 optgroup option"));
                 String optionList = "";
                 
                for(WebElement value : arrList) {
                	optionList += value.getText() + ", ";
                }
                 
                item.setOptionText(optionList);
                
                System.out.print("optionList : " + optionList);
                System.out.println("");
                
                List<ItemVo> temp = new ArrayList<>();
                temp = alperoService.selectItemList(item);
                
                if(temp.size() > 0) {
                	alperoService.insertItemsTemp(item);
                }else {
                	alperoService.insertItems(item);
                }
                
                items.add(cnt, item);
                
                cnt++;
            }
            
            
		} catch (Exception e) {
			// TODO: handle exception
		}
        return items;
    }
    
    /*
     * 하우스 모어
     * 
     * */
    private List<ItemVo> web_plala(WebDriver driver){
        List<ItemVo> items = new ArrayList<ItemVo>();
        
        try {
        	driver.get("https://plala.co.kr/member/login.html");

            driver.findElement(By.cssSelector("#member_id")).click();
            driver.findElement(By.cssSelector("#member_id")).sendKeys("alpero1122");

            driver.findElement(By.cssSelector("#member_passwd")).click();
            driver.findElement(By.cssSelector("#member_passwd")).sendKeys("pepperlavu0315*");


            driver.findElement(By.cssSelector(".login fieldset a")).click();

            Thread.sleep(1000);
            
            String[] list = {
            		"https://plala.co.kr/product/detail.html?product_no=16&cate_no=1&display_group=2",
            		"https://plala.co.kr/product/detail.html?product_no=229&cate_no=26&display_group=1"
            };
            
            ItemVo item = new ItemVo();
            int cnt = 0;
            
            for(String url : list) {
            	 driver.get(url);
                 Thread.sleep(1000);
                 WebElement ele = driver.findElement((By.cssSelector(".headingArea h2")));
                  
                 //item.setItemCode(itemCode);
                 item.setShopCode("A0004");
                 
                 item.setTitle(ele.getText());
                 System.out.println("title : " + ele.getText());

                 ele = driver.findElement(By.cssSelector(".keyImg .thumbnail a img"));
                 item.setThumbnailLink(ele.getAttribute("src"));
                 
                 ele = driver.findElement(By.cssSelector("#prdDetail .cont"));
                 item.setDetailLink(ele.getAttribute("innerHTML"));
                 
                 ele = driver.findElement(By.cssSelector(".infoArea div:nth-child(2) table tbody tr:nth-child(7) td span"));
               //*[@id="sub_contents"]/div[2]/div[1]/div[2]/div[2]/table/tbody/tr[7]/td/span
                 item.setPrice1(ele.getText());
                 System.out.println("price1 : " + ele.getText());

                 ele = driver.findElement(By.cssSelector(".infoArea div:nth-child(2) table tbody tr:nth-child(8) td span strong"));
                 item.setPrice2(ele.getText());
                 System.out.println("price2 : " + ele.getText());
                 
                 List<WebElement> arrList = driver.findElements(By.cssSelector(".infoArea > table .xans-element-"));
                 String optionList = "";
                 
                for(WebElement value : arrList) {
                	value.click();
                	
                	List<WebElement> arrList2 = value.findElements(By.cssSelector("tr td select option"));
                	for(WebElement optionValue : arrList2) {
                		if(!optionValue.getAttribute("value").contains("*")) {
                			optionList += value.getText() + ": " + optionValue.getText() + ", ";
                		}
                	}
                }
                
                item.setOptionText(optionList);
                
                System.out.print("optionList : " + optionList);
                System.out.println("");
                
//                List<ItemVo> temp = new ArrayList<>();
//                temp = alperoService.selectItemList(item);
//                
//                if(temp.size() > 0) {
//                	alperoService.insertItemsTemp(item);
//                }else {
//                	alperoService.insertItems(item);
//                }
                
                items.add(cnt, item);
                
                cnt++;
            }
            
            
		} catch (Exception e) {
			// TODO: handle exception
		}
        return items;
    }
    
    /*
     * 아떼스타
     * */
    public List<ItemVo> crawlAtestarProduct(WebDriver driver) {

        ChromeOptions chromeOptions = new ChromeOptions();
        // chromeOptions.addArguments("--headless"); // 필요시 헤드리스 모드 활성화

        List<ItemVo> items = new ArrayList<>();
        try {
            driver.get("https://atestar.co.kr/member/login.html");
            Thread.sleep(2000); // 페이지가 완전히 로드될 때까지 기다림
            
            driver.findElement(By.cssSelector("#member_id")).click();
            driver.findElement(By.cssSelector("#member_id")).sendKeys("alpero1122");

            driver.findElement(By.cssSelector("#member_passwd")).click();
            driver.findElement(By.cssSelector("#member_passwd")).sendKeys("pepperlavu0315*");


            driver.findElement(By.cssSelector(".login fieldset a")).click();
            
            ItemVo item = new ItemVo();
            
            
            String[] list = {
            		"https://atestar.co.kr/product-/3913/category/1/"
            };
            for(String url : list) {
            	driver.get(url);
                Thread.sleep(1000);
            
                // 상품명 크롤링
                WebElement ele = driver.findElement(By.cssSelector(".optCon span"));
                item.setTitle(ele.getText());
                
                // 상품 이미지 URL 크롤링
                ele = driver.findElement(By.cssSelector(".imgArea .is-selected img"));
                String imgThumbLink = ele.getAttribute("src");
                item.setThumbnailLink(imgThumbLink);

                // 상품 상세 설명 크롤링
                ele = driver.findElement(By.cssSelector(".cont"));
                item.setDetailLink(ele.getAttribute("innerHTML"));

                int optionCnt = ele.findElements(By.cssSelector(".topInner .xans-product-option tr")).size();
                if(optionCnt > 1){
                    List<WebElement> seleEle = ele.findElements(By.cssSelector("table > tbody > tr:nth-of-type(1) > td > select > option"));
                   
                    String optionList = "";
                    
                    for(WebElement value : seleEle){
                        if(!value.getAttribute("value").contains("*")){
                            value.click();
                            List<WebElement> optionEle = ele.findElements(By.cssSelector("table > tbody > tr:nth-of-type(2) > td > select > option"));

                            for(WebElement optionValue : optionEle){
                                if(!optionValue.getAttribute("value").contains("*")){
                                	optionList += value.getText() + ": " + optionValue.getText() + ", ";
                                }
                            }
                        }
                    }
                    optionList = optionList.substring(0,optionList.length()-2);
                    item.setOptionText(optionList);
                }else{
                	
                	boolean isElementPresent = isElementPresent(driver,By.cssSelector("#product_option_id1 > optgroup"));
                	List<WebElement> optionEle;
                	
                	if(isElementPresent) {
                		optionEle = ele.findElements(By.cssSelector("table > tbody > tr > td > select > optgroup > option"));
                	}else {
                		optionEle = ele.findElements(By.cssSelector("table > tbody > tr > td > select > option"));
                	}
                    
                    String optionList = "";
                    
                    int idx = 1;
                    if(optionEle.size() > 0) {
                    	for(WebElement value : optionEle){
                    		if(!value.getAttribute("value").contains("*")) {
                    			optionList += value.getText();
                    			if(idx < optionEle.size()) {
                    				optionList += ", ";
                    			}
                    			idx++;
                    		}
                    	}
                    	item.setOptionText(optionList);
                    }else {
                    	item.setOptionText("판매종료?");
                    }
                    
                }
                
                // 상품 가격 크롤링
                ele = driver.findElement(By.cssSelector("#span_product_price_text"));
                item.setPrice1(ele.getText());
                
                // 상품 가격 크롤링
                ele = driver.findElement(By.cssSelector("#span_product_price_sale"));
                item.setPrice2(ele.getText());
                
                ele = driver.findElement((By.cssSelector("html")));
                
                String itemCode = ele.findElement(By.cssSelector("meta[property='product:productId']")).getAttribute("content");
                // 아이템 코드 설정
                item.setItemCode(itemCode); // 예제용 코드. 실제 사이트에서 고유 코드가 있다면 추출 필요
                item.setShopCode("A0005"); // 예제용 쇼핑몰 코드
                
                // 데이터를 리스트에 추가
                items.add(item);
                
                // 데이터베이스에 추가 또는 업데이트
                List<ItemVo> temp = alperoService.selectItemList(item);
                if (temp.size() > 0) {
                    //alperoService.insertItemsTemp(item);
                } else {
                    //alperoService.insertItems(item);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
            
        
        return items;
    }
    
    /*
     * 네이버 자동 로그인 캡챠 우회
     * */
    public static void loginNaver(WebDriver driver) {
    	try {
    		WebElement ele = driver.findElement(By.cssSelector("#id"));
            driver.findElement(By.cssSelector("#id")).click();
            driver.findElement(By.cssSelector("#id")).sendKeys(Keys.CONTROL + "v");
            
            Thread.sleep(1000);
            
            String id = driver.findElement(By.cssSelector("#id")).getAttribute("value").replace("pepxylavu0315*", "");
            String pw = driver.findElement(By.cssSelector("#id")).getAttribute("value").replace("alpero1122", "");
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", ele, id);
            
            Thread.sleep(1000);
            
            ele = driver.findElement(By.cssSelector("#pw"));
            driver.findElement(By.cssSelector("#pw")).click();
            driver.findElement(By.cssSelector("#pw")).sendKeys(Keys.CONTROL + "v");
            
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", ele, pw);
            
            driver.findElement(By.cssSelector(".btn_login_wrap .btn_login")).click();
            
            driver.findElement(By.cssSelector(".btn_cancel a")).click();
    	}catch(Exception e) {
    		
    	}
    }
    
    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true; // 요소가 존재함
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // 요소가 존재하지 않음
        }
    }

    public static boolean isElementPresent2(WebDriver driver, By by, WebElement value) {
    	try {
    		value.findElement(by);
    		return true; // 요소가 존재함
    	} catch (org.openqa.selenium.NoSuchElementException e) {
    		return false; // 요소가 존재하지 않음
    	}
    }
}

