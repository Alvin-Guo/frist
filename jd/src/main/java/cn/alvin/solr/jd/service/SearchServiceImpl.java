package cn.alvin.solr.jd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import cn.alvin.solr.jd.po.Product;
import cn.alvin.solr.jd.po.Result;
@Service
public class SearchServiceImpl implements SearchService {
	@Resource
	private HttpSolrServer server;
	@Override
	public Result searchProduct(String queryString, String catalog_name, String price, Integer page, String sort)  {
		//建立查询对象
		SolrQuery sq = new SolrQuery();
		//如果用户没有输入关键词,设置为查询全部
		if (StringUtils.isNotBlank(queryString)) {
			sq.setQuery(queryString);
		}else {
			sq.setQuery("*:*");
		}
		//设置默认搜索域
		sq.set("df", "product_keywords");
		//设置过滤条件
		//分类条件
		if (StringUtils.isNotBlank(catalog_name)) {
			catalog_name="product_catalog_name"+catalog_name;
		}
		if (StringUtils.isNotBlank(price)) {
			String[] split = price.split("-");
			price="product_price:["+split[0]+" TO "+split[1]+"]";
		}
		sq.setFilterQueries(catalog_name,price);
		
		//设置分页
		if (page==null) {
			page=1;
		}
		int pageSize = 10;
		sq.setStart((page-1)*pageSize);
		sq.setRows(pageSize);
		//设置排序
		if ("1".equals(sort)) {
			sq.setSort("product_price",ORDER.asc);
		} else {
			sq.setSort("product_price",ORDER.desc);
		}
		
		//设置高亮显示
		sq.setHighlight(true);
		sq.addHighlightField("product_name");
		sq.setHighlightSimplePre("<font color='red'>");
		sq.setHighlightSimplePost("<font/>");	
		
		QueryResponse response=null;
		//执行搜索
		try {
			response = server.query(sq);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		//获取结果集
		SolrDocumentList results = response.getResults();
		//获取高亮结果集
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//建立搜索结果对象
		Result result = new Result();
		//设置结果
		//设置当前页
		result.setCurPage(page);
		//设置总页数
		int totals=(int) results.getNumFound();
		int pageCount = 0;
		if (totals%pageSize==0) {
			pageCount=totals/pageSize;
		}else {
			pageCount=(totals/pageSize)+1;
		}
		result.setPageCount(pageCount);
		result.setRecordCount(totals);
		
		//设置搜索的结果集合
		List<Product> productList = new ArrayList<>();
		
		for(SolrDocument doc : results){
			String pid = doc.get("id").toString();
			String pname="";
			
			List<String> list = highlighting.get(pid).get("product_name");

			if (list!=null&&list.size()>0) {
				pname=list.get(0);
			}else{
				pname=doc.get("product_name").toString();
			}
			String ppicture = doc.get("product_picture").toString();
			String pprice = doc.get("product_price").toString();
			
			//建立商品对象
			Product pro = new Product();
			//设置商品
			pro.setPid(pid);
			pro.setName(pname);
			pro.setPrice(pprice);
			pro.setPicture(ppicture);
			productList.add(pro);
		}
		result.setProductList(productList);
	
		return result;
	}

}
