package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;
import util.Validation;
import util.ValidationUtil;

public class AccountValidation extends Validation {

	/**
	 * コンストラクタ
	 * 
	 * @param request リクエストオブジェクト
	 */
	public AccountValidation(HttpServletRequest request) {
		super(request);
	}

	/**
	 * バリデーションを行います。
	 * 
	 * @return バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public Map<String, String> validate() {

		// アカウント名のバリデーション
		if (!ValidationUtil.isMinLength(this.request.getParameter("name"), 1)) {
			this.errors.put("name", String.format(MessageSettings.MSG_REQUIRED, "アカウント名"));
		}

		if (!ValidationUtil.isMaxLength(this.request.getParameter("name"), 50)) {
			this.errors.put("name", String.format(MessageSettings.MSG_LENGTH_LONG, "アカウント名", 50));
		}

		// メールアドレスのバリデーション
		if (!ValidationUtil.isEmail(this.request.getParameter("email"))) {
			this.errors.put("email", MessageSettings.MSG_EMAIL_FAILURE);
		}

		// パスワードのバリデーション
		if (!ValidationUtil.isPassword(this.request.getParameter("password"))) {
			this.errors.put("password", MessageSettings.MSG_PASSWORD_FAILURE);
		}

		return errors;

	}

}
