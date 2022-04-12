package com.speaker.dto;

import com.speaker.entities.CityName;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class CityDTO {
    private final int id;
    private final CityName name;
}
