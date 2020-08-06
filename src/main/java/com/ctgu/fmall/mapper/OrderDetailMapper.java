package com.ctgu.fmall.mapper;

import com.ctgu.fmall.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Delete("delete from order_detail where oid = #{id}")
    void deleteByOid(int id);
}
