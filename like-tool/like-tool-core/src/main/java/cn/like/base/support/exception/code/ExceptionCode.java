package cn.like.base.support.exception.code;

import cn.like.base.BaseEnum;

/**
 * desc: 错误代码 <br>
 * 全局错误码 10000-15000
 * <p>
 * 预警异常编码    范围： 30000~34999
 * 标准服务异常编码 范围：35000~39999
 * 邮件服务异常编码 范围：40000~44999
 * 短信服务异常编码 范围：45000~49999
 * 权限服务异常编码 范围：50000-59999
 * 文件服务异常编码 范围：60000~64999
 * 日志服务异常编码 范围：65000~69999
 * 消息服务异常编码 范围：70000~74999
 * 开发者平台异常编码 范围：75000~79999
 * 搜索服务异常编码 范围：80000-84999
 * 共享交换异常编码 范围：85000-89999
 * 移动终端平台 异常码 范围：90000-94999
 * <p>
 * 安全保障平台    范围：        95000-99999
 * 软硬件平台 异常编码 范围：    100000-104999
 * 运维服务平台 异常编码 范围：  105000-109999
 * 统一监管平台异常 编码 范围：  110000-114999
 * 认证方面的异常编码  范围：115000-115999
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:57:09
 * @see BaseEnum
 */
public enum ExceptionCode implements BaseEnum {
	
	/** 成功 */ SUCCESS(0, "执行成功"),
	FAILED(-1, "操作失败"),
	
	TIMEOUT_ERROR(-2, "请求超时，请稍候再试"),
	SYSTEM_TIMEOUT(-3, "系统维护中~请稍后再试~"),
	SQL_EXCEPTION(-4, "运行SQL出现异常"),
	NULL_POINT_EXCEPTION(-5, "空指针异常"),
	ILLEGAL_ARGUMENT_EXCEPTION(-6, "无效参数异常"),
	MEDIA_TYPE_EXCEPTION(-7, "请求类型异常"),
	LOAD_RESOURCES_ERROR(-8, "加载资源出错"),
	VALID_EXCEPTION(-9, "统一验证参数异常"),
	OPERATION_EXCEPTION(-10, "操作异常"),
	PARAM_EX(-11, "参数类型解析异常"),
	
	OK(200, "OK"),
	
	// 400
	BAD_REQUEST(400, "错误的请求"),
	UNAUTHORIZED(401, "未经授权"),
	NOT_FOUND(404, "没有找到资源"),
	METHOD_NOT_ALLOWED(405, "不支持当前请求类型"),
	TOO_MANY_REQUESTS(429, "请求超过次数限制"),
	
	// 500
	INTERNAL_SERVER_ERROR(500, "内部服务错误"),
	BAD_GATEWAY(502, "网关错误"),
	GATEWAY_TIMEOUT(504, "网关超时"),
	
	REQUIRED_FILE_PARAM_EX(1001, "请求中必须至少包含一个有效文件"),
	;
	
	private final String code;
	private final String msg;
	
	ExceptionCode(final long code, final String msg) {
		this.code = String.valueOf(code);
		this.msg = msg;
	}
	
	public static ExceptionCode fromCode(long code) {
		ExceptionCode[] ecs = ExceptionCode.values();
		for (ExceptionCode ec : ecs) {
			if (ec.getCode().equals(String.valueOf(code))) {
				return ec;
			}
		}
		return SUCCESS;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getDesc() {
		return msg;
	}
	
	@Override
	public String toString() {
		return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
	}
}
