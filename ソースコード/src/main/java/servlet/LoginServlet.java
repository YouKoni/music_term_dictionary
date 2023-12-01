package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.LoginLogic;
import model.Account;
import settings.MessageSettings;

/**
 * ログインのservletクラス
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ログインページへフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの文字コードを指定
		response.setContentType("text/html; charset=UTF-8");

		String email = request.getParameter("email");         // E-mailアドレス
		String password = request.getParameter("password");   // パスワード

		try {

			// ログイン処理
			LoginLogic LoginAccountLogic = new LoginLogic();
			Account accountUser = LoginAccountLogic.find(email, password);

			// ログイン成功時の処理
			if (accountUser != null) {

				// アカウントモデルがセッションに保存されていることでログイン状態を保持する。
				// セッションからアカウントモデルを削除することでログアウトとする。
				HttpSession session = request.getSession();
				session.setAttribute("accountUser", accountUser);

				// アカウントが見つかった時は、検索(メイン)ページへリダイレクトする。
				response.sendRedirect("./MainServlet");

			} else {

				// アカウントが見つからなかった時は、エラーメッセージをリクエストスコープに設定する。
				request.setAttribute("LoginError", MessageSettings.MSG_LOGIN_FAILURE);

				// ログインに使用した情報を再表示する為に、リクエストスコープに保存する。
				accountUser = new Account();
				accountUser.setEmail(request.getParameter("email"));
				accountUser.setPassword(request.getParameter("password"));
				request.setAttribute("accountUser", accountUser);

				// ログインページへフォワード
				RequestDispatcher dispatcher =
						request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
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
