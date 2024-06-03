package com.jeremy;


import cn.hutool.core.bean.BeanUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson.JSON;
import com.jeremy.model.ProductTest;
import com.jeremy.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class ElasticsearchApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void add() {
		// 公司 es 版本 8.7.0 springboot 版本 3.1.5
		// 家里 es 版本 6.8.12 springboot 版本 2.2.6.RELEASE
//		ProductTest product = new ProductTest("1","1","aa",3);
//		ProductTest product2 = new ProductTest("2","1","aa1",2);
//		ProductTest product3 = new ProductTest("3","2","aa2",12);
//		ProductTest product4 = new ProductTest("4","2","aa3",44);
//		ProductTest product5 = new ProductTest("5","2","aa4",16);
		ProductTest product6 = new ProductTest("7","2","aa5",33);
		List<ProductTest> productTestList = new ArrayList<>();
		productTestList.add(product6);
//		productTestList.add(product2);
//		productTestList.add(product3);
//		productTestList.add(product4);
//		productTestList.add(product5);
		Iterable<ProductTest> productTestIterable = productService.saveAll(productTestList);
		System.out.println(productTestIterable);
	}

//	@Test
//	public void delete() {
//		productService.deleteProduct("1");
//	}

	@Test
	public void find() {
		ProductTest productTest = productService.findById("2");
		System.out.println(productTest);
	}

	@Autowired
	private ElasticsearchClient elasticsearchClient;

	@Test
	public void queryAll() throws IOException {
		/*if (StringUtils.isNotBlank(queryOrderDataIndexDto.getOrderNo())) {
			queryBuilder.must(TermQuery.of(builder -> builder.field("orderNo").value(queryOrderDataIndexDto.getOrderNo()))._toQuery());
		}*/
		// 聚合函数
		/*TermsAggregation productAggregation = AggregationBuilders.terms(t -> t.field("code")
				.size(100)).terms();
		// 创建聚合 Map
		Map<String, Aggregation> aggregations = Map.of(
				"code_aggregation", Aggregation.of(a -> a.terms(productAggregation))
		);*/
		BoolQuery.Builder queryBuilder = new BoolQuery.Builder();


		SearchRequest searchRequest = new SearchRequest.Builder()
				.index("product_test")
				.query(q -> q.bool(queryBuilder.build()))
				//.aggregations("code_terms", agg -> agg.terms(t -> t.field("code")))
				.build();
		SearchResponse<Map> searchResponse = elasticsearchClient.search(searchRequest,Map.class);
		System.out.println(searchResponse);
		List<ProductTest> hits = searchResponse.hits().hits().stream()
				.map(Hit::source)
				.map(obj -> BeanUtil.fillBeanWithMap(obj, new ProductTest(), false))
				.collect(Collectors.toList());
		System.out.println(hits);
		/*// 解析聚合结果
		TermsAggregate codeTerms = searchResponse.aggregations().get("code_terms").terms();
		List<? extends Bucket> buckets = codeTerms.buckets().array();
		for (Bucket bucket : buckets) {
			String code = bucket.key().stringValue();
			long count = bucket.docCount();
			System.out.printf("Code: %s, Count: %d%n", code, count);
		}*/
		/*List<ProductTest> searchOrderDataVos = List.of(searchResponse.aggregations().get("code_terms")
				.sterms().buckets()).stream().map(Buckets::array).flatMap(List::stream)
				.flatMap(obj -> obj.aggregations().get("product_top_hits").topHits()
				.hits().hits().stream().map(Hit::source).map(hit -> JSON.parseObject(hit.toJson().toString(), ProductTest.class)).peek(p -> p.setProductCount(obj.docCount()))).toList();
		System.out.println(searchOrderDataVos);*/



	}

	

}
