package com.paper.demo.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import com.paper.demo.entity.bo.Paper;
import com.paper.demo.mapper.PaperMapper;
import com.paper.demo.util.AjaxResult;
import com.sun.deploy.net.HttpResponse;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-21
 */
@Controller
public class ElasticSearchController {

    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    PaperMapper paperMapper;

    private ElasticsearchOperations elasticsearchOperations;


    public ElasticSearchController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }


    @RequestMapping("/search")
    public String search() {
        return "search";
    }

    /**
     * 这一段不需要了 防止乱掉接口删除索引
     *
     * @throws IOException
     */

    @DeleteMapping("/deleteAll")
    @ResponseBody
    public void deleteAll(Integer id) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("paper_index*");
//        DeleteRequest request = new DeleteRequest("paper_index", id.toString());
//        DeleteResponse delete = highLevelClient.delete(request, RequestOptions.DEFAULT);
//        AcknowledgedResponse response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        AcknowledgedResponse response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);

    }


    @PostMapping("/deleteIndex")
    @ResponseBody
    public String deleteIndex(@RequestBody JSONObject id) throws IOException {
        String paperId = id.getStr("paperId");
        DeleteRequest request = new DeleteRequest("paper_index", paperId.toString());
        DeleteResponse delete = highLevelClient.delete(request, RequestOptions.DEFAULT);
        return AjaxResult.msg(HttpServletResponse.SC_OK,"删除成功");
    }
//
//
//    @PostMapping("/create")
//    public void createIndex() throws IOException {
//        CreateIndexRequest request = new CreateIndexRequest("paper_index");
//        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
//        if (createIndexResponse.isAcknowledged()) {
//            Console.log("创建index成功!");
//        } else {
//            Console.log("创建index失败!");
//        }
//    }
    @PostMapping("/inserAll")
    @ResponseBody
    public void insertIndex() throws IOException {
        List<Paper> paperList = paperMapper.getAllPapers();
        BulkRequest request = new BulkRequest("paper_index");
        paperList.forEach(t -> {
            Paper testData = new Paper();
            testData.setPaperId(t.getPaperId());
            testData.setTitle(t.getTitle());
            testData.setCatId(t.getCatId());
            testData.setUrl(t.getUrl());
            IndexRequest indexRequest = new IndexRequest("paper_index");
            indexRequest.id(String.valueOf(testData.getPaperId()));
            indexRequest.source(new JSONObject(testData).toString()
                    , XContentType.JSON);
            request.add(indexRequest);
        });

        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
        Console.log("插入状态:{} 数量:{} ", response.status(), response.getItems().length);
    }


    @PostMapping("/insertPaper")
    @ResponseBody
    public String inserPaper(@RequestBody Paper paper) throws IOException {

        BulkRequest request = new BulkRequest("paper_index");

        IndexRequest indexRequest = new IndexRequest("paper_index");
        indexRequest.id(String.valueOf(paper.getPaperId()));
        indexRequest.source(new JSONObject(paper).toString()
                , XContentType.JSON);
        request.add(indexRequest);
        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
        return AjaxResult.msg(HttpServletResponse.SC_OK,"插入成功");
    }


    @GetMapping(value = "search.do")
    public String query(@RequestParam("keyword") String keyword,
                        @RequestParam(value = "page", defaultValue = "1") Integer page
            , Model model) throws IOException {
        SearchRequest searchRequest = new SearchRequest("paper_index");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", keyword);
        boolQueryBuilder.must(matchQueryBuilder);
        searchSourceBuilder.from((page - 1) * 12);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(12);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();

        TotalHits totalHits = hits.getTotalHits();
        List<Long> pageList = new ArrayList<Long>();
        long maxpage = totalHits.value / 12 + (totalHits.value % 12 > 0 ? 1 : 0);
        for (long i = 1; i <= maxpage; i++) {
            pageList.add(i);
        }
        SearchHit[] searchHits = hits.getHits();
        List<Paper> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            Map<String, Object> map = hit.getSourceAsMap();
            Paper paper = new Paper((Integer) map.get("paperId"), (Integer) map.get("catId"),
                    (String) map.get("url"), (String) map.get("title"));
            list.add(paper);
        }
        model.addAttribute("maxPage", maxpage);
        model.addAttribute("searchList", list);
        model.addAttribute("pageList", pageList);
        model.addAttribute("currentpage", page);
        return "search::searchFrame";
    }

}
