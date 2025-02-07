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
package org.laokou.admin.gatewayimpl.database.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.laokou.common.mybatisplus.database.dataobject.BaseDO;

import java.io.Serial;

/**
 * @author laokou
 */
@Data
@TableName("boot_sys_message")
@Schema(name = "MessageDO", description = "消息")
public class MessageDO extends BaseDO {

	@Serial
	private static final long serialVersionUID = -8162940421790076067L;

	@Schema(name = "title", description = "标题")
	private String title;

	@Schema(name = "content", description = "内容")
	private String content;

	@Schema(name = "type", description = "类型 0通知 1提醒")
	private Integer type;

}
