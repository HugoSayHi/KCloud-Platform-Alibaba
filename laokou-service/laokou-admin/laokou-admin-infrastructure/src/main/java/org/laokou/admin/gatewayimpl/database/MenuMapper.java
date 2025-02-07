/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.laokou.admin.gatewayimpl.database;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.laokou.admin.gatewayimpl.database.dataobject.MenuDO;
import org.laokou.common.mybatisplus.database.BatchMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.laokou.common.mybatisplus.database.dataobject.BaseDO.TENANT_ID;

/**
 * @author laokou
 */
@Repository
@Mapper
public interface MenuMapper extends BatchMapper<MenuDO> {

	List<MenuDO> getMenuListByUserId(@Param("type") Integer type, @Param("userId") Long userId);

	List<MenuDO> getMenuListLikeName(@Param("type") Integer type, @Param("name") String name);

	List<MenuDO> getMenuListByTenantIdAndLikeName(@Param("type") Integer type, @Param(TENANT_ID) Long tenantId,
			@Param("name") String name);

	List<Long> getMenuIdsByRoleId(@Param("roleId") Long roleId);

	List<MenuDO> getTenantMenuList();

}
