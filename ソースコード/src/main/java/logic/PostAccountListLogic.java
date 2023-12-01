package logic;

import java.sql.Connection;
import java.sql.SQLException;

import dao.AccountDAO;
import database.DBConnection;
import model.Account;

/**
 * アカウント情報をdoPost()として送るlogicクラス
 */
public class PostAccountListLogic {

	/**
	 * 新たなアカウント情報を取得する
	 * @return AccountDAOのアカウント情報を追加するメソッド(アカウントモデル)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */  	
	public int create(Account account) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			AccountDAO dao = new AccountDAO();

			return dao.create(conn, account);
		}
	}

	/**
	 * アカウント情報を更新する
	 * @return AccountDAOのアカウント情報を更新するメソッド(アカウントモデル)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */	
	public int update(Account account) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			AccountDAO dao = new AccountDAO();

			return dao.update(conn, account);
		}
	}
}
