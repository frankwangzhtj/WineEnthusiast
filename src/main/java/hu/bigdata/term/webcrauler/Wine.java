package hu.bigdata.term.webcrauler;

public class Wine {
	private long id;
	
	private String title;
	private String description;
	private String designation;
	private String points;
	private String price;
	private String country;
	private String province;
	private String region1;
	private String region2;
	private String tasterName;
	private String tasterTwitterHandle;
	private String tasterTitle;
	private String variety;
	private String winery;
	private String appellation;
	private String datePublished;
	private String category;
	private String bottleSize;
	private String alcohol;
	
	@Override
	public String toString() {
		String wine = title + "\t" + designation + "\t" + points + "\t" + price + "\t" + appellation + "\t" 
				+ tasterName + "\t" + tasterTwitterHandle + "\t" + tasterTitle + "\t" + variety  + "\t" 
				+ variety + "\t" + winery + "\t" + datePublished + "\t" + category + "\t" + bottleSize + "\t"
				+ alcohol + "\t" + description ;

		return wine;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRegion1() {
		return region1;
	}
	public void setRegion1(String region1) {
		this.region1 = region1;
	}
	public String getRegion2() {
		return region2;
	}
	public void setRegion2(String region2) {
		this.region2 = region2;
	}
	public String getTasterName() {
		return tasterName;
	}
	public void setTasterName(String tasterName) {
		this.tasterName = tasterName;
	}
	public String getTasterTwitterHandle() {
		return tasterTwitterHandle;
	}
	public void setTasterTwitterHandle(String tasterTwitterHandle) {
		this.tasterTwitterHandle = tasterTwitterHandle;
	}
	public String getTasterTitle() {
		return tasterTitle;
	}
	public void setTasterTitle(String tasterTitle) {
		this.tasterTitle = tasterTitle;
	}
	public String getVariety() {
		return variety;
	}
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public String getWinery() {
		return winery;
	}
	public void setWinery(String winery) {
		this.winery = winery;
	}
	public String getAppellation() {
		return appellation;
	}
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	public String getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBottleSize() {
		return bottleSize;
	}
	public void setBottleSize(String bottleSize) {
		this.bottleSize = bottleSize;
	}
	public String getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}

	
}
