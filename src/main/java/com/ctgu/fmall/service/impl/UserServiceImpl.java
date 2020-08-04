package com.ctgu.fmall.service.impl;

import com.ctgu.fmall.common.ResultEnum;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.mapper.UserMapper;
import com.ctgu.fmall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public Result editUser(User user) {
        boolean IsUpdate = this.updateById(user);
        if(IsUpdate != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result getAllUserInfo() {
        List<User> list = this.list(null);
        if(list != null){
            return ResultUtil.success(list);
        }else {
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

    @Override
    public Result delUserById(int uid) {
        boolean IsRemove = this.removeById(uid);
        if(IsRemove != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }

    }

    @Override
    public Result addUser(User user) {
        boolean IsSave = this.save(user);
        if (IsSave != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else {
            return ResultUtil.success(ResultEnum.SUCCESS);
        }

    }
}
