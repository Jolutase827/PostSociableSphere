package com.sociablesphere.postsociablesphere.api.dto;

import com.sociablesphere.postsociablesphere.constraints.PostValidationConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PostValidationConstraint
public class PostUpdateDto {

    @NotBlank(message = "El contenido no debe estar vacio")
    private String content;

    @NotBlank
    private Boolean isPaid;

    @NotBlank(message = "El coste no debe estar vacio")
    private BigDecimal cost;

    @NotBlank
    private Boolean isAd;

    @NotBlank(message = "Las vistas no deben estar vacias")
    private Integer maxViews;

    @NotBlank(message = "El pie de post no debe estar vacio")
    private String footer;
}
