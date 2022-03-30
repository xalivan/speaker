package com.speaker.entities;

import lombok.Data;

@Data
public class Country {
     private final int id;
     private final CountryName name;
     private final City city;
}
