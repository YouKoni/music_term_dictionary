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
import javax.servlet.http.HttpSession;

import logic.PostAccountListLogic;
import model.Account;
import settings.DatabaseSettings;
import settings.MessageSettings;
import validation.AccountValidation;

/**
 * アカウント更新のservletクラス
 */
@WebServlet("/AccountUpdateServlet")
public class AccountUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 何も処理せずにアカウント更新ページにフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/AccountUpdate.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータ
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String isDeleted = "0";

		try {

			// 削除フラグにチェックボックスが入っているかを確認。
			if (request.getParameter("isDeleted") != null) {
				isDeleted = "1";
			}

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
				accountUser.put("isDeleted", isDeleted);
				request.setAttribute("accountUser", accountUser);

				// アカウント更新ページへフォワードして終了する。
				RequestDispatcher dispatcher =
						request.getRequestDispatcher("WEB-INF/jsp/AccountUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// アカウントのIDをセッションから取得する。
			HttpSession session = request.getSession();
			Account accountUser = (Account) session.getAttribute("accountUser");
			int id = accountUser.getId();

			// リクエストパラメータをアカウントモデルに設定する。
			accountUser.setId(id);
			accountUser.setEmail(request.getParameter("email"));
			accountUser.setPassword(request.getParameter("password"));
			accountUser.setName(request.getParameter("name"));
			accountUser.setIsDeleted(Integer.parseInt(isDeleted));

			// アカウント情報を更新する。
			PostAccountListLogic logic = new PostAccountListLogic();
			int ret = logic.update(accountUser);

			// 実行結果により処理を切り替える。
			switch (ret) {

			case DatabaseSettings.DB_EXECUTION_SUCCESS:
				// データベース操作成功のとき、ログアウトさせ、ログインページへリダイレクトして終了する。
				session.removeAttribute("accountUser");
				response.sendRedirect("./LogoutServlet?isDeleted=" + isDeleted);
				return;

			case DatabaseSettings.DB_EXECUTION_FAILURE_ERR_DUP_KEYNAME:
				// ユニークKEYが重複（メールアドレスが重複）しているとき、エラーメッセージをリクエストスコープに保存する。
				request.setAttribute("dbError", String.format(MessageSettings.MSG_ER_DUP_KEYNAME,  accountUser.getEmail()));

				// アカウント更新ページへフォワード
				RequestDispatcher dispatcher =
						request.getRequestDispatcher("WEB-INF/jsp/AccountUpdate.jsp");
				dispatcher.forward(request, response);
				break;

			default:
				// その他エラーのとき、エラーメッセージをリクエストスコープに保存する。
				request.setAttribute("dbError", MessageSettings.MSG_ERROR_OCCURRED);
				break;
			}

			// リクエストスコープにアカウントモデルを保存する。
			request.setAttribute("accountUser", accountUser);

			return;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}
}
