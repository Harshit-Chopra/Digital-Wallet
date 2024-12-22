package org.gfg.JBDL_76_UserService.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(nullable = false, unique = true, length =15)
    private String phoneNo;

    @Column(nullable = false, unique = true, length =50)
    private String email;

    private String address;

    @Enumerated
    private UserIdentifier identifier;
    private String userIdentifierValue;

    private String password;
    private String authorities;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @CreationTimestamp
    protected Date createdOn;

    @UpdateTimestamp
    protected Date updatedOn;
    @Enumerated
    private UserType userType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(",")).map(a -> new SimpleGrantedAuthority(a)).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
