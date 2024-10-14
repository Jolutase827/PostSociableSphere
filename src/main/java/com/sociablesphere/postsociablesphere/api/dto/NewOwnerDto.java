package com.sociablesphere.postsociablesphere.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewOwnerDto {


    @NotBlank(message = "El id el del post no debe estar vacio")
    private Long postId;

    @NotBlank(message = "El id el del usuario no debe estar vacio")
    private Long userId;
}
