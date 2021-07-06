package cn.like.base.support;


import cn.hutool.json.JSONUtil;

import cn.like.base.support.exception.BizException;
import cn.like.base.support.exception.code.ExceptionCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

import static cn.like.base.support.exception.code.ExceptionCode.TIMEOUT_ERROR;
import static cn.like.base.support.exception.code.ExceptionCode.VALID_EXCEPTION;


/**
 * desc: Rest Api返回结果 <br>
 * details:
 * <pre>
 *     {@link Rest#of(Object)} 默认成功
 * </pre>
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:28:47
 */
@Getter
@ApiResponse
@Api(tags = "Rest Api返回结果")
public class Rest<T> implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 业务状态码 */
	@ApiModelProperty(name = "业务状态码,0/200-请求处理成功")
	private Long code;
	/** 结果集 */
	@ApiModelProperty(name = "结果集")
	private T data;
	/** 描述 */
	@ApiModelProperty(name = "提示消息")
	private String msg;
	
	@ApiModelProperty(value = "请求路径")
	private String path;
	
	public Rest() {
		// to do nothing
	}
	
	public Rest(ExceptionCode errorCode) {
		errorCode = Optional.ofNullable(errorCode).orElse(ExceptionCode.FAILED);
		this.code = Long.valueOf(errorCode.getCode());
		this.msg = errorCode.getDesc();
	}
	
	// ========================== success
	
	public static <T> Rest<T> ok(T data) {
		ExceptionCode aec = ExceptionCode.SUCCESS;
		if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
			aec = ExceptionCode.FAILED;
		}
		return restResult(data, aec);
	}
	
	// ========================== failed
	
	public static <T> Rest<T> failed(String msg) {
		return restResult(null, Long.parseLong(ExceptionCode.FAILED.getCode()), msg);
	}
	
	public static <T> Rest<T> failed(ExceptionCode errorCode) {
		return restResult(null, errorCode);
	}
	
	// ========================== validFail
	
	public static <T> Rest<T> validFail(String msg) {
		return restResult(
		  null, Long.parseLong(VALID_EXCEPTION.getCode()),
		  (msg == null || msg.isEmpty()) ? VALID_EXCEPTION.getDesc() : msg
		);
	}
	
	public static <T> Rest<T> validFail(String msg, Object... args) {
		String message = (msg == null || msg.isEmpty()) ? VALID_EXCEPTION.getDesc() : msg;
		return restResult(null, Long.parseLong(VALID_EXCEPTION.getCode()), String.format(message, args));
	}
	
	public static <T> Rest<T> validFail(ExceptionCode exceptionCode) {
		return restResult(null, Long.parseLong(VALID_EXCEPTION.getCode()),
						  (exceptionCode.getDesc() == null || exceptionCode.getDesc()
							.isEmpty()) ? VALID_EXCEPTION.getDesc() : exceptionCode.getDesc()
		);
	}
	
	public static <T> Rest<T> timeout() {
		return failed(TIMEOUT_ERROR);
	}
	
	// ========================== restResult
	
	public static <T> Rest<T> restResult(T data, ExceptionCode errorCode) {
		return restResult(data, Long.parseLong(errorCode.getCode()), errorCode.getDesc());
	}
	
	private static <T> Rest<T> restResult(T data, long code, String msg) {
		Rest<T> apiResult = new Rest<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		
		return apiResult;
	}
	
	// ========================== of
	
	public static <T> Rest<T> of(T data) {
		return ok(data);
	}
	
	public static <T> Rest<T> of(T data, long code) {
		final Rest<T> of = of(data);
		of.setCode(code);
		
		return of;
	}
	
	public static <T> Rest<T> of(T data, String msg) {
		final Rest<T> of = of(data);
		of.setMsg(msg);
		
		return of;
	}
	
	public static <T> Rest<T> of(T data, long code, String msg) {
		return restResult(data, code, msg);
	}
	
	public static <T> Rest<T> of(T data, ExceptionCode errorCode) {
		return restResult(data, errorCode);
	}
	
	public Rest<T> errorCode(ExceptionCode errorCode) {
		this.code = Long.valueOf(errorCode.getCode());
		this.msg = errorCode.getDesc();
		
		return this;
	}
	
	public Rest<T> errorCode(long code, String msg) {
		this.code = code;
		this.msg = msg;
		
		return this;
	}
	
	public Rest<T> msg(String msg) {
		this.msg = msg;
		
		return this;
	}
	
	public Rest<T> code(long code) {
		this.code = code;
		
		return this;
	}
	
	public Rest<T> path(String path) {
		this.path = path;
		
		return this;
	}
	
	/**
	 * desc: 判断当前请求是否是成功的 <br>
	 *
	 * @return: boolean
	 * @author: like 980650920@qq.com
	 * @date 2021-06-29 21:42:14
	 */
	public boolean ok() {
		return Long.parseLong(ExceptionCode.SUCCESS.getCode()) == code || this.code == 200;
	}
	
	/**
	 * 服务间调用非业务正常，异常直接释放
	 */
	public T serviceData() {
		if (!ok()) {
			throw new BizException(this.msg);
		}
		return data;
	}
	
	// ========================== set方法 私有
	
	private void setCode(long code) {
		this.code = code;
	}
	
	private void setData(T data) {
		this.data = data;
	}
	
	private void setMsg(String msg) {
		this.msg = msg;
	}
	
	private void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}
}

