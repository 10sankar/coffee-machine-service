package com.learning.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Accessors(chain = true)
public class IngredientTray {
    private final String name;
    private Long capacity;
    private String description;
    private Long availableQuantity;
}
