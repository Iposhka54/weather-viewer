package com.iposhka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {
    private Integer id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
