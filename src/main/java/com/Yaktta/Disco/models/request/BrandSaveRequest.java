package com.Yaktta.Disco.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandSaveRequest {
    @NotBlank(message = "Brand name is required")
    private String brandName;
}
