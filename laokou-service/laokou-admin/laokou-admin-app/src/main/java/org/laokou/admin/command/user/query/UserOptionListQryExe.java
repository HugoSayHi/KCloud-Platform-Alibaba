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

package org.laokou.admin.command.user.query;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.domain.annotation.DataFilter;
import org.laokou.admin.dto.common.clientobject.OptionCO;
import org.laokou.admin.dto.user.UserOptionListQry;
import org.laokou.admin.gatewayimpl.database.UserMapper;
import org.laokou.admin.gatewayimpl.database.dataobject.UserDO;
import org.laokou.common.core.utils.CollectionUtil;
import org.laokou.common.i18n.dto.PageQuery;
import org.laokou.common.i18n.dto.Result;
import org.laokou.common.i18n.utils.DateUtil;
import org.laokou.common.mybatisplus.template.TableTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.laokou.common.mybatisplus.constant.DsConstant.BOOT_SYS_USER;
import static org.laokou.common.mybatisplus.constant.DsConstant.USER;
import static org.laokou.common.mybatisplus.template.TableTemplate.MIN_TIME;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class UserOptionListQryExe {

	private final UserMapper userMapper;

	@DataFilter(alias = BOOT_SYS_USER)
	public Result<List<OptionCO>> execute(UserOptionListQry qry) {
		try {
			DynamicDataSourceContextHolder.push(USER);
			PageQuery pageQuery = qry.ignore();
			List<String> dynamicTables = TableTemplate.getDynamicTables(MIN_TIME,
					DateUtil.format(DateUtil.now(), DateUtil.YYYY_BAR_MM_BAR_DD_EMPTY_HH_RISK_HH_RISK_SS),
					BOOT_SYS_USER);
			List<UserDO> list = userMapper.getOptionListByTenantId(dynamicTables, pageQuery);
			if (CollectionUtil.isEmpty(list)) {
				return Result.of(new ArrayList<>(0));
			}
			List<OptionCO> options = new ArrayList<>(list.size());
			for (UserDO userDO : list) {
				OptionCO oc = new OptionCO();
				oc.setLabel(userDO.getUsername());
				oc.setValue(userDO.getId().toString());
				options.add(oc);
			}
			return Result.of(options);
		}
		finally {
			DynamicDataSourceContextHolder.clear();
		}
	}

}
