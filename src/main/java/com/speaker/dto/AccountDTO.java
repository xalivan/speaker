package com.speaker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel
public class AccountDTO {
    @JsonIgnore
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final int countryId;
    private final int cityId;
}
