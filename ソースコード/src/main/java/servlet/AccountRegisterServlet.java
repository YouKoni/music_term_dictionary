package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PostAccountListLogic;
import model.Account;
import settings.DatabaseSettings;
import settings.MessageSettings;
import validation.AccountValidation;

/**
 * アカウント登録のservletクラス
 */
@WebServlet("/AccountRegisterServlet")
public class AccountRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// アカウント登録ページへフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/AccountRegister.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの文字コードを指定
		response.setContentType("text/html; charset=UTF-8");
		String email = request.getParameter("email");         // E-mailアドレス
		String password = request.getParameter("password");   // パスワード
		String name = request.getParameter("name");           // アカウント名

		try {

			// バリデーションチェックを行う。
			AccountValidation validate = new AccountValidation(request);
			Map<String, String> errors = validate.validate();

			// バリデーションエラーがあった時
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);

				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> accountUser = new HashMap<String, String>();
				accountUser.put("email", email);
				accountUser.put("password", password);
				accountUser.put("name", name);
				request.setAttribute("accountUser", accountUser);


				// アカウント登録ページへフォワードして終了する
				RequestDispatcher dispatcher =
						request.getRequestDispatcher("WEB-INF/jsp/AccountRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// アカウント情報を追加
			Account account = new Account();
			account.setEmail(email);
			account.setPassword(password);
			account.setName(name);
			PostAccountListLogic postAccountListLogic = new PostAccountListLogic();
			int ret = postAccountListLogic.create(account);

			// 実行結果により処理を切り替える
			switch (ret) {

			case DatabaseSettings.DB_EXECUTION_SUCCESS:
				// データベース操作成功のとき、ログインページへリダイレクトして終了する。
				response.sendRedirect("./LoginServlet");
				return;

			case DatabaseSettings.DB_EXECUTION_FAILURE_ERR_DUP_KEYNAME:
				// ユニークKEYが重複（メールアドレスが重複）しているとき、エラーメッセージをリクエストスコープに保存する。
				request.setAttribute("dbError", String.format(MessageSettings.MSG_ER_DUP_KEYNAME, account.getEmail()));

				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> accountUser = new HashMap<String, String>();
				accountUser.put("email", email);
				accountUser.put("password", password);
				accountUser.put("name", name);
				request.setAttribute("accountUser", accountUser);

				// アカウント登録ページへフォワード
				RequestDispatcher dispatcher =
					request.getRequestDispatcher("WEB-INF/jsp/AccountRegister.jsp");
				dispatcher.forward(request, response);
				break;

			default:
				// その他エラーの時、エラーメッセージをリクエストスコープに保存する
				request.setAttribute("dbError", MessageSettings.MSG_ERROR_OCCURRED);
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);
		}
	}
}
