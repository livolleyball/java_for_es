package com.java_es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;



@Configuration
public class EsConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {

        Settings settings = Settings.builder()
                //指定集群名称
                .put("cluster.name", "dev-es")
                //探测集群中机器状态
//                .put("client.transport.sniff", true)
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.198.89"), 9305));

        return client;

//
//        TransportClient transportClient = null;
//        try {
//            Settings settings = Settings.builder().put("cluster.name", "es-dev").build();
//            //开发环境
//            transportClient = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.198.89"), 9305));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//
    }

}
