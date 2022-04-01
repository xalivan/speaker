package com.speaker.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private final int id;
    private final CityName name;
}
