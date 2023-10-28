/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
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

package org.laokou.logstash.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laokou.common.core.utils.JacksonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.laokou.common.kafka.constant.MqConstant.LAOKOU_LOGSTASH_CONSUMER_GROUP;
import static org.laokou.common.kafka.constant.MqConstant.LAOKOU_TRACE_TOPIC;

/**
 * @author laokou
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TraceConsumer {

	@KafkaListener(topics = LAOKOU_TRACE_TOPIC, groupId = LAOKOU_LOGSTASH_CONSUMER_GROUP)
	public void kafkaConsumerTest(List<String> message, Acknowledgment ack) {
		log.info("接收到消息：{}", JacksonUtil.toJsonStr(message));
		ack.acknowledge();
	}

}