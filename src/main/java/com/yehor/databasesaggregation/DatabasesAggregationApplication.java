package com.yehor.databasesaggregation;

import com.yehor.databasesaggregation.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({Config.class})
@RequiredArgsConstructor
public class DatabasesAggregationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabasesAggregationApplication.class, args);
    }
}
