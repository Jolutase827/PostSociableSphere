package com.sociablesphere.postsociablesphere.api.dto;


import com.sociablesphere.postsociablesphere.constraints.PostValidationConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Set;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PostValidationConstraint
public class PostResponseDto {
    private Long id;
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

    @NotBlank(message = "Las vistas restantes no deben estar vacias")
    private Integer viewsRemaining;

    @NotBlank(message = "El pie de post no debe estar vacio")
    private String footer;

    @NotBlank(message = "El parentId no debe estar vacio")
    private Long parentId;

    private Set<LikeResponseDto> likes;
    private Set<UserResponseDto> postOwners;
}
