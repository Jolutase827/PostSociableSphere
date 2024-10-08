package com.sociablesphere.postsociablesphere.api.dto;

import lombok.*;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserResponseDto {

    @EqualsAndHashCode.Include
    private Long id;

    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String photo;
    private String description;
    private String role;

}

