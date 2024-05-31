package jv.bazar.amacame.dto.req;


import jv.bazar.amacame.entity.Roles;
import jv.bazar.amacame.enums.RoleEnum;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpUserReqDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private Set<RoleEnum> roles;
}
