package com.speaker.dto;

import com.speaker.entities.CityName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDTO {
    private final int id;
    private final CityName name;
}
