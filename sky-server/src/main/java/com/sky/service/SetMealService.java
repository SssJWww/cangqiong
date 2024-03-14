package com.sky.service;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;

import java.util.List;

public interface SetMealService {
    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);

    Setmeal getById(Long id);
}
