package hu.bigdata.term.webcrauler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
public class WebCrawler {
	 private HashSet<String> links;
	 private static final int MAX_DEPTH = 2;

	    public WebCrawler() {
	        links = new HashSet<String>();
	    }
	    
	    public void getWebList(){
	    	for(int i=1;i<=2000;i++){
	    		int num = 1+i/100;
	    		getPageLinks("https://www.winemag.com/?s=&drink_type=wine&page="+i, 0,"./file/wineData"+num);
	    	}
	    }

	    public void getPageLinks(String URL, int depth, String fileName) {
	        if (!links.contains(URL)  && depth < MAX_DEPTH ) {
	            System.out.println(">> Depth: " + depth + " [" + URL + "]");
	            try {
	                links.add(URL);

	                Document document = Jsoup.connect(URL).get();
	                Element sr = document.getElementById("search-results");
	                Elements hrefElements= sr.select("div.results").select("a[href]");
	                HashSet<String> urls = new HashSet<String>();
	                HashSet<Wine> wines = new HashSet<Wine>();
	                for (Element element:hrefElements){
	                	Elements h =element.getAllElements();
	                	String href = h.get(0).attr("href");
	                	Wine wine = fetchWebData(href);
	                	urls.add(href);
	                	if(wine!=null)
	                		wines.add(wine);
	                }
	    	    	File webs = new File("./file/webs");
	                BufferedWriter websbf = new BufferedWriter(new FileWriter(webs,true));
	                for(String str:urls)
	                	websbf.write(str+"\n");
	                websbf.close();

	                
	                //File wineData = new File("./file/wineData");
	                File wineData = new File(fileName);
	                BufferedWriter wineDatabf = new BufferedWriter(new FileWriter(wineData,true));
	                for(Wine wine:wines)
	                	wineDatabf.write(wine.getTitle()+"|||"+wine.getDesignation()+"|||"+wine.getPoints()+"|||"+wine.getPrice()+"|||"
	                	+wine.getAppellation()+"|||"+wine.getTasterName()+"|||"+wine.getTasterTwitterHandle()+"|||"+wine.getTasterTitle()+"|||"
	                	+wine.getVariety()+"|||"+wine.getWinery()+"\t"+wine.getDatePublished()+"|||"+wine.getCategory()+"|||"
	                	+wine.getBottleSize()+"|||"+wine.getAlcohol()+"|||"+wine.getDescription()+"\n");
	                wineDatabf.close();
	                
	                //depth++;

	            } catch (IOException e) {
	                System.err.println("For '" + URL + "': " + e.getMessage());
	            }
	        }
	    }

	    public Wine fetchWebData(String URL){
	    	if(!URL.startsWith("https://www.winemag.com/"))
	    			return null;
	    	Wine wine = new Wine();
	    	try {
	    		Document document = Jsoup.connect(URL).get();
	    		String title = document.select("div.article-title").text();
	    		String description = document.select("p.description").text();
	    		Elements primaryInfo= document.select("ul.primary-info").select("li.row");
	    		Elements secondaryInfo= document.select("ul.secondary-info").select("li.row");
	    		

	    		String points = document.getElementById("points").text();
	    		
	    		
	    		Elements tasterInfo= document.select("div.taster");
	    		String testerName = tasterInfo.select("div.name").text();
	    		String testerTwitterHandle = tasterInfo.select("div.twitter-handle").text();
	    		String testerTitle = tasterInfo.select("div.short-description").text();
	    		
	    		primaryInfo.addAll(secondaryInfo);


	    		Map<String,String> map = new HashMap<String,String>();
	    		for(Element element:primaryInfo){
	    			Elements item = element.select("span");
	    			if(item !=null && item.size()==2){
	    				String key = item.get(0).text();
	    				String value = item.get(1).text();
	    				map.put(key, value);
	    			}else if(item !=null && item.size()==3){
	    				String key = item.get(0).text();
	    				String value = item.get(2).text();
	    				map.put(key, value);
	    			}
	    		}
	    		
	    		
	    		wine.setTitle(title);
	    		String price = map.get("Price").split(",")[0];
	    		wine.setPrice(price);
	    		wine.setPoints(points);
	    		wine.setDesignation(map.get("Designation"));
	    		wine.setAppellation(map.get("Appellation"));
	    		wine.setWinery(map.get("Winery"));
	    		wine.setVariety(map.get("Variety"));
	    		wine.setDatePublished(map.get("Date Published"));
	    		wine.setCategory(map.get("Category"));
	    		wine.setAlcohol(map.get("Alcohol"));
	    		wine.setBottleSize(map.get("Bottle Size"));
	    		
	    		wine.setTasterName(testerName);
	    		wine.setTasterTwitterHandle(testerTwitterHandle);
	    		wine.setTasterTitle(testerTitle);
	    		wine.setDescription(description);
	    		
	    		
	    	} catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
	    	
	    	return wine;

	    }
	    
	    public static void main(String[] args) {
	        //1. Pick a URL from the frontier
	    	WebCrawler webCrawler = new WebCrawler();
	    	webCrawler.getWebList();
	    	

	    }
	    
	    
}
