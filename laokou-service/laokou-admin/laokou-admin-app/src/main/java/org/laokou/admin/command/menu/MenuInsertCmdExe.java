/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
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
 *
 */

package org.laokou.admin.command.menu;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.convertor.MenuConvertor;
import org.laokou.admin.domain.gateway.MenuGateway;
import org.laokou.admin.dto.menu.MenuInsertCmd;
import org.laokou.admin.dto.menu.clientobject.MenuCO;
import org.laokou.admin.gatewayimpl.database.MenuMapper;
import org.laokou.admin.gatewayimpl.database.dataobject.MenuDO;
import org.laokou.common.i18n.common.exception.SystemException;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class MenuInsertCmdExe {

	private final MenuGateway menuGateway;

	private final MenuConvertor menuConvertor;

	private final MenuMapper menuMapper;

	public Result<Boolean> execute(MenuInsertCmd cmd) {
		MenuCO co = cmd.getMenuCO();
		Long count = menuMapper.selectCount(Wrappers.lambdaQuery(MenuDO.class).eq(MenuDO::getName, co.getName()));
		if (count > 0) {
			throw new SystemException("菜单已存在，请重新填写");
		}
		return Result.of(menuGateway.insert(menuConvertor.toEntity(co)));
	}

}
