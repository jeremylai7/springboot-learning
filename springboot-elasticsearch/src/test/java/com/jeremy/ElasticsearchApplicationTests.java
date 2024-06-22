package com.jeremy;


import cn.hutool.core.bean.BeanUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson2.JSON;
import com.jeremy.model.ProductTest;
import com.jeremy.service.ProductService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		ProductTest product6 = new ProductTest("9","1","aa7",12);
		List<ProductTest> productTestList = new ArrayList<>();
		productTestList.add(product6);
//		productTestList.add(product2);
//		productTestList.add(product3);
//		productTestList.add(product4);
//		productTestList.add(product5);
		Iterable<ProductTest> productTestIterable = productService.saveAll(productTestList);
		System.out.println(productTestIterable);
	}

	@Test
	public void delete() {
		List<String> list = Arrays.asList("5","6","7");
		for (String string : list) {

			productService.deleteProduct(string);
		}
	}

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

		// SELECT materialCode, COUNT(*) FROM erp_order_data WHERE ... GROUP BY materialCode LIMIT ...;
		TermsAggregation productAggregation = AggregationBuilders.terms(t -> t.field("code.keyword")
				.size(1000)).terms();
		// 每个分组获取最新一条数据，取id最大的值，
		Aggregation productTopHits = AggregationBuilders.topHits(t -> t.source(s -> s.fetch(true))
				.from(0)
				.size(1)
				.sort(SortOptions.of(s -> s.field(d -> d.field("id.keyword").order(SortOrder.Desc)))));
		// 分组结果分页处理
		Aggregation productSortPage = AggregationBuilders.bucketSort(b -> b.sort(Lists.newArrayList())
				.from(100)
				.size(1000));
		// materialCode 字段的唯一值数量 COUNT(DISTINCT materialCode)
		Aggregation productCount = AggregationBuilders.cardinality(c -> c.field("code.keyword"));

		SearchResponse<Map> searchResponse2 = elasticsearchClient.search(s -> s.index("product_test")
				.query(q -> q.bool(queryBuilder.build()))
				.from(0)
				.size(0)
				.aggregations("product_aggregate", agg -> agg.terms(productAggregation)
						.aggregations("product_top_hits", productTopHits)
						.aggregations("bucket_sort", productSortPage))
				.aggregations("product_aggregate_count", productCount), Map.class);

		System.out.println(searchResponse2);
		List<ProductTest> hits = searchResponse2.hits().hits().stream()
				.map(Hit::source)
				.map(obj -> BeanUtil.fillBeanWithMap(obj, new ProductTest(), false))
				.collect(Collectors.toList());
		System.out.println(hits);


		SearchRequest searchRequest = new SearchRequest.Builder()
				.index("product_test")
				.query(q -> q.bool(queryBuilder.build()))
				.aggregations("code_terms", agg -> agg.terms(productAggregation))
				.build();
		SearchResponse<Map> searchResponse = elasticsearchClient.search(searchRequest,Map.class);
		System.out.println(searchResponse);

		// 解析查询结果
		List<ProductTest> hits2 = searchResponse.hits().hits().stream()
				.map(Hit::source)
				.map(obj -> BeanUtil.fillBeanWithMap(obj, new ProductTest(), false))
				.collect(Collectors.toList());
		/*List<SearchOrderProductDataVo> searchOrderDataVos = List.of(searchResponse.aggregations().get("product_aggregate").sterms().buckets()).stream().map(Buckets::array).flatMap(List::stream).flatMap(obj -> obj.aggregations().get("product_top_hits").topHits().hits().hits().stream().map(Hit::source).map(hit -> JSON.parseObject(hit.toJson().toString(), SearchOrderProductDataVo.class)).peek(p -> p.setProductCount(obj.docCount()))).toList();

.aggregations("product_aggregate", agg -> agg.terms(productAggregation)
		// 解析聚合结果
		TermsAggregate codeTerms = searchResponse.aggregations().get("code_terms").terms();
		List<? extends Bucket> buckets = codeTerms.buckets().array();
		for (Bucket bucket : buckets) {
			String code = bucket.key().stringValue();
			long count = bucket.docCount();
			System.out.printf("Code: %s, Count: %d%n", code, count);
		}*/
		System.out.println(hits2);
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


	@Test
	public void copyOne() throws IOException {

		QueryOrderDataIndexDto queryOrderDataIndexDto = new QueryOrderDataIndexDto();
		queryOrderDataIndexDto.setPageNum(1);
		queryOrderDataIndexDto.setPageSize(10);
		BoolQuery.Builder queryBuilder = new BoolQuery.Builder();
		// select code,count(*) from xx where group by code limit 10;
		TermsAggregation productAggregation = AggregationBuilders.terms(t -> t.field("code.keyword")
				.size(queryOrderDataIndexDto.getPageNum() * queryOrderDataIndexDto.getPageSize())).terms();
		// 每个分组获取最新一条数据，
		Aggregation productTopHits = AggregationBuilders.topHits(t -> t.source(s -> s.fetch(true)).from(0).size(1).sort(SortOptions.of(s -> s.field(d -> d.field("id.keyword").order(SortOrder.Desc)))));
		// 排序
		Aggregation productSortPage = AggregationBuilders.bucketSort(b -> b.sort(Lists.newArrayList()).from((queryOrderDataIndexDto.getPageNum() - 1) * queryOrderDataIndexDto.getPageSize()).size(queryOrderDataIndexDto.getPageSize()));
		Aggregation productSortPage2 = AggregationBuilders.bucketSort(b -> b
				.sort(s -> s.field(f -> f.field("product_sum").order(SortOrder.Asc)))
				.from((queryOrderDataIndexDto.getPageNum() - 1) * queryOrderDataIndexDto.getPageSize())
				.size(queryOrderDataIndexDto.getPageSize())
		);

		//Aggregation productCount = AggregationBuilders.cardinality(c -> c.field("code.keyword"));
		Aggregation productSum = AggregationBuilders.sum(s -> s.field("price"));

		/*SearchResponse<Map> searchResponse = elasticsearchClient.search(s -> s.index("product_test")
				.query(q -> q.bool(queryBuilder.build())).from(0).size(0)
				.aggregations("product_aggregate", agg -> agg.terms(productAggregation)
					.aggregations("product_top_hits", productTopHits)
					.aggregations("bucket_sort", productSortPage))
				    .aggregations("product_sum", productSum), Map.class);*/
		SearchResponse<Map> searchResponse2 = elasticsearchClient.search(s -> s.index("product_test")
						.query(q -> q.bool(queryBuilder.build())).from(0).size(0)
						.aggregations("product_aggregate", agg -> agg.terms(productAggregation)
						.aggregations("product_sum", productSum)  // Sum aggregation at the correct level
						.aggregations("product_top_hits", productTopHits)
						.aggregations("bucket_sort", productSortPage)), Map.class);

		/*List<ProductTest> searchOrderDataVos = List.of(searchResponse.aggregations().get("product_aggregate").sterms().buckets()).stream()
				.map(Buckets::array).flatMap(List::stream).flatMap(obj -> obj.aggregations().get("product_top_hits")
						.topHits().hits().hits().stream().map(Hit::source).map(hit -> JSON.parseObject(hit.toJson().toString(), ProductTest.class))
						.peek(p -> p.setProductCount(obj.docCount()))).toList();*/


		List<ProductTest> searchOrderDataVos3 = searchResponse2.aggregations()
				.get("product_aggregate")
				.sterms()
				.buckets()
				.array()
				.stream()
				.flatMap(bucket -> {
					// 获取该分组的 sum 值
					double sum = bucket.aggregations().get("product_sum").sum().value();
					long docCount = bucket.docCount();

					// 提取 top hits 并将 sum 值赋给 productCount 字段
					return bucket.aggregations().get("product_top_hits").topHits().hits().hits()
							.stream()
							.map(hit -> JSON.parseObject(hit.source().toString(), ProductTest.class))
							.peek(p -> {
								p.setSumPrice(sum);
								p.setProductCount(docCount);

							});
				})
				.collect(Collectors.toList());



		System.out.println(searchOrderDataVos3);
	}

	

}
