package hu.bigdata.term.webcrauler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebFetcher {
	
	public static class TokenizerMapper extends Mapper<Object, Text, Text, ObjectWritable>{
		private Text url = new Text();
		private ObjectWritable  objectWritable = new ObjectWritable();
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			
			while (itr.hasMoreTokens()) {
				url.set(itr.nextToken());
				Wine wine = fetchWebData(url.toString());
				objectWritable.set(wine.toString());
			}
			context.write(url, objectWritable);
			
	    }
    
	    public Wine fetchWebData(String URL){

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
    
	}

	public static class TokenizerReducer extends Reducer<Text,ObjectWritable,Text,ObjectWritable> {
		private ObjectWritable result = new ObjectWritable();

	    public void reduce(Text key, Iterable<ObjectWritable> values, Context context ) throws IOException, InterruptedException {
		   
		    for (ObjectWritable val : values) 
		    	result=val;
		    context.write(key, result);
	    }
	}

	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "WebCrawler");
	    job.setJarByClass(WebFetcher.class);
	    job.setMapperClass(TokenizerMapper.class);
	    job.setCombinerClass(TokenizerReducer.class);
	    job.setReducerClass(TokenizerReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(ObjectWritable.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
  
}