package cn.like.base.support.exception;


import cn.like.base.support.exception.code.ExceptionCode;

/**
 * desc:
 * 业务异常
 * 用于在处理业务逻辑时，进行抛出的异常。
 * <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 20:11:05
 * @see BaseUncheckedException
 */
public class BizException extends BaseUncheckedException {

    private static final long serialVersionUID = -3843907364558373817L;

    public BizException(String message) {
        super(-1, message);
    }

    public BizException(long code, String message) {
        super(code, message);
    }

    public BizException(long code, String message, Object... args) {
        super(code, message, args);
    }

    /**
     * 实例化异常
     *
     * @param code    自定义异常编码
     * @param message 自定义异常消息
     * @param args    已定义异常参数
     * @return
     */
    public static BizException wrap(long code, String message, Object... args) {
        return new BizException(code, message, args);
    }

    public static BizException wrap(String message, Object... args) {
        return new BizException(Long.parseLong(ExceptionCode.FAILED.getCode()), message, args);
    }

    public static BizException validFail(String message, Object... args) {
        return new BizException(Long.parseLong(ExceptionCode.VALID_EXCEPTION.getCode()), message, args);
    }

    public static BizException wrap(ExceptionCode ex) {
        return new BizException(Long.parseLong(ex.getCode()), ex.getDesc());
    }

    @Override
    public String toString() {
        return "BizException [message=" + message + ", code=" + code + "]";
    }
    
    public static void main(String[] args) {
        System.out.println(BizException.validFail("验证错误", "age", "name").getMessage());
    }
}
