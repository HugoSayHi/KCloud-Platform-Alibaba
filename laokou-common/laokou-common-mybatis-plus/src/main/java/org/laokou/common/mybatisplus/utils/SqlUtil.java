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

package org.laokou.common.mybatisplus.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.laokou.common.i18n.common.exception.DataSourceException;

/**
 * @author laokou
 */
@Slf4j
public class SqlUtil {

	@SneakyThrows
	public static Statement parseSql(String sql) {
		try {
			return CCJSqlParserUtil.parse(sql);
		}
		catch (Exception e) {
			log.error("SQL解析失败");
			throw new DataSourceException("SQL解析失败");
		}
	}

	public static String formatSql(String sql) {
		return parseSql(sql).toString();
	}

	public static PlainSelect plainSelect(String sql) {
		return (PlainSelect) ((Select) parseSql(sql)).getSelectBody();
	}

}
