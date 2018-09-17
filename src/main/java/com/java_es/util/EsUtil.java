//package com.java_es.util;
//import java.io.IOException;
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//import java.net.InetAddress;
//
//import org.apache.log4j.Logger;
//import org.elasticsearch.ElasticsearchException;
//import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequestBuilder;
//import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryResponse;
//import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequestBuilder;
//import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryResponse;
//import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequestBuilder;
//import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
//import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequestBuilder;
//import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotResponse;
//import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequestBuilder;
//import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
//import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotRequestBuilder;
//import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotResponse;
//import org.elasticsearch.action.admin.indices.close.CloseIndexRequestBuilder;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
//import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
////import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
////import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
//import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
//import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.ClusterAdminClient;
//import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
//import org.elasticsearch.cluster.health.ClusterIndexHealth;
//import org.elasticsearch.cluster.health.ClusterHealthStatus;
//
//import org.elasticsearch.client.Requests;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
////import org.elasticsearch.common.collect.ImmutableList;
////import org.elasticsearch.common.settings.ImmutableSettings;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.rest.RestStatus;
//
//import com.google.gson.JsonObject;
//import org.junit.Before;
//import org.junit.Test;
//
//
//public class EsUtil {
//    private Client client;
//    private String ip ="192.168.198.89";
//    private Integer port =9305;
//    private String index = "bigdata";
//    private String type = "product";
//    private ClusterAdminClient clusterAdminClient;
//
//    @Test
//    @Before
//    public void testBefore() {
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//        Settings settings = Settings.builder()
//                .put("cluster.name", "es-dev")
////                .put("cluster.name", "es-qa")
//                .put("client.transport.sniff", false)
//                .build();
//        //连接es
//        try {
//            client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
////			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.100.2.108"), 9300));//10.100.2.108
//            clusterAdminClient = client.admin().cluster();
//
//            ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
//            String clusterName = healths.getClusterName();
//            int numberOfDataNodes = healths.getNumberOfDataNodes();
//            int numberOfNodes = healths.getNumberOfNodes();
//
//            for (ClusterIndexHealth health : healths.getIndices().values()) {
//                String index = health.getIndex();
//                int numberOfShards = health.getNumberOfShards();
//                int numberOfReplicas = health.getNumberOfReplicas();
//                ClusterHealthStatus status = health.getStatus();
//                System.out.println(status.value());
//            }
//            System.out.println(client.admin().cluster().prepareHealth().get());
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
//}
