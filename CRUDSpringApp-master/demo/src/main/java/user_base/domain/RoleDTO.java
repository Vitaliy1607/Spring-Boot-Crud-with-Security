package user_base.domain;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RoleDTO {

    private String id;

    private String role;
}
