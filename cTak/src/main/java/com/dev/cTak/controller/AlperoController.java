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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.cTak.vo.ItemVo;

@Controller
public class AlperoController {

    private String WEB_STR = "D:\\work\\chromedriver_win32\\chromedriver.exe";

    @PostMapping("/alpero")
    @ResponseBody
    public List<ItemVo> crawling(@RequestBody String url) {
        HashMap map = new HashMap();
        System.setProperty("webdriver.chrome.driver", "D:\\work\\chromedriver-win32\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        WebDriver driver = new ChromeDriver(chromeOptions);
        List<ItemVo> list = new ArrayList<>();

        list = web_fromvi(driver);

        //map.put("items",web_fromvi(driver));


        return list;
    }

    private static List<ItemVo> web_fromvi(WebDriver driver){
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
                    "https://fromvi.com/product/detail.html?product_no=1221",
                    "https://fromvi.com/product/detail.html?product_no=649"
            };
            int cnt = 0;

            for(String url: list){
                ItemVo item = new ItemVo();
                driver.get(url);
                Thread.sleep(1000);
                WebElement ele = driver.findElement((By.cssSelector(".imgArea")));
                String imgThumbLink = ele.findElement(By.cssSelector(".keyImg > a > img")).getAttribute("src");
                
                item.setImgThumbLink(imgThumbLink);
                
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
                    int idx = 1;
                    
                    for(WebElement value : seleEle){
                        if(!value.getAttribute("value").contains("*")){
                            value.click();
                            List<WebElement> optionEle = ele.findElements(By.cssSelector("table > tbody > tr:nth-of-type(2) > td > select > option"));

                            for(WebElement optionValue : optionEle){
                                if(!optionValue.getAttribute("value").contains("*")){
                                	optionList += value.getText() + ": " + optionValue.getText();
                                	if(idx < optionEle.size()) {
                                		 optionList += ", "; 
                                	}
                                }
                            }
                        }
                    }
                    item.setSele(optionList);
                }else{
                    List<WebElement> optionEle = ele.findElements(By.cssSelector("table > tbody > tr > td > select > optgroup > option"));
                    
                    String optionList = "";
                    
                    int idx = 1;
                    
                    for(WebElement value : optionEle){
                    	optionList += value.getText();
                    	if(idx < optionEle.size()) {
                    		optionList += ", ";
                    	}
                    	
                    	idx++;
                    }
                    item.setSele(optionList);
                }
                System.out.println("======================================");

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

    private static ItemVo web_burberry(WebDriver driver){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(3));

        ItemVo items = new ItemVo();

        try{
            List<String> list = new ArrayList<>();

            driver.get("https://kr.burberry.com/");







        }catch(TimeoutException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return items;
    }
}

