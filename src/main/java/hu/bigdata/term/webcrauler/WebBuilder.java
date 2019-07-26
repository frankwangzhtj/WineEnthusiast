package hu.bigdata.term.webcrauler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebBuilder {
	private HashSet<String> links;
	private static final int MAX_DEPTH = 2;
	public WebBuilder() {
		links = new HashSet<String>();
	}
	public static void main(String[] args){
		WebBuilder wb = new WebBuilder();

		for(int i=1;i<=13357;i++)  //13357 267127 webpage
			wb.getPageLinks("https://www.winemag.com/?s=&drink_type=wine&page="+i, 0,"./resources/webpage");
    	
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

                for (Element element:hrefElements){
                	Elements h =element.getAllElements();
                	String href = h.get(0).attr("href");
                	if(href.startsWith("https://www.winemag.com"))
                	urls.add(href);
                }
    	    	File webs = new File(fileName);
                BufferedWriter websbf = new BufferedWriter(new FileWriter(webs,true));
                for(String str:urls)
                	websbf.write(str+"\n");
                
                	
                websbf.close();
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
}
