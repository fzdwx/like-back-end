package cn.like.common.handler;


import cn.hutool.core.util.StrUtil;
import cn.like.base.support.Rest;
import cn.like.base.support.exception.BizException;
import cn.like.base.support.exception.code.ExceptionCode;
import cn.like.util.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * desc: 默认全局异常处理程序 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 20:56:02
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public abstract class DefaultGlobalExceptionHandler {
	
	@ExceptionHandler(BizException.class)
	public Rest<String> bizException(BizException ex, HttpServletRequest request) {
		log.warn("BizException:", ex);
		return Rest.of(StrPool.EMPTY,ex.getCode(), ex.getMessage()).path(request.getRequestURI());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Rest httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
		log.warn("HttpMessageNotReadableException:", ex);
		String message = ex.getMessage();
		if (StrUtil.containsAny(message, "Could not read document:")) {
			String msg = String.format(
			  "无法正确的解析json类型的参数：%s", StrUtil.subBetween(message, "Could not read document:", " at "));
			return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.PARAM_EX.getCode()), msg)
					   .path(request.getRequestURI());
		}
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.PARAM_EX.getCode()),
					   ExceptionCode.PARAM_EX.getDesc()
					  ).path(request.getRequestURI());
	}
	
	@ExceptionHandler(BindException.class)
	public Rest bindException(BindException ex, HttpServletRequest request) {
		log.warn("BindException:", ex);
		try {
			String msgs = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
			if (StrUtil.isNotEmpty(msgs)) {
				return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.PARAM_EX.getCode()), msgs)
						   .path(request.getRequestURI());
			}
		} catch (Exception ignored) {
		}
		StringBuilder msg = new StringBuilder();
		List<FieldError> fieldErrors = ex.getFieldErrors();
		fieldErrors.forEach((oe) ->
							  msg.append("参数:[").append(oe.getObjectName())
								 .append(".").append(oe.getField())
								 .append("]的传入值:[").append(oe.getRejectedValue()).append("]与预期的字段类型不匹配.")
						   );
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.PARAM_EX.getCode()), msg.toString())
				   .path(request.getRequestURI());
	}
	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Rest methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
		log.warn("MethodArgumentTypeMismatchException:", ex);
		String msg = "参数：[" + ex.getName() +
		  "]的传入值：[" + ex.getValue() +
		  "]与预期的字段类型：[" +
		  ex.getRequiredType().getName() + "]不匹配";
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.PARAM_EX.getCode()), msg)
				   .path(request.getRequestURI());
	}
	
	@ExceptionHandler(IllegalStateException.class)
	// for
	public Rest illegalStateException(IllegalStateException ex, HttpServletRequest request) {
		log.warn("IllegalStateException:", ex);
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode()),
			  ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION.getDesc()
			 )
		  .path(request.getRequestURI());
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Rest missingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
		log.warn("MissingServletRequestParameterException:", ex);
		StringBuilder msg = new StringBuilder();
		msg.append("缺少必须的[").append(ex.getParameterType()).append("]类型的参数[").append(ex.getParameterName()).append("]");
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode()), msg.toString())
		  .path(request.getRequestURI());
	}
	
	@ExceptionHandler(NullPointerException.class)
	public Rest nullPointerException(NullPointerException ex, HttpServletRequest request) {
		log.warn("NullPointerException:", ex);
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.NULL_POINT_EXCEPTION.getCode()),
			  ExceptionCode.NULL_POINT_EXCEPTION.getDesc()
			 )
		  .path(request.getRequestURI());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public Rest illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
		log.warn("IllegalArgumentException:", ex);
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode()), ex.getMessage())
		  .path(request.getRequestURI());
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Rest httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
		log.warn("HttpMediaTypeNotSupportedException:", ex);
		MediaType contentType = ex.getContentType();
		if (contentType != null) {
			StringBuilder msg = new StringBuilder();
			msg.append("请求类型(Content-Type)[").append(contentType.toString()).append("] 与实际接口的请求类型不匹配");
			return Rest
			  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.MEDIA_TYPE_EXCEPTION.getCode()), msg.toString())
			  .path(request.getRequestURI());
		}
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.MEDIA_TYPE_EXCEPTION.getCode()), "无效的Content-Type类型")
		  .path(request.getRequestURI());
	}
	
	@ExceptionHandler(MissingServletRequestPartException.class)
	public Rest missingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request) {
		log.warn("MissingServletRequestPartException:", ex);
		
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode()),
			  ExceptionCode.REQUIRED_FILE_PARAM_EX.getDesc()
			 )
		  .path(request.getRequestURI());
	}
	
	@ExceptionHandler(ServletException.class)
	public Rest servletException(ServletException ex, HttpServletRequest request) {
		log.warn("ServletException:", ex);
		String msg = "UT010016: Not a multi part request";
		if (msg.equalsIgnoreCase(ex.getMessage())) {
			return Rest
			  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode()),
				  ExceptionCode.REQUIRED_FILE_PARAM_EX.getDesc()
				 )
			  .path(request.getRequestURI());
		}
		return Rest
		  .of(StrPool.EMPTY, Long.parseLong(ExceptionCode.SYSTEM_TIMEOUT.getCode()), ex.getMessage())
		  .path(request.getRequestURI());
		
	}
	
	@ExceptionHandler(MultipartException.class)
	public Rest multipartException(MultipartException ex, HttpServletRequest request) {
		log.warn("MultipartException:", ex);
		return Rest.of(
		  StrPool.EMPTY, Long.parseLong(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode()),
		  ExceptionCode.REQUIRED_FILE_PARAM_EX.getDesc()
					  )
				   .path(request.getRequestURI());
	}
	
	/**
	 * jsr 规范中的验证异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public Rest<String> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
		log.warn("ConstraintViolationException:", ex);
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.VALID_EXCEPTION.getCode()), message)
				   .path(request.getRequestURI());
	}
	
	/**
	 * spring 封装的参数验证异常， 在conttoller中没有写result参数时，会进入
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
		log.warn("MethodArgumentNotValidException:", ex);
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.VALID_EXCEPTION.getCode()),
					   ExceptionCode.VALID_EXCEPTION.getDesc()
					  )
				   .path(request.getRequestURI());
	}
	
	/**
	 * 其他异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public Rest<String> otherExceptionHandler(Exception ex, HttpServletRequest request) {
		log.warn("Exception:", ex);
		if (ex.getCause() instanceof BizException) {
			return this.bizException((BizException) ex.getCause(), request);
		}
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.FAILED.getCode()), ExceptionCode.FAILED.getDesc())
				   .path(request.getRequestURI());
	}
	
	
	/**
	 * 返回状态码:405
	 */
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public Rest<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
		log.warn("HttpRequestMethodNotSupportedException:", ex);
		return Rest.of(StrPool.EMPTY, Long.parseLong(ExceptionCode.METHOD_NOT_ALLOWED.getCode()),
					   ExceptionCode.METHOD_NOT_ALLOWED.getDesc()
					  )
				   .path(request.getRequestURI());
	}
	
	
	@ExceptionHandler(PersistenceException.class)
	public Rest<String> persistenceException(PersistenceException ex, HttpServletRequest request) {
		log.warn("PersistenceException:", ex);
		if (ex.getCause() instanceof BizException) {
			BizException cause = (BizException) ex.getCause();
			return Rest.of(StrPool.EMPTY, cause.getCode(), cause.getMessage());
		}
		return Rest.of( StrPool.EMPTY, Long.parseLong(ExceptionCode.SQL_EXCEPTION.getCode()), ExceptionCode.SQL_EXCEPTION.getDesc())
				   .path(request.getRequestURI());
	}
	
	@ExceptionHandler(MyBatisSystemException.class)
	public Rest<String> myBatisSystemException(MyBatisSystemException ex, HttpServletRequest request) {
		log.warn("PersistenceException:", ex);
		if (ex.getCause() instanceof PersistenceException) {
			return this.persistenceException((PersistenceException) ex.getCause(), request);
		}
		return Rest.of( StrPool.EMPTY, Long.parseLong(ExceptionCode.SQL_EXCEPTION.getCode()), ExceptionCode.SQL_EXCEPTION.getDesc())
				   .path(request.getRequestURI());
	}
	
	@ExceptionHandler(SQLException.class)
	public Rest<String> sqlException(SQLException ex, HttpServletRequest request) {
		log.warn("SQLException:", ex);
		return Rest.of( StrPool.EMPTY, Long.parseLong(ExceptionCode.SQL_EXCEPTION.getCode()), ExceptionCode.SQL_EXCEPTION.getDesc())
				   .path(request.getRequestURI());
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Rest dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
		log.warn("DataIntegrityViolationException:", ex);
		return Rest.of( StrPool.EMPTY, Long.parseLong(ExceptionCode.SQL_EXCEPTION.getCode()), ExceptionCode.SQL_EXCEPTION.getDesc())
				   .path(request.getRequestURI());
	}
	
}
