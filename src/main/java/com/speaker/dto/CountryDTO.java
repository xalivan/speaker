package com.speaker.dto;

import com.speaker.entities.CountryName;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class CountryDTO {
    private final int id;
    private final CountryName name;
    private final CityDTO cityDTO;
}
