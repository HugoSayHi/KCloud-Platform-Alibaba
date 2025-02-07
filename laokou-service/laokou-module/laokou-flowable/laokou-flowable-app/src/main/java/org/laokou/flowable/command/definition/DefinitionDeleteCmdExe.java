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

package org.laokou.flowable.command.definition;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.laokou.common.i18n.common.exception.SystemException;
import org.laokou.common.i18n.dto.Result;
import org.laokou.common.mybatisplus.utils.TransactionalUtil;
import org.laokou.flowable.dto.definition.DefinitionDeleteCmd;
import org.springframework.stereotype.Component;

import static org.laokou.flowable.common.Constant.FLOWABLE;

/**
 * @author laokou
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefinitionDeleteCmdExe {

	private final RepositoryService repositoryService;

	private final TransactionalUtil transactionalUtil;

	public Result<Boolean> execute(DefinitionDeleteCmd cmd) {
		try {
			DynamicDataSourceContextHolder.push(FLOWABLE);
			return transactionalUtil.execute(r -> {
				try {
					// true允许级联删除 不设置会导致数据库关联异常
					repositoryService.deleteDeployment(cmd.getDeploymentId(), true);
					return Result.of(true);
				}
				catch (Exception e) {
					log.error("错误信息：{}", e.getMessage());
					r.setRollbackOnly();
					throw new SystemException(e.getMessage());
				}
			});
		}
		finally {
			DynamicDataSourceContextHolder.clear();
		}
	}

}
