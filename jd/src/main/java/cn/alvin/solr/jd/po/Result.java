package cn.alvin.solr.jd.po;

import java.util.List;

public class Result {
//	当前页
	private Integer curPage;
//	页数
	private Integer pageCount;
//	记录数
	private Integer recordCount;
//	商品结果集合list
	private List<Product> productList;
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
