package cn.like.base.context;

/**
 * desc:
 * 常量工具类
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 20:01:14
 */
public class BaseContextConstants {
	/**
	 * 用户token
	 */
	public static final String TOKEN_NAME = "token";
	/**
	 * 账号id
	 */
	public static final String JWT_KEY_USER_ID = "userId";
	/**
	 * 登录的账号
	 */
	public static final String JWT_KEY_NAME = "name";
	/**
	 *账号表中的name
	 */
	public static final String JWT_KEY_ACCOUNT = "account";
	
	/**
	 * 组织id
	 */
	public static final String JWT_KEY_ORG_ID = "orgId";
	/**
	 * 岗位id
	 */
	public static final String JWT_KEY_STATION_ID = "stationId";
	
	/**
	 * 动态数据库名前缀。  每个项目配置死的
	 */
	public static final String DATABASE_NAME = "database_name";
}
