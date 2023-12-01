package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.MusicTermDAO;
import database.DBConnection;
import model.BrowseHistory;
import model.MusicTerm;

/**
 * 音楽用語・記号情報をdoGet()として送るlogicクラス
 */
public class GetMusicTermListLogic {

	/**
	 * 音楽用語・記号IDを1件取得します。
	 * @return MusicTermDAOの音楽用語・記号IDを1件検索するメソッド(音楽用語・記号iD)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MusicTerm find(int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			MusicTermDAO dao = new MusicTermDAO();

			return dao.findOne(conn, id);
		}
	}

	/**
	 * 音楽用語・記号を検索します。
	 * @return MusicTermDAOの音楽用語・記号をキーワード検索するメソッド(main.jspの"key")
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MusicTerm> find(String keyWord) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			MusicTermDAO dao = new MusicTermDAO();

			return dao.findByKeyWord(conn, keyWord);
		}
	}

	/**
	 * 閲覧履歴を取得する。
	 * @return MusicTermDAOの閲覧履歴を10件取得するメソッド(ユーザーID)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */ 
	public List<BrowseHistory> findHistory(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			MusicTermDAO dao = new MusicTermDAO();

			return dao.findBrowseHistoryTen(conn, userId);
		}
	}

	/**
	 * 閲覧履歴を追加する。
	 * @return MusicTermDAOの検索した閲覧履歴の情報をを追加するメソッド(閲覧履歴モデル)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean create(BrowseHistory browseHistory) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			MusicTermDAO dao = new MusicTermDAO();

			return dao.create(conn, browseHistory);
		}
	}

	/**
	 * \r\nを<br />に置き換える。
	 * @return ""(文字なし)
	 * @return str(入力文字)
	 */
	public String replace(String str) {
		if (str == null || "".equals(str)) {

			return "";
		}

		str = str.replaceAll("\r\n", "<br />");
		str = str.replaceAll("\n", "<br />");
		return str;

	}
}
