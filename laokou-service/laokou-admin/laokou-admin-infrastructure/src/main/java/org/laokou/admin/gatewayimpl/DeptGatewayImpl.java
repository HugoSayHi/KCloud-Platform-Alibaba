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

package org.laokou.admin.gatewayimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laokou.admin.convertor.DeptConvertor;
import org.laokou.admin.domain.dept.Dept;
import org.laokou.admin.domain.gateway.DeptGateway;
import org.laokou.admin.gatewayimpl.database.DeptMapper;
import org.laokou.admin.gatewayimpl.database.dataobject.DeptDO;
import org.laokou.common.core.utils.CollectionUtil;
import org.laokou.common.core.utils.IdGenerator;
import org.laokou.common.i18n.common.exception.SystemException;
import org.laokou.common.i18n.utils.StringUtil;
import org.laokou.common.mybatisplus.utils.TransactionalUtil;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.laokou.common.i18n.common.Constant.COMMA;
import static org.laokou.common.i18n.common.Constant.DEFAULT;

/**
 * @author laokou
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeptGatewayImpl implements DeptGateway {

	private final DeptMapper deptMapper;

	private final TransactionalUtil transactionalUtil;

	private final DeptConvertor deptConvertor;

	@Override
	public List<Dept> list(Dept dept, Long tenantId) {
		DeptDO deptDO = deptConvertor.toDataObject(dept);
		deptDO.setTenantId(tenantId);
		return deptConvertor.convertEntityList(deptMapper.getDeptList(deptDO));
	}

	@Override
	public Boolean insert(Dept dept) {
		DeptDO deptDO = deptConvertor.toDataObject(dept);
		deptDO.setId(IdGenerator.defaultSnowflakeId());
		deptDO.setPath(getPath(deptDO.getPid(), deptDO.getId()));
		return insertDept(deptDO);
	}

	@Override
	public Boolean update(Dept dept) {
		DeptDO deptDO = deptConvertor.toDataObject(dept);
		DeptDO dep = deptMapper.selectById(deptDO.getId());
		deptDO.setVersion(dep.getVersion());
		deptDO.setPath(getPath(deptDO.getPid(), deptDO.getId()));
		// 获取所有子节点
		List<DeptDO> deptChildrenList = deptMapper.selectDeptChildrenListByLikePath(dep.getPath());
		return updateDept(deptDO, dept.getPath(), deptDO.getPath(), deptChildrenList);
	}

	@Override
	public List<Long> getDeptIds(Long roleId) {
		return deptMapper.getDeptIdsByRoleId(roleId);
	}

	@Override
	public Boolean deleteById(Long id) {
		return transactionalUtil.execute(r -> {
			try {
				return deptMapper.deleteById(id) > 0;
			}
			catch (Exception e) {
				log.error("错误信息", e);
				r.setRollbackOnly();
				throw new SystemException(e.getMessage());
			}
		});
	}

	@Override
	public Dept getById(Long id) {
		return deptConvertor.convertEntity(deptMapper.selectById(id));
	}

	public Boolean updateDept(DeptDO deptDO, String oldPath, String newPath, List<DeptDO> deptChildrenList) {
		return transactionalUtil.execute(r -> {
			try {
				deptMapper.updateById(deptDO);
				updateDeptChildren(oldPath, newPath, deptChildrenList);
				return true;
			}
			catch (Exception e) {
				log.error("错误信息", e);
				r.setRollbackOnly();
				throw new SystemException(e.getMessage());
			}
		});
	}

	private Boolean insertDept(DeptDO deptDO) {
		return transactionalUtil.execute(r -> {
			try {
				return deptMapper.insertTable(deptDO);
			}
			catch (Exception e) {
				log.error("错误信息", e);
				r.setRollbackOnly();
				throw new SystemException(e.getMessage());
			}
		});
	}

	private void updateDeptChildren(String oldPath, String newPath, List<DeptDO> deptChildrenList) {
		if (CollectionUtil.isNotEmpty(deptChildrenList)) {
			deptChildrenList.forEach(deptChild -> {
				deptChild.setPath(deptChild.getPath().replace(oldPath, newPath));
				deptMapper.updateById(deptChild);
			});
		}
	}

	private String getPath(Long pid, Long id) {
		String path = deptMapper.getDeptPathByPid(pid);
		return StringUtil.isNotEmpty(path) ? path + COMMA + id : DEFAULT + COMMA + id;
	}

}
