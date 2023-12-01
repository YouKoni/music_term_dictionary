package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import settings.DatabaseSettings;

/**
 * アカウントのDAOクラス
 */
public class AccountDAO {
	//データベース接続に使用する情報
	private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final String JDBC_URL = "jdbc:mysql://localhost/music_dictionary_system?connectionCollation=utf8mb4_general_ci";
	private final String DB_USERS = "root";
	private final String DB_PASS = "";

	/**
	 * 全てのアカウント情報を取得する。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return アカウントのArrayList
	 */
	public List<Account> findAll(Connection connection) {
		List<Account> AccountList = new ArrayList<Account>();

		try {
			// JDBCドライバを読み込む
			Class.forName(DRIVER_NAME);

			// try-with-resourcesの構文
			// データベースへ接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS)) {
				String sql = "SELECT * FROM ACCOUNT " + "WHERE IS_DELETED = 0 " + "ORDER BY ID ASC";

				// SQLを実行
				try (PreparedStatement pStmt = conn.prepareStatement(sql) ; ResultSet rs = pStmt.executeQuery();) {
					// SQLの実行結果をArrayListに格納
					while (rs.next()) {
						Account account = new Account();
						account.setId(rs.getInt("ID"));
						account.setEmail(rs.getString("EMAIL"));
						account.setPassword(rs.getString("PASSWORD"));
						account.setName(rs.getString("NAME"));
						account.setIsDeleted(rs.getInt("IS_DELETED"));
						account.setCreatedAt(Timestamp.valueOf(rs.getString("CREATED_AT")));
						account.setUpdateAt(Timestamp.valueOf(rs.getString("UPDATE_AT")));
						AccountList.add(account);
					}
				}   
			}  
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return AccountList;
	}	

	/**
	 * 指定アカウントIDの情報を1件検索します。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param id         アカウントiD
	 * @return 検索結果（アカウントモデル）
	 */
	public Account findOne(Connection connection, int id) {
		Account account = new Account();

		try {
			// JDBCドライバを読み込む
			Class.forName(DRIVER_NAME);

			// try-with-resourcesの構文
			// データベースへ接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS)) {
				String sql = "SELECT * FROM ACCOUNT WHERE ID = ?";

				// SELECT文中の「?」に使用する値を設定しSQLを完成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, account.getId());

				// SQLを実行
				try (ResultSet rs = pStmt.executeQuery();) {

					// SQLの実行結果をArrayListに格納
					if (rs.next()) {
						account.setId(rs.getInt("ID"));
						account.setEmail(rs.getString("EMAIL"));
						account.setPassword(rs.getString("PASSWORD"));
						account.setName(rs.getString("NAME"));
						account.setIsDeleted(rs.getInt("IS_DELETED"));
						account.setCreatedAt(Timestamp.valueOf(rs.getString("CREATED_AT")));
						account.setUpdateAt(Timestamp.valueOf(rs.getString("UPDATE_AT")));
					} else {
						account = null;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return account;
	}		   

	/**
	 * 指定アカウントのEmailとパスワードの情報を1件検索します。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param email      E-mail
	 * @param password   パスワード
	 * @return 検索結果（アカウントモデル）
	 */
	public Account findOne(Connection connection, String email, String password) {
		Account account = new Account();

		try {
			// JDBCドライバを読み込む
			Class.forName(DRIVER_NAME);

			// try-with-resourcesの構文
			// データベースへ接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS)) {
				String sql = "SELECT * FROM ACCOUNT WHERE IS_DELETED = 0 AND EMAIL = ? AND PASSWORD = ?";

				// SELECT文中の「?」に使用する値を設定しSQLを完成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, email);
				pStmt.setString(2, password);

				// SQLを実行
				try (ResultSet rs = pStmt.executeQuery();) {

					// SQLの実行結果をArrayListに格納
					if (rs.next()) {
						account.setId(rs.getInt("ID"));
						account.setEmail(rs.getString("EMAIL"));
						account.setPassword(rs.getString("PASSWORD"));
						account.setName(rs.getString("NAME"));
						account.setIsDeleted(rs.getInt("IS_DELETED"));
						account.setCreatedAt(Timestamp.valueOf(rs.getString("CREATED_AT")));
						account.setUpdateAt(Timestamp.valueOf(rs.getString("UPDATE_AT")));
					} else {
						account = null;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return account;
	}		   	   

	/**
	 * 新たなアカウント情報を取得する。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param account 検索結果（アカウントモデル）
	 * @return データベース操作の成功を表示
	 */
	public int create(Connection connection, Account account) {	   
		try {
			// JDBCドライバを読み込み
			Class.forName(DRIVER_NAME);

			// データベース接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS)) {

				// INSERT文の準備(idは自動連番なので指定しなくてよい)
				String sql = "INSERT INTO ACCOUNT (EMAIL, PASSWORD, NAME) VALUES(?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				// INSERT文中の「?」に使用する値を設定しSQLを完成
				pStmt.setString(1, account.getEmail());
				pStmt.setString(2, account.getPassword());
				pStmt.setString(3, account.getName());

				// INSERT文を実行(resultには追加された行数が代入される)
				int result = pStmt.executeUpdate();
				if (result != 1) {

				}
			} catch (SQLException e) {
				e.printStackTrace();

				return e.getErrorCode();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}
		return DatabaseSettings.DB_EXECUTION_SUCCESS;   
	} 	  

	/**
	 * 既存のアカウント情報を更新する。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param account 検索結果（アカウントモデル）
	 * @return データベース操作の成功を表示
	 */
	public int update(Connection connection, Account account) {
		Connection conn = null;

		try {
			// JDBCドライバを読み込み
			Class.forName(DRIVER_NAME);

			// データベースへ接続
			try {

				conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS);

				// INSERT文の準備(idは自動連番なので指定しなくてよい)
				String sql = "UPDATE ACCOUNT SET EMAIL = ?, PASSWORD = ?, NAME = ?, IS_DELETED = ? WHERE ID = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				// パラメータを設定
				pStmt.setString(1, account.getEmail());
				pStmt.setString(2, account.getPassword());
				pStmt.setString(3, account.getName());
				pStmt.setInt(4, account.getIsDeleted());
				pStmt.setInt(5, account.getId());

				// アップデート文を実行
				pStmt.executeUpdate();

				// prepareStatementを明示的に閉じる
				pStmt.close();

			} catch (SQLException e) {
				e.printStackTrace();

				return e.getErrorCode();

			} finally {

				try {
					if (conn != null) {	
						conn.close();
					}  
				} catch (SQLException e) {

					e.printStackTrace();

					return e.getErrorCode();
				}
			}

		} catch (ClassNotFoundException e) {    
			e.printStackTrace();

		}
		return DatabaseSettings.DB_EXECUTION_SUCCESS;
	}
}
