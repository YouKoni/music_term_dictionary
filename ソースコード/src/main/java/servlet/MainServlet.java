package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.GetMusicTermListLogic;
import model.Account;
import model.BrowseHistory;
import model.MusicTerm;

/**
 * 検索(メイン)画面ページのservletクラス
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			request.setCharacterEncoding("UTF-8");

			// 閲覧履歴の登録
			// セッションスコープからユーザーIDを取得
			HttpSession session = request.getSession();
			Account accountUesr = (Account)session.getAttribute("accountUser");

			List<MusicTerm> musicTermList = null;

			if (request.getParameter("key") != null) {
				// 検索キーワードがある場合、
				// GETパラメータで日本語を受け取ると文字化けするので、servlet.xmlに下記を追記する
				GetMusicTermListLogic getMusicTermListLogic = new GetMusicTermListLogic();
				musicTermList = getMusicTermListLogic.find(request.getParameter("key"));

				// 検索テキストボックス表示用
				session.setAttribute("key", request.getParameter("key"));
			} 

			// ログイン時にのみ実行される
			if (accountUesr != null) {

				// アカウント別に閲覧履歴をリセットする。
				List<BrowseHistory> browseHistoryList = null;

				// 解説画面ページの「検索ページに戻る」を押した時の閲覧日と検索履歴の表示
				GetMusicTermListLogic getMusicTermListLogic = new GetMusicTermListLogic();
				browseHistoryList = getMusicTermListLogic.findHistory(accountUesr.getId()); 

				// 閲覧日と検索履歴の情報をリクエストスコープに保存する。
				request.setAttribute("browseHistoryList", browseHistoryList); 
			}

			// 音楽用語・記号の情報をリクエストスコープに保存する。  
			request.setAttribute("musicTermList", musicTermList);

			// 検索(メイン)ページへフォワードする。
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

			// エラーページへフォワードする
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの文字コードを指定。
		response.setContentType("text/html; charset=UTF-8");
	}
}
