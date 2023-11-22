package com.yehor.databasesaggregation.config;

import lombok.Data;

@Data
public class DatabaseConfig {

    private String name;
    private String url;
    private String username;
    private String password;

}
