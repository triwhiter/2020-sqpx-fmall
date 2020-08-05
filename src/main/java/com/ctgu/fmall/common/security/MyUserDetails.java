package com.ctgu.fmall.common.security;

import com.ctgu.fmall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Auther: yanghao
 * @Date: 2020/8/1 19:00
 * @PackageName:com.ctgu.fmall.common
 * @Description: 自定义认证用户
 * @Version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDetails implements UserDetails {
    private User user;
    private Collection<GrantedAuthority> authorities;
    private String username;
    private String password;

    public  MyUserDetails(User user,String username,String password,Collection<GrantedAuthority> authorities ){
        this.user=user;
        this.username=username;
        this.password=password;
        this.authorities=authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    public User getUse() {
        return this.user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
