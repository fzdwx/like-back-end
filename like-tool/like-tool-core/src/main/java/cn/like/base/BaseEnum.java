package cn.like.base;

import cn.like.util.MapHelper;

import java.util.Arrays;
import java.util.Map;

/**
 * desc: 枚举类型基类
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:56:30
 */
public interface BaseEnum {
	
	/**
	 * 将制定的枚举集合转成 map
	 * key -> code
	 * value -> desc
	 *
	 * @param list
	 * @return
	 */
	static Map<String, String> toMap(BaseEnum[] list) {
		return MapHelper.uniqueIndex(Arrays.asList(list), BaseEnum::getCode, BaseEnum::getDesc);
	}
	
	/**
	 * desc: 编码 <br>
	 *
	 * @return: long
	 * @author: like 980650920@qq.com
	 * @date 2021-07-06 19:22:35
	 */
	String getCode();
	
	/**
	 * desc: 描述 <br>
	 * details:
	 *
	 * @return: {@link String }
	 * @author: like 980650920@qq.com
	 * @date 2021-07-06 19:22:39
	 */
	String getDesc();
}
