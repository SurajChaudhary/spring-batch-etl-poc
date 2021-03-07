package com.devtalk.batch.etl.config;

import com.devtalk.batch.etl.model.Users;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserItemWriter implements ItemWriter<Users> {
    @Autowired
    private RestTemplate restTemplate;
    private final String createPersonUrl = "http://localhost:9090/users";

    @Override
    public void write(List<? extends Users> list) throws Exception {
        restTemplate = new RestTemplate();
        list.forEach(item -> {
            System.out.println("Going to post : " + item);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Users> request = new HttpEntity<>(item, headers);
            restTemplate.postForLocation(createPersonUrl, request);
        });
    }
}
