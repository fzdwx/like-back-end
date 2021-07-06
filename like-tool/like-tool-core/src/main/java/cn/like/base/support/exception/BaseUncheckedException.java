package cn.like.base.support.exception;

/**
 * desc:
 * 非运行期异常基类，所有自定义非运行时异常继承该类
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 20:10:41
 * @see RuntimeException
 * @see BaseException
 */
public class BaseUncheckedException extends RuntimeException implements BaseException {

    private static final long serialVersionUID = -778887391066124051L;

    /**
     * 异常信息
     */
    protected String message;

    /**
     * 具体异常码
     */
    protected long code;

    public BaseUncheckedException(long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseUncheckedException(long code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }
	

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public long getCode() {
        return code;
    }
}
