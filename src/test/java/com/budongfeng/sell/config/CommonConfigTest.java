package com.budongfeng.sell.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CommonConfigTest {

    @Autowired
    private CommonConfig commonConfig;

    @Test
    public void getContext_path() throws Exception {
        log.info("【获取项目名称】 name={} ",commonConfig.getContext_path());
    }

}