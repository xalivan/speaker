package com.speaker.service.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<A, B> {
    private A first;
    private B second;
}
