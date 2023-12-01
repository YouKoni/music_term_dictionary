package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.AccountDAO;
import database.DBConnection;
import model.Account;

/**
 * アカウント情報をdoGet()として送るlogicクラス
 */
public class GetAccountListLogic {

	/**
	 * アカウントを全件取得します。
	 * @return AccountDAOの全てのアカウント情報を取得するメソッド
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Account> find() throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			AccountDAO dao = new AccountDAO();

			return dao.findAll(conn);

		}
	}

	/**
	 * 指定ユーザーIDのアカウントを取得します。
	 * @return AccountDAOのIDの情報を1件検索するメソッド(ユーザーiD)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Account find(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			AccountDAO dao = new AccountDAO();

			return dao.findOne(conn, userId);
		}
	}

	/**
	 * 指定されたアカウント情報(E-mail, パスワード)を取得します。
	 * @return AccountDAOのアカウント情報を1件検索するメソッド(Email,パスワード)
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
