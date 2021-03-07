package com.devtalk.batch.etl.config;

import com.devtalk.batch.etl.model.Users;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<Users,Users> {
    @Override
    public Users process(Users user) throws Exception {
        System.out.println("Processing :: " + user);
        return user;
    }
}
