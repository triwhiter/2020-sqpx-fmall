package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.entity.Admin;
import com.ctgu.fmall.mapper.AdminMapper;
import com.ctgu.fmall.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 16:59
 * @PackageName:com.ctgu.fmall.service.impl
 * @Description: TODO
 * @Version:V1.0
 */
@Slf4j
@Component
public class AdminDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number",username);
        Admin dbAdmin=adminMapper.selectOne(wrapper);
        if(dbAdmin == null){
            return null;
        }else{
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if(dbAdmin.getIsSadmin().equals(1)){
                authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
            }
            else{
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
//            authorities.add(new JaasGrantedAuthority("ROLE_ADMIN"));
//            MyUserDetails user = new MyUserDetails(dbUser,dbUser.getEmail(),dbUser.getPassword(),authorities);
            User admin = new User(dbAdmin.getId().toString(),dbAdmin.getPassword(),authorities);
            log.info("当前认证管理员："+dbAdmin);
//            MyUserDetails user = new User(dbUser.getEmail(),passwordEncoder.encode(dbUser.getPassword()),authorities,db);
//            System.out.println("管理员信息："+user.getUser().getUsername()+"   "+passwordEncoder.encode(dbUser.getPassword())+"  "+user.getAuthorities());
            return admin;
        }
    }
}
