/**
 * Copyright (c) 2022 KCloud-Platform Authors. All Rights Reserved.
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
 */
package io.laokou.admin.application.service.impl;

import io.laokou.admin.application.service.SysSearchApplicationService;
import io.laokou.admin.infrastructure.common.feign.elasticsearch.ElasticsearchApiFeignClient;
import io.laokou.admin.infrastructure.common.feign.elasticsearch.form.SearchForm;
import io.laokou.admin.infrastructure.common.feign.elasticsearch.form.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
public class SysSearchApplicationServiceImpl implements SysSearchApplicationService {

    @Autowired
    private ElasticsearchApiFeignClient elasticsearchApiFeignClient;

    @Override
    public SearchVO<Map<String,Object>> searchResource(SearchForm form) {
        return elasticsearchApiFeignClient.highlightSearch(form).getData();
    }
}