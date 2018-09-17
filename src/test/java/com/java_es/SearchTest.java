package com.java_es;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class SearchTest {
    private Client client;
    private String index="lihm_index";
    private String type="my_parent";


    @Before
    public void testBefore() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Settings settings = Settings.builder()
                .put("cluster.name", "es-dev")
                .put("client.transport.sniff", false)
                .build();
        //连接es
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.198.89"), 9305));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_simple_search() {
        GetResponse response = client.prepareGet("lihm_index", "my_parent", "44")
                .execute().actionGet();
        System.out.println("response.getId():" + response.getId());
        System.out.println("response.getSourceAsString():" + response.getSourceAsString());

    }

    @Test
    public void testSearch1() {
        SearchResponse response = client.prepareSearch(index)    // 在prepareSearch()的参数为索引库列表，意为要从哪些索引库中进行查询
//                .setSearchType(SearchType.DEFAULT)  // 设置查询类型，有QUERY_AND_FETCH  QUERY_THEN_FETCH  DFS_QUERY_AND_FETCH  DFS_QUERY_THEN_FETCH
//                 .setQuery(QueryBuilders.prefixQuery("content", "烂摊子"))// 设置相应的query，用于检索，termQuery的参数说明：name是doc中的具体的field，value就是要找的具体的值
//                .setQuery(QueryBuilders.regexpQuery("content", ".*烂摊子.*"))
//                .setQuery(QueryBuilders.prefixQuery("content", "中国"))
                .get();
        showResult(response);
    }

    /**
     * 格式化输出查询结果
     * @param response
     */
    private void showResult(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        float maxScore = searchHits.getMaxScore();  // 查询结果中的最大文档得分
        System.out.println("maxScore: " + maxScore);
        long totalHits = searchHits.getTotalHits(); // 查询结果记录条数
        System.out.println("totalHits: " + totalHits);
        SearchHit[] hits = searchHits.getHits();    // 查询结果
        System.out.println("当前返回结果记录条数：" + hits.length);
        for (SearchHit hit : hits) {
            long version = hit.version();
            String id = hit.getId();
            String index = hit.getIndex();
            String type = hit.getType();
            float score = hit.getScore();
            System.out.println("===================================================");
            String source = hit.getSourceAsString();
            System.out.println("version: " + version);
            System.out.println("id: " + id);
            System.out.println("index: " + index);
            System.out.println("type: " + type);
            System.out.println("score: " + score);
            System.out.println("source: " + source);
        }
    }


    @After
    public void cleanUp() {
        client.close();
    }
}

