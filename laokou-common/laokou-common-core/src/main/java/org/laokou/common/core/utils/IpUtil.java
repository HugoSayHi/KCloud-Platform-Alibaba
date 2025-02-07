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
package org.laokou.common.core.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.laokou.common.i18n.common.Constant;
import org.laokou.common.i18n.utils.StringUtil;

import java.util.Objects;

import static org.laokou.common.i18n.common.Constant.*;

/**
 * IP工具类
 *
 * @author laokou
 */
@Slf4j
public class IpUtil {

	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return IP_UNKNOWN;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (conditionNull(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (conditionNull(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (conditionNull(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (conditionNull(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (conditionNull(ip)) {
			ip = request.getRemoteAddr();
		}
		return LOCAL_NETWORK_SEGMENT.equals(ip) ? LOCAL_IP : ip.split(Constant.COMMA)[0];
	}

	public static boolean localIp(String ip) {
		return LOCAL_NETWORK_SEGMENT.equals(ip) || LOCAL_IP.equals(ip);
	}

	public static boolean internalIp(String ip) {
		byte[] bytes = textToNumericFormatV4(ip);
		return Objects.nonNull(bytes) && (internalIp(bytes) || LOCAL_IP.equals(ip));
	}

	private static boolean conditionNull(String ip) {
		return StringUtil.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip);
	}

	private static boolean internalIp(byte[] addr) {
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		// 10.x.x.x/8
		final byte section1 = 0x0A;
		// 172.16.x.x/12
		final byte section2 = (byte) 0xAC;
		final byte section3 = (byte) 0x10;
		final byte section4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte section5 = (byte) 0xC0;
		final byte section6 = (byte) 0xA8;
        return switch (b0) {
            case section1 -> true;
            case section2 -> b1 >= section3 && b1 <= section4;
            case section5 -> b1 == section6;
            default -> false;
        };
	}

	/**
	 * 将IPv4地址转换成字节
	 * @param text IPv4地址
	 * @return byte 字节
	 */
	public static byte[] textToNumericFormatV4(String text) {
		if (text.isEmpty()) {
			return null;
		}

		byte[] bytes = new byte[4];
		String[] elements = text.split("\\.", -1);
		try {
			long l, j;
			switch (elements.length) {
				case 1:
					l = Long.parseLong(elements[0]);
					j = 4294967295L;
					if ((l < 0L) || (l > j)) {
						return null;
					}
					bytes[0] = (byte) (int) (l >> 24 & 0xFF);
					bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
					bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
					bytes[3] = (byte) (int) (l & 0xFF);
					break;
				case 2:
					l = Integer.parseInt(elements[0]);
					j = 255;
					if (l < 0L || l > j) {
						return null;
					}
					bytes[0] = (byte) (int) (l & 0xFF);
					l = Integer.parseInt(elements[1]);
					j = 16777215;
					if (l < 0L || l > j) {
						return null;
					}
					bytes[1] = (byte) (int) (l >> 16 & 0xFF);
					bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
					bytes[3] = (byte) (int) (l & 0xFF);
					break;
				case 3:
					j = 2;
					for (int i = 0; i < j; i++) {
						l = Integer.parseInt(elements[i]);
						if ((l < 0L) || (l > 255L)) {
							return null;
						}
						bytes[i] = (byte) (int) (l & 0xFF);
					}
					l = Integer.parseInt(elements[2]);
					j = 65535L;
					if ((l < 0L) || (l > j)) {
						return null;
					}
					bytes[2] = (byte) (int) (l >> 8 & 0xFF);
					bytes[3] = (byte) (int) (l & 0xFF);
					break;
				case 4:
					j = 4;
					for (int i = 0; i < j; i++) {
						l = Integer.parseInt(elements[i]);
						if ((l < 0L) || (l > 255L)) {
							return null;
						}
						bytes[i] = (byte) (int) (l & 0xFF);
					}
					break;
				default:
					return null;
			}
		}
		catch (NumberFormatException e) {
			log.error("格式化失败，错误信息", e);
			return null;
		}
		return bytes;
	}

}
