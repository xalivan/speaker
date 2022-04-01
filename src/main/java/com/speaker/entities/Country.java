package com.speaker.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Country {
     private final int id;
     private final CountryName name;
     private final City city;
}
