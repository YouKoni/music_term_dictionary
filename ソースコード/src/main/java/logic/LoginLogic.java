package logic;

import java.sql.Connection;
import java.sql.SQLException;

import dao.AccountDAO;
import database.DBConnection;
import model.Account;

/**
 * ログインする為のlogicクラス
 */
public class LoginLogic {

	/**
	 * Emailとパスワードの情報を1件検索します
	 * @return AccountDAOのアカウント情報を取得するメソッド(E-mail, パスワード)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Account find(String email, String password) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			AccountDAO dao = new AccountDAO();

			return dao.findOne(conn, email, password);
		}
	}
}
