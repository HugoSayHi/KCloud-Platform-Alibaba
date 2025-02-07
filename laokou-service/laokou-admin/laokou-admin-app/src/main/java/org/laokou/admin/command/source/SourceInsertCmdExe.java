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

package org.laokou.admin.command.source;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.convertor.SourceConvertor;
import org.laokou.admin.domain.gateway.SourceGateway;
import org.laokou.admin.domain.source.Source;
import org.laokou.admin.dto.source.SourceInsertCmd;
import org.laokou.admin.gatewayimpl.database.SourceMapper;
import org.laokou.admin.gatewayimpl.database.dataobject.SourceDO;
import org.laokou.common.core.utils.RegexUtil;
import org.laokou.common.i18n.common.exception.SystemException;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class SourceInsertCmdExe {

	private final SourceGateway sourceGateway;

	private final SourceMapper sourceMapper;

	private final SourceConvertor sourceConvertor;

	public Result<Boolean> execute(SourceInsertCmd cmd) {
		Source source = sourceConvertor.toEntity(cmd.getSourceCO());
		validate(source);
		return Result.of(sourceGateway.insert(source));
	}

	private void validate(Source source) {
		String name = source.getName();
		boolean sourceRegex = RegexUtil.sourceRegex(name);
		if (!sourceRegex) {
			throw new SystemException("数据源名称必须包含字母、下划线和数字");
		}
		Long count = sourceMapper.selectCount(Wrappers.query(SourceDO.class).eq("name", name));
		if (count > 0) {
			throw new SystemException("数据源名称已存在，请重新填写");
		}
	}

}
