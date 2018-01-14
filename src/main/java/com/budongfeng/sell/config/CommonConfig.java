package com.budongfeng.sell.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties( prefix = "server")   //  获取yml配置文件中配置的内容
public class CommonConfig {

    @Value("${server.context-path}")
    private String context_path;
}
