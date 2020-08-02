package com.ctgu.fmall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.MyUserDetails;

import com.ctgu.fmall.mapper.AdminMapper;
import com.ctgu.fmall.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
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
        QueryWrapper<com.ctgu.fmall.entity.User> wrapper = new QueryWrapper<>();
        wrapper.eq("nick_name",username);
        com.ctgu.fmall.entity.User dbUser=userMapper.selectOne(wrapper);
        if(dbUser == null){
            return null;
        }else{
            Collection<GrantedAuthority> authorities = new ArrayList<>();         
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            authorities.add(new JaasGrantedAuthority("ROLE_ADMIN"));
//            MyUserDetails user = new MyUserDetails(dbUser,dbUser.getEmail(),dbUser.getPassword(),authorities);
            User user = new User(dbUser.getId().toString(),dbUser.getPassword(),authorities);
            log.warn("当前认证用户："+user.toString());
//            MyUserDetails user = new User(dbUser.getEmail(),passwordEncoder.encode(dbUser.getPassword()),authorities,db);
//            System.out.println("管理员信息："+user.getUser().getUsername()+"   "+passwordEncoder.encode(dbUser.getPassword())+"  "+user.getAuthorities());
            return user;
        }
    }
}