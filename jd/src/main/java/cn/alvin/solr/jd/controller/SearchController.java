package cn.alvin.solr.jd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.alvin.solr.jd.po.Result;
import cn.alvin.solr.jd.service.SearchService;

@Controller
public class SearchController {
	@Resource
	private SearchService searchService;
	@RequestMapping("/list.action")
	public String list(String queryString, String catalog_name, String price, Integer page, String sort,Model model) {
		// 1.执行搜索
				Result result = this.searchService.searchProduct(queryString, 
						catalog_name, price, page, sort);
				
				// 2.响应搜索结果数据
				model.addAttribute("result", result);
				
				// 3.设置参数回显
				model.addAttribute("queryString", queryString);
				model.addAttribute("catalog_name", catalog_name);
				model.addAttribute("price", price);
				model.addAttribute("sort", sort);

		
		return "product_list";
	}
}
