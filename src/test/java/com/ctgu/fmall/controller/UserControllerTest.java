package com.ctgu.fmall.controller;

import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.UserService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 22:58
 * @PackageName:com.ctgu.fmall.controller
 * @Description: TODO
 * @Version:V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest extends TestCase {
    /**
     * 批量更新用户头像
     */
    @Autowired
    UserService userService;
    @Test
    public void updateUserAvatar(){
        List<User> users=userService.list(null);
        users.forEach(u->{
            u.setAvatar("https://api.uomg.com/api/rand.avatar?sort="+u.getSex()+"&uid="+u.getId().toString());
            userService.updateById(u);
        });
    }
}