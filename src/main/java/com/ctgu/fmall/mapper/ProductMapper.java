package com.ctgu.fmall.mapper;

import com.ctgu.fmall.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhen
 * @since 2020-08-05
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    @Delete("delete from product where id = #{id};")
    int delProduct(int id);

    @Select("select * from product where deleted = 1")
    List<Map> getRemoveProduct();
}
