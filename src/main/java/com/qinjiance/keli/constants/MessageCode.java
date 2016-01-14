package com.qinjiance.keli.constants;

/**
 * @author "Jiance Qin"
 * 
 * @date 2014年7月5日
 * 
 * @time 上午11:18:19
 * 
 * @desc ARC服务接口json返回code值
 * 
 */
public enum MessageCode {

	/**
	 * 小于10000，处理成功的不同提示消息，不同接口之间可复用, 消息需要自定义
	 * */
	SUCC_0(0, ""), SUCC_1(1, ""), SUCC_2(2, ""), SUCC_3(3, ""), SUCC_4(4, ""), SUCC_5(5, ""), SUCC_6(6, ""), SUCC_7(7,
			""), SUCC_8(8, ""), SUCC_9(9, ""), SUCC_10(10, ""),

	/**
	 * 大于10000,参数错误,唯一不复用
	 * */
	PARAM_VALIDATE_FAILED(10001, "参数错误"), PARAM_CAPTCHA_FAILED(10002, "验证码错误"),

	/**
	 * 大于20000,业务错误,唯一不复用
	 * */
	SERVICE_INTERNAL_ERROR(20001, "业务处理失败"), SERVICE_INVALID_USERNAME_PASSWORD(20002, "用户名或密码错误"),

	/**
	 * 大于30000,访问权限错误,唯一不复用
	 * */
	AUTHO_INVALID_PC_SIGN(30001, "客户端签名无效"),

	/**
	 * 大于90000,系统错误,唯一不复用
	 * */
	SYSTEM_INTERNAL_ERROR(90001, "系统内部错误"),

	/**
	 * 大于100000,HTTP code,唯一不复用
	 * */
	HTTP_REDIRECT(100001, "重定向");

	private int code;
	private String message;

	private MessageCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param code
	 * @return
	 */
	public static MessageCode getEnum(int code) {
		for (MessageCode enumItem : MessageCode.values()) {
			if (enumItem.getCode() == code) {
				return enumItem;
			}
		}
		return null;
	}
}
