package cn.alvin.solr.jd.po;

public class Product {
//	商品id
	private String pid;
//	商品图片
	private String picture;
//	商品名称
	private String name;
//	商品价格
	private String price;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
