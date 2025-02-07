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

package org.laokou.admin.command.tenant;

import lombok.RequiredArgsConstructor;
import org.laokou.admin.convertor.TenantConvertor;
import org.laokou.admin.domain.gateway.TenantGateway;
import org.laokou.admin.domain.tenant.Tenant;
import org.laokou.admin.dto.tenant.TenantUpdateCmd;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class TenantUpdateCmdExe {

	private final TenantGateway tenantGateway;

	private final TenantConvertor tenantConvertor;

	public Result<Boolean> execute(TenantUpdateCmd cmd) {
		Tenant tenant = tenantConvertor.toEntity(cmd.getTenantCO());
		return Result.of(tenantGateway.update(tenant));
	}

}
