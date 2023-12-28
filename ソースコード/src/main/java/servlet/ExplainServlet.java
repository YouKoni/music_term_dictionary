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

import logic.GetMusicTermListLogic;
import model.Account;
import model.BrowseHistory;
import model.MusicTerm;

/**
 * 音楽用語・記号の解説画面ページのservletクラス
 */
@WebServlet("/ExplainServlet")
public class ExplainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			request.setCharacterEncoding("UTF-8");

			// リクエストパラメータの文字コードを指定
			response.setContentType("text/html; charset=UTF-8");
			int id = Integer.parseInt(request.getParameter("musicTermId")); 

			// 音楽記号・用語のIDを追加
			MusicTerm musicTerm = new MusicTerm();
			musicTerm.setId(id);

			// MusicTermのID情報を取得する処理
			GetMusicTermListLogic getMusicTermListLogic = new GetMusicTermListLogic();
			musicTerm = getMusicTermListLogic.find(musicTerm.getId());

			// 改行コード「\r\n」を<br>に変換する処理
			musicTerm.setTerm(getMusicTermListLogic.replace(musicTerm.getTerm()));
			musicTerm.setTermExplain(getMusicTermListLogic.replace(musicTerm.getTermExplain()));

			// Music_Termモデルをリクエストスコープに保存する。
			request.setAttribute("musicTerm", musicTerm);

			// 閲覧履歴の登録
			// セッションスコープからユーザーIDを取得
			HttpSession session = request.getSession();
			Account accountUesr = (Account)session.getAttribute("accountUser");

			// ログインしている時だけ、閲覧履歴を登録
			if (accountUesr != null) {

				// Browse_History(閲覧履歴)モデルのインスタンス生成
				BrowseHistory browseHistory = new BrowseHistory();

				browseHistory.setUserId(accountUesr.getId()); // ユーザーID
				browseHistory.setTermId(id);                  // 音楽用語・記号ID

				// Browse_Histroyモデルの登録結果
				boolean result = getMusicTermListLogic.create(browseHistory);

				// Browse_Histroyモデルの登録処理結果
				if (result == false) {
					// エラーページへフォワードする。
					RequestDispatcher dispatcher =
							request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
					dispatcher.forward(request, response);
				}
			}

			// フォワード
			RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/explain.jsp");
			dispatcher.forward(request, response);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 解説画面ページにフォワードする
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/explain.jsp");
		dispatcher.forward(request, response);
	}
}
