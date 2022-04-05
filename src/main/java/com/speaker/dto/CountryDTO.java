package com.speaker.dto;

import com.speaker.entities.City;
import com.speaker.entities.CountryName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {
    private final int id;
    private final CountryName name;
    private final City city;
}
