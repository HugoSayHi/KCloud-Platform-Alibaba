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

package org.laokou.common.nacos.utils;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class ConfigUtil {

	public static final String ROUTER_DATA_ID = "router.json";

	private final NacosConfigManager nacosConfigManager;

	private final NacosConfigProperties nacosConfigProperties;

	public String getGroup() {
		return nacosConfigProperties.getGroup();
	}

	public ConfigService getConfigService() {
		return nacosConfigManager.getConfigService();
	}

	public String getNameSpace() {
		return nacosConfigProperties.getNamespace();
	}

}
