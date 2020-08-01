package com.ctgu.fmall.service.impl;

import com.ctgu.fmall.entity.Comment;
import com.ctgu.fmall.mapper.CommentMapper;
import com.ctgu.fmall.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
