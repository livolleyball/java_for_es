package com.java_es;


import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class DeleteTest {
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


    /**
     * 删除操作
     */
    @Test
    public void testDelete() {
        DeleteResponse response = client.prepareDelete(index, type, "45").get();
        // 删除其实是打上标签，并过滤掉
        System.out.println(response.getVersion());
    }

}
