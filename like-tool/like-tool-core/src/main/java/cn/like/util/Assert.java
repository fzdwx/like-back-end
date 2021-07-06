/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.like.util;


import cn.like.base.support.exception.BizException;
import cn.like.base.support.exception.code.ExceptionCode;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * desc: 业务断言
 * <p>参考：org.junit.Assert</p>
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:07:36
 */
public class Assert {
	
	protected Assert() {
		// to do noting
	}
	
	/**
	 * 大于O
	 */
	public static void gtZero(Integer num, ExceptionCode errorCode) {
		if (num == null || num <= 0) {
			fail(errorCode);
		}
	}
	
	/**
	 * 大于等于O
	 */
	public static void geZero(Integer num, ExceptionCode errorCode) {
		if (num == null || num < 0) {
			fail(errorCode);
		}
	}
	
	/**
	 * num1大于num2
	 */
	public static void gt(Integer num1, Integer num2, ExceptionCode errorCode) {
		if (num1 <= num2) {
			fail(errorCode);
		}
	}
	
	/**
	 * num1大于等于num2
	 */
	public static void ge(Integer num1, Integer num2, ExceptionCode errorCode) {
		if (num1 < num2) {
			fail(errorCode);
		}
	}
	
	/**
	 * obj1 eq obj2
	 */
	public static void eq(Object obj1, Object obj2, ExceptionCode errorCode) {
		if (!obj1.equals(obj2)) {
			fail(errorCode);
		}
	}
	
	public static void isTrue(boolean condition, ExceptionCode errorCode) {
		if (!condition) {
			fail(errorCode);
		}
	}
	
	public static void isFalse(boolean condition, ExceptionCode errorCode) {
		if (condition) {
			fail(errorCode);
		}
	}
	
	public static void isNull(ExceptionCode errorCode, Object... conditions) {
		if (!Objects.isNull(conditions)) {
			fail(errorCode);
		}
	}
	
	public static void notNull(ExceptionCode errorCode, Object... conditions) {
		if (Objects.isNull(conditions)) {
			fail(errorCode);
		}
	}
	
	/**
	 * 失败结果
	 *
	 * @param errorCode 异常错误码
	 */
	public static void fail(ExceptionCode errorCode) {
		throw BizException.wrap(errorCode);
	}
	
	public static void fail(boolean condition, ExceptionCode errorCode) {
		if (condition) {
			fail(errorCode);
		}
	}
	
	public static void fail(String message) {
		throw new BizException(message);
	}
	
	public static void fail(boolean condition, String message) {
		if (condition) {
			fail(message);
		}
	}
	
	
	/**
	 * 返回多语言异常消息
	 *
	 * @param message       多语言消息 KEY
	 * @param args          多语言提示默认参数数组对象
	 * @param messageSource 多语言资源对象
	 */
	public static void fail(String message, Object[] args, MessageSource messageSource) {
		throw new BizException(messageSource.getMessage(message, args, LocaleContextHolder.getLocale()));
	}
	
	public static void fail(boolean condition, String message, Object[] args, MessageSource messageSource) {
		if (condition) {
			fail(message, args, messageSource);
		}
	}
	
	public static void fail(String message, MessageSource messageSource) {
		throw new BizException(messageSource.getMessage(message, null, LocaleContextHolder.getLocale()));
	}
	
	public static void fail(boolean condition, String message, MessageSource messageSource) {
		if (condition) {
			fail(message, messageSource);
		}
	}
	
	public static void notEmpty(Object[] array, ExceptionCode errorCode) {
		if (ObjectUtils.isEmpty(array)) {
			fail(errorCode);
		}
	}
	
	public static void noNullElements(Object[] array, ExceptionCode errorCode) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					fail(errorCode);
				}
			}
		}
	}
	
	public static void notEmpty(Collection<?> collection, ExceptionCode errorCode) {
		if (CollectionUtils.isEmpty(collection)) {
			fail(errorCode);
		}
	}
	
	public static void notEmpty(Map<?, ?> map, ExceptionCode errorCode) {
		if (ObjectUtils.isEmpty(map)) {
			fail(errorCode);
		}
	}
	
	public static void isInstanceOf(Class<?> type, Object obj, ExceptionCode errorCode) {
		notNull(errorCode, type);
		if (!type.isInstance(obj)) {
			fail(errorCode);
		}
	}
	
	public static void isAssignable(Class<?> superType, Class<?> subType, ExceptionCode errorCode) {
		notNull(errorCode, superType);
		if (subType == null || !superType.isAssignableFrom(subType)) {
			fail(errorCode);
		}
	}
}
