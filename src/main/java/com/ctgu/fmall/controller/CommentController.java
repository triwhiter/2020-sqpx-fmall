package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.entity.Comment;
import com.ctgu.fmall.service.CommentService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/total")
    @ApiOperation("获取评论总数量")
    public Result getTotalComment(){
        System.out.println(ResultUtil.success(20));
        return ResultUtil.success(commentService.list(null).size());
    }

    @GetMapping("/{uid}")
    @ApiOperation("获取一个评论")
    public Result getOneComment(@PathVariable("uid") int uid){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        List<Comment> comments =commentService.list(wrapper);
        return ResultUtil.success("查找成功",comments);
    }

}

