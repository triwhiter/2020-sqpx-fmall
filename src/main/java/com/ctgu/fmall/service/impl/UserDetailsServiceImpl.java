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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails  loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过手机号同时查询用户表和管理员表
        QueryWrapper<com.ctgu.fmall.entity.User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number",username);
        com.ctgu.fmall.entity.User dbUser=userMapper.selectOne(wrapper);

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("phone_number",username);
        Admin dbAdmin=adminMapper.selectOne(adminQueryWrapper);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        User user = null;
        //既不是用户也不是管理员
        if(dbUser == null){
            //当前用户是管理员
            if(dbAdmin!=null){
                if(dbAdmin.getIsSadmin().equals(1)){
                    //超级管理员权限
                    authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
                    log.info("当前是超级管理员："+dbAdmin);
                }
                else{
                    //普通管理员权限
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    log.info("当前是普通管理员："+dbAdmin);
                }
                user = new User(dbAdmin.getId().toString(),dbAdmin.getPassword(),authorities);
            }
        }
        else{
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                log.info("当前是普通用户："+dbUser);
                user = new User(dbUser.getId().toString(),dbUser.getPassword(),authorities);
        }
     return user;
    }

}
