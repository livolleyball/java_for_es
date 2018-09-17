package com.java_es;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class IndexTest {
    private Client client;
    private String index="lihm_index_00";
    private String type="my_parent_00";


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
    public void createIndex(){
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("name", "flume");
        source.put("author", "Cloudera");
        source.put("version", "1.8.0");
        IndexResponse response = client.prepareIndex(index, type, "5").setSource(source).get();
        System.out.println(response.status());

    }

    @Test
    public void CreateIndexBulk() throws Exception {
        // 创建BulkPorcessor对象
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
                // TODO Auto-generated method stub
            }

            // 执行出错时执行
            @Override
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
                // TODO Auto-generated method stub
                System.out.println(paramBulkResponse.status());
            }
        })
                // 1w次请求执行一次bulk
                .setBulkActions(10000)
                // 1gb的数据刷新一次bulk
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // 固定5s必须刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(1)
                // 设置退避, 100ms后执行, 最大请求3次
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

//         添加insert请求
        int x = 1;
        while( x < 2000 ) {
            bulkProcessor.add(new IndexRequest(index, type, String.valueOf(x))
                    .source(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("user_id", String.valueOf(x))
                            .field("age", x)
                            .endObject()));
            x++;
        }




        // 关闭
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        // 或者
        bulkProcessor.close();
    }


    @After
    public void cleanUp() {
        client.close();
    }
}
