package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.fmall.common.eums.ResultEnum;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.mapper.UserMapper;
import com.ctgu.fmall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
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
    public Result getAllUserInfo(int page, int size) {
        IPage<User> userInfoPages = this.page(new Page<>(page, size), null);
        if(userInfoPages != null){
            return ResultUtil.success(userInfoPages);
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
