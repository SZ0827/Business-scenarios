package com.sz.mapper;

import com.sz.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO order(user_id, product_id, amount, status) VALUES (#{userId}, #{productId}, #{amount}, 'PENDING')")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int createOrder(Order order);
    @Insert("UPDATE order SET status = #{status} WHERE id = #{id}")
    int updateOrderStatus(@Param("id") Long id, @Param("status") String status);
    @Select("SELECT * FROM order WHERE id = #{id}")
    Order findById(Long id);
}
