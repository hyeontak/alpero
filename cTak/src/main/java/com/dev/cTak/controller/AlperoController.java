package com.dev.cTak.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    		System.out.println(value.getItemCode());
    		System.out.println("==========================");
    		alperoService.curDataUpdate(value);
    	}
    	
    	return result;
    }
    
    
    @PostMapping("/alpero")
    @ResponseBody
    public List<ItemVo> crawling(@RequestBody String url) {
        System.setProperty("webdriver.chrome.driver", "D:\\work\\chromedriver-win32\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        //chromeOptions.addArguments("--no-sandbox");
        
        WebDriver driver = new ChromeDriver(chromeOptions);
        List<ItemVo> list = new ArrayList<>();

        //list = web_fromvi(driver);
        list = web_lunatic(driver);
        //list.addAll(web_lunatic(driver));
        
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
            		"https://www.fromvi.com/product/detail.html?product_no=1112",
        			"https://www.fromvi.com/product/detail.html?product_no=1149",
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
            System.out.println(e.toString());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

        driver.close();
        driver.quit();

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

            driver.findElement(By.cssSelector("#naverIdLogin_loginButton")).click();
             
            Thread.sleep(1000);
            
        	driver.findElement(By.cssSelector("#id")).click();
            driver.findElement(By.cssSelector("#id")).sendKeys("alpero1122");

            driver.findElement(By.cssSelector("#pw")).click();
            driver.findElement(By.cssSelector("#pw")).sendKeys("pepxylavu0315*");
            
            driver.findElement(By.cssSelector(".btn_login_wrap .btn_login")).click();

            Thread.sleep(1000);
            
            
           
            
            Thread.sleep(1000);
           
            
            
        }catch(Exception e) {
        	
        }

        return items;
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

