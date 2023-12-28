package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログアウトのservletクラス
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String isDeleted = request.getParameter("isDeleted");

		// セッションに保存されたアカウントを破棄してログアウトする。
		HttpSession session = request.getSession();
		session.removeAttribute("accountUser");

		// セッションに保存された検索キーワードを破棄してログアウトする。
		session.removeAttribute("key");

		if (isDeleted == null) {
			// 検索ページより、ログアウトする時に成功メッセージをリクエストスコープに保存する。
			request.setAttribute("LogoutSuccess", "ログアウトしました");

		} else if ("1".equals(isDeleted)) {
			// "削除"のチェックボックスにチェックがある時、
			// アカウント削除メッセージをリクエストスコープに保存する。
			request.setAttribute("LogoutSuccess", "アカウント情報を削除しました");

		} else if ("0".equals(isDeleted)) {
			// "削除"のチェックボックスにチェックがない時、
			// アカウント更新メッセージをリクエストスコープに保存する。
			request.setAttribute("LogoutSuccess", "アカウント情報を更新しました");	

		}
		// ログインページへフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
