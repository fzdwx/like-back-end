package cn.like.util;

/**
 * desc:
 * 字符串常量池
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 19:27:36
 * @see cn.hutool.core.text.StrPool
 */
public interface StrPool extends cn.hutool.core.text.StrPool {
	
	String EMPTY = "";
	
	String TEST = "test";
	String PROD = "prod";
	
	/**
	 * 默认的根节点path
	 */
	String DEF_ROOT_PATH = ",";
	/**
	 * 默认的父id
	 */
	Long DEF_PARENT_ID = 0L;
}
