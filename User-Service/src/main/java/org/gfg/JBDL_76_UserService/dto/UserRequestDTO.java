package org.gfg.JBDL_76_UserService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.gfg.JBDL_76_UserService.model.UserIdentifier;
import org.gfg.JBDL_76_UserService.model.Users;
@Getter
public class UserRequestDTO {

    private String name;
    private String contact;
    @NotBlank(message = "email should not be blank")
    private String email;
    private String address;
    @NotNull(message = "userIdentifier will be required")
    private UserIdentifier identifier;
    @NotBlank(message = "userIdentifier will be required")
    private String userIdentifierValue;
    @NotBlank(message = "password should not be blank")
    private String password;

    public Users toUser() {

        return Users.builder().
                name(this.name).
                email(this.email).
                phoneNo(this.contact).
                address(this.address).
                userIdentifierValue(this.userIdentifierValue).
                identifier(this.identifier).
                enabled(true).
                accountNonExpired(true).
                accountNonLocked(true).
                credentialsNonExpired(true).
                build();
    }
}
