package com.ctgu.fmall.service.impl;

import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.mapper.UserMapper;
import com.ctgu.fmall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getUserInfoById(int id) {
        User user = userMapper.selectById(id);
        if(user != null){
            return new Result(200,"获取数据成功",user);
        }else{
            return new Result(400,"获取数据失败",null);
        }
    }
}
