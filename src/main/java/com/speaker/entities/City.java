package com.speaker.entities;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class City {
    private final int id;
    private final CityName name;
}
