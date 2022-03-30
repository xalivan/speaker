package com.speaker.entities;

import lombok.Data;

@Data
public class City {
    private final int id;
    private final CityName name;
    private final Country countryId;
}
