package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
      void insert(Orders orders);

      @Select("SELECT * from sky_take_out.orders where status = #{status} and order_time < (#{orderTime})")
      List<Orders> getByStautsAndOrderTime(Integer status, LocalDateTime orderTime);

      void update(Orders orders);
}
