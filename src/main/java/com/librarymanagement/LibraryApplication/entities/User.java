package com.librarymanagement.LibraryApplication.entities;

import com.librarymanagement.LibraryApplication.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USER")
public class User implements UserDetails {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "IS_NOT_LOCKED")
    private Boolean isNotLocked;

    @Column(name = "PASSWORD_ATTEMPT_COUNT")
    private Integer passwordAttemptCount;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<ReserveAndBorrow> reserveAndBorrowList;

    @OneToMany(mappedBy = "librarian")
    private List<LibrarianAccount>  accountList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status;
    }
}
