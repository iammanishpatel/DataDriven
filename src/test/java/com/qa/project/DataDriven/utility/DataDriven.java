package com.qa.project.DataDriven.utility;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

public class DataDriven {
	
	WebDriver driver;
	public static final String url = "https://translate.google.co.in/?um=1&ie=UTF-8&hl=en&client=tw-ob#hi/en/";
	public static final String file_name="words.xls";
	public static final String path_xlsxDataSheet="C://Users//manishpatel//workspace//DataDriven//src//test//java//com//qa//project//DataDriven//data//words.csv";
	
	@BeforeTest
	public void begning(){
		System.setProperty("webdriver.chrome.driver","C:\\Users\\manishpatel\\Downloads\\chromedriver_win32\\chromedriver.exe");
		if(driver == null){
			driver = new ChromeDriver();
			driver.get(url);
			driver.manage().window().maximize();
		}
	}
	
	@Test(priority=1)
	public void translatorSetUp() throws InterruptedException{
		String requiredLanguageMenuPath, requiredLanguagePath;
		requiredLanguageMenuPath = "//div[@id='gt-tl-gms']";
		requiredLanguagePath="//div[@id='gt-tl-gms-menu']/table/tbody/tr/td[6]/div[@id='goog-menuitem-group-6']/div[7]/div[@class='goog-menuitem-content' and contains(text(),'Spanish')]";
		//gt-res-dir-ctr
		driver.findElement(By.xpath(requiredLanguageMenuPath)).click();
		System.out.println(" Clicked on Language Menu");
		Thread.sleep(500);
		driver.findElement(By.xpath(requiredLanguagePath)).click();
		System.out.println(" Language Selected");
	}
	
	@Test(priority=2)
	public void readContent() throws IOException, InterruptedException{
		String path_input,path_translateButton,output,path_output;
		path_input = "//textarea[@id='source']";
		path_output = "//span[@id='result_box']";
		path_translateButton = "//input[@class='jfk-button jfk-button-action']";
		CSVReader reader = new CSVReader(new FileReader(path_xlsxDataSheet));// This will load csv file
		PrintWriter writer = new PrintWriter(new File("newList.csv"));
		StringBuilder sb = new StringBuilder();
		List<String[]> words = reader.readAll();
		System.out.println("total row size "+words.size());
		Iterator<String[]> itr = words.iterator();
		while(itr.hasNext()){
			String[] str=itr.next();
			System.out.println(" Words to be translated are :");
			for(int i=0;i<str.length;i++){
				 System.out.print(str[i]+" => ");
				 driver.findElement(By.xpath(path_input)).clear();
				 driver.findElement(By.xpath(path_input)).sendKeys(str[i]);
				 driver.findElement(By.xpath(path_translateButton)).click();
				 output = driver.findElement(By.xpath(path_output)).getText();
				 System.out.println(output);
				 sb.append(output);
			     sb.append(", ");
				 Thread.sleep(500);
			}  	 
		}
		writer.write(sb.toString());
		System.out.println("Done...");
	}
	
	@AfterTest
	public void end(){
		//driver.quit();
	}

}
