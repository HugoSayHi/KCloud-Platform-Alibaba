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

package org.laokou.admin.command.resource.query;

import lombok.RequiredArgsConstructor;
import org.laokou.admin.convertor.ResourceConvertor;
import org.laokou.admin.domain.gateway.ResourceGateway;
import org.laokou.admin.dto.resource.ResourceGetQry;
import org.laokou.admin.dto.resource.clientobject.ResourceCO;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class ResourceGetQryExe {

	private final ResourceGateway resourceGateway;

	private final ResourceConvertor resourceConvertor;

	public Result<ResourceCO> execute(ResourceGetQry qry) {
		return Result.of(resourceConvertor.convertClientObject(resourceGateway.getById(qry.getId())));
	}

}
