package com.yehor.databasesaggregation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    private long id;
    private String username;
    private String name;
    private String surname;

}
