package com.sociablesphere.postsociablesphere.api.dto;

import com.sociablesphere.postsociablesphere.constraints.PostValidationConstraint;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El id el del usuario no debe estar vacio")
    @Pattern(regexp = "text|image|video", message = "Type must be 'text', 'image', or 'video'")
    private String type;

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

    @NotBlank(message = "El parentId no debe estar vacio")
    private Long parentId;

    @NotBlank(message = "El id el del usuario no debe estar vacio")
    private Long userId;


}
