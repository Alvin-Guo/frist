package cn.alvin.solr.jd.service;

import cn.alvin.solr.jd.po.Result;

public interface SearchService {
	// 搜索商品方法
	// 参数需要根据页面表单确定
	public Result searchProduct(String queryString,String catalog_name,
			String price,Integer page,String sort);

}
