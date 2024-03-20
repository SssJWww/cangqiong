package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "C_end dish view interface")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("search dish by id")
    public Result<List<DishVO>> list(Long categoryId) {

        //create redis key, rules:dish_{category_id}
        String key = "dish_"+categoryId;

        //search dish data whether exists in redis database
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list!=null && list.size()>0) {
            //if exists return data
            return Result.success(list);
        }

        //if not exists search mysql get data put into redis and return data
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);
        return Result.success(list);
    }
}
