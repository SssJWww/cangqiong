package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * add employee
     * @param employeeDTO
     */
    @ApiOperation("add employee")
    @PostMapping
    public Result add(@RequestBody  EmployeeDTO employeeDTO) {
        log.info("add employee:{}",employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    /**
     * query employees through pages
     * @param employeePageQueryDTO
     * @return
     */
    @ApiOperation("query employees through page")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("employees show in pages", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("able or disable employee account")
    @PostMapping("/status/{status}")
    public Result ableOrDisable(@PathVariable("status")Integer status, Long id) {
        log.info("启用禁用员工账号：{},{}", status, id);
        employeeService.ableOrDisable(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("get employee by id")
    public Result<Employee> getEmpById(@PathVariable("id")Long id) {
        log.info("查询id为员工信息:{}", id);
        Employee employee = employeeService.getEmpById(id);
        return Result.success(employee);
    }

    /**
     * edit employee information
     * @param employeeDTO
     * @return
     */
    @PutMapping()
    @ApiOperation("edit employee information")
    public Result updateEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("edit employee information");
        employeeService.updateEmp(employeeDTO);
        return Result.success();
    }

}
