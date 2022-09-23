/**
 * Copyright (c) 2022 KCloud-Platform Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.laokou.admin.interfaces.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.laokou.admin.interfaces.dto.SysUserDTO;
import org.laokou.admin.interfaces.qo.SysUserQO;
import org.laokou.admin.interfaces.vo.OptionVO;
import org.laokou.admin.interfaces.vo.SysUserVO;
import org.laokou.admin.application.service.SysUserApplicationService;
import org.laokou.common.utils.HttpResultUtil;
import org.laokou.log.annotation.OperateLog;
import org.laokou.security.annotation.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 系统用户控制器
 * @author Kou Shenhai
 */
@RestController
@Api(value = "系统用户API",protocols = "http",tags = "系统用户API")
@RequestMapping("/sys/user/api")
public class SysUserApiController {

    @Autowired
    private SysUserApplicationService sysUserApplicationService;

    @PutMapping("/update")
    @ApiOperation("系统用户>修改")
    @OperateLog(module = "系统用户",name = "用户修改")
    @PreAuthorize("sys:user:update")
    public HttpResultUtil<Boolean> update(@RequestBody SysUserDTO dto, HttpServletRequest request) {
        return new HttpResultUtil<Boolean>().ok(sysUserApplicationService.updateUser(dto,request));
    }

    @GetMapping("/option/list")
    @ApiOperation("系统用户>下拉框列表")
    public HttpResultUtil<List<OptionVO>> optionList() {
        return new HttpResultUtil<List<OptionVO>>().ok(sysUserApplicationService.getOptionList());
    }

    @PutMapping("/updateInfo")
    @ApiOperation("系统用户>修改个人信息")
    public HttpResultUtil<Boolean> updateInfo(@RequestBody SysUserDTO dto, HttpServletRequest request) {
        return new HttpResultUtil<Boolean>().ok(sysUserApplicationService.updateUser(dto,request));
    }

    @PutMapping("/password")
    @ApiOperation("系统用户>重置")
    @PreAuthorize("sys:user:password")
    @OperateLog(module = "系统用户",name = "重置密码")
    public HttpResultUtil<Boolean> password(@RequestBody SysUserDTO dto, HttpServletRequest request) {
        return new HttpResultUtil<Boolean>().ok(sysUserApplicationService.updateUser(dto,request));
    }

    @PostMapping("/insert")
    @ApiOperation("系统用户>新增")
    @PreAuthorize("sys:user:insert")
    @OperateLog(module = "系统用户",name = "用户新增")
    public HttpResultUtil<Boolean> insert(@RequestBody SysUserDTO dto, HttpServletRequest request) {
        return new HttpResultUtil<Boolean>().ok(sysUserApplicationService.insertUser(dto,request));
    }

    @GetMapping("/detail")
    @ApiOperation("系统用户>详情")
    public HttpResultUtil<SysUserVO> detail(@RequestParam("id") Long id) {
        return new HttpResultUtil<SysUserVO>().ok(sysUserApplicationService.getUserById(id));
    }

    @DeleteMapping("/delete")
    @ApiOperation("系统用户>删除")
    @PreAuthorize("sys:user:delete")
    @OperateLog(module = "系统用户",name = "用户删除")
    public HttpResultUtil<Boolean> delete(@RequestParam("id") Long id,HttpServletRequest request) {
        return new HttpResultUtil<Boolean>().ok(sysUserApplicationService.deleteUser(id,request));
    }

    @PostMapping("/query")
    @ApiOperation("系统用户>查询")
    @PreAuthorize("sys:user:query")
    public HttpResultUtil<IPage<SysUserVO>> query(@RequestBody SysUserQO qo) {
        return new HttpResultUtil<IPage<SysUserVO>>().ok(sysUserApplicationService.queryUserPage(qo));
    }

}