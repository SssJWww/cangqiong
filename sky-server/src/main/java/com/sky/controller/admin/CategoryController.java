package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "category relevant interface")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * add category
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("add category")
    public Result<String> add(@RequestBody CategoryDTO categoryDTO) {
        log.info("add category: {}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /**
     * category query by pages
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("category pages")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("pages query: {}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * delete category
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete category")
    public Result deleteById(Long id) {
        log.info("delete category: {}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * update category
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("update category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * able or disable category status
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("able or disable status")
    public Result ableOrDisable(@PathVariable("status") Integer status, Long id) {
        log.info("update id:{} category status:{}", id, status);
        categoryService.update(status, id);
        return Result.success();
    }

    /**
     * get category by type
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("get category by type")
    public Result<List<Category>> list(Integer type) {
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
