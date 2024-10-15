package com.sociablesphere.postsociablesphere.api.dto;

import com.sociablesphere.postsociablesphere.constraints.PostValidationConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PostValidationConstraint
public class PostCreationDto {

    @NotBlank(message = "El contenido no debe estar vacio")
    private String content;

    @NotBlank(message = "El tipo no debe estar vacio")
    @Pattern(regexp = "text|image|video", message = "Type must be 'text', 'image', or 'video'")
    private String type;

    @NotNull(message = "isPaid no debe ser nulo")
    private Boolean isPaid;

    @NotNull(message = "El coste no debe estar vacio")
    private BigDecimal cost;

    @NotNull(message = "isAd no debe ser nulo")
    private Boolean isAd;

    @NotNull(message = "Las vistas no deben estar vacias")
    private Integer maxViews;

    @NotBlank(message = "El pie de post no debe estar vacio")
    private String footer;

    @NotNull(message = "El parentId no debe estar vacio")
    private Long parentId;

    @NotNull(message = "El id del usuario no debe estar vacio")
    private Long userId;
}
