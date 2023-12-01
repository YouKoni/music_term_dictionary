package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.BrowseHistory;
import model.MusicTerm;

/**
 * 音楽用語・記号のDAOクラス
 */
public class MusicTermDAO {
	//データベース接続に使用する情報
	private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final String JDBC_URL = "jdbc:mysql://localhost/music_dictionary_system?connectionCollation=utf8mb4_general_ci";
	private final String DB_USERS = "root";
	private final String DB_PASS = "";

	/**
	 * 指定する音楽用語・記号IDを1件検索します。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param id         音楽用語・記号iD
	 * @return 検索結果（音楽用語・記号モデル）
	 */
	public MusicTerm findOne(Connection connection, int id) {
		// レコードを格納するMusic_Termのインスタンスの生成
		MusicTerm musicTerm = new MusicTerm();

		try {
			// JDBCドライバを読み込み
			Class.forName(DRIVER_NAME);

			// try-with-resourcesの構文
			// データベースへ接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS)) {
				String sql = "SELECT * FROM MUSIC_TERM WHERE ID = ?";

				// SELECT文中の「?」に使用する値を設定しSQLを完成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, id);

				// SQLを実行
				try (ResultSet rs = pStmt.executeQuery();) {

					// SQLの実行結果をArrayListに格納
					if (rs.next()) {
						musicTerm.setId(rs.getInt("ID"));
						musicTerm.setTerm(rs.getString("TERM"));
						musicTerm.setImage(rs.getString("IMAGE"));
						musicTerm.setTermExplain(rs.getString("TERM_EXPLAIN"));
						musicTerm.setCreatedAt(Timestamp.valueOf(rs.getString("CREATED_AT")));
						musicTerm.setUpdateAt(Timestamp.valueOf(rs.getString("UPDATE_AT")));

					} else {
						musicTerm = null;

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

		return musicTerm;

	}

	/**
	 * 指定された音楽記号・用語をキーワードで検索します。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param keyWord  検索キーワード(main.jspのname属性である"key"と同じ位置付け)           
	 * @return MusicTermモデルのArrayList
	 */
	public List<MusicTerm> findByKeyWord(Connection connection, String keyWord) {
		// レコードを格納するMusic_Termのインスタンスの生成
		List<MusicTerm> MusicTermList = new ArrayList<MusicTerm>();

		try {

			// SQL文を設定する。
			String sql = "SELECT * FROM MUSIC_TERM WHERE TERM LIKE ? ORDER BY ID ASC";  	   

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータの設定
				stmt.setString(1, "%" + keyWord + "%");   		  

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する。 
					while (rs.next()) {
						// Music_Termモデルのインスタンスを生成する。
						MusicTerm musicTerm = new MusicTerm();

						// フィールドに値を設定する。
						musicTerm.setId(rs.getInt("ID"));
						musicTerm.setTerm(rs.getString("TERM"));
						musicTerm.setImage(rs.getString("IMAGE"));
						musicTerm.setTermExplain(rs.getString("TERM_EXPLAIN"));
						musicTerm.setCreatedAt(Timestamp.valueOf(rs.getString("CREATED_AT")));
						musicTerm.setUpdateAt(Timestamp.valueOf(rs.getString("UPDATE_AT"))); 

						// レコードをArrayListに追加する。
						MusicTermList.add(musicTerm);
					}   
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return MusicTermList;	

	}

	/**
	 * 検索した音楽用語・記号の閲覧履歴を10件表示する。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param userId     ユーザーid               
	 * @return BrowseHistoryモデルのArrayList
	 */
	public List<BrowseHistory> findBrowseHistoryTen(Connection connection, int userId) {
		// レコードを格納するMusic_Termのインスタンスの生成
		List<BrowseHistory> BrowseHistoryList = new ArrayList<BrowseHistory>(); 

		try {

			// SQL文を設定する。
			String sql = "SELECT * FROM BROWSE_HISTORY INNER JOIN MUSIC_TERM ON MUSIC_TERM.ID = BROWSE_HISTORY.TERM_ID WHERE USER_ID = ? ORDER BY CHECKED_DATE DESC LIMIT 10 OFFSET 0";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				// SELECT文中の「?」に使用する値を設定しSQLを完成
				stmt.setInt(1, userId);
				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する。 
					while (rs.next()) {
						// Music_Termモデルのインスタンスを生成する。
						BrowseHistory browseHistory = new BrowseHistory();

						// フィールドに値を設定する。
						browseHistory.setId(rs.getInt("ID"));
						browseHistory.setUserId(rs.getInt("USER_ID"));
						browseHistory.setTermId(rs.getInt("TERM_ID"));
						browseHistory.setTermName(rs.getString("TERM"));
						browseHistory.setCheckedDate(Timestamp.valueOf(rs.getString("CHECKED_DATE")));
						browseHistory.setCreatedAt(Timestamp.valueOf(rs.getString("CREATED_AT")));
						browseHistory.setUpdateAt(Timestamp.valueOf(rs.getString("UPDATE_AT"))); 

						BrowseHistoryList.add(browseHistory);

					}   
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return BrowseHistoryList;	   

	}

	/**
	 * 検索した音楽用語・記号の閲覧履歴の情報をを追加する。
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param browseHistory   閲覧履歴モデル                              
	 */
	public boolean create(Connection connection, BrowseHistory browseHistory) {

		try {
			// JDBCドライバを読み込み
			Class.forName(DRIVER_NAME);     		     

			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERS, DB_PASS)) {

				// INSERT文の準備(idは自動連番なので指定しなくてよい)
				String sql = "INSERT INTO BROWSE_HISTORY (USER_ID, TERM_ID) VALUES(?, ?)";

				PreparedStatement pStmt = conn.prepareStatement(sql);

				// INSERT文中の「?」に使用する値を設定しSQLを完成
				pStmt.setInt(1, browseHistory.getUserId());
				pStmt.setInt(2, browseHistory.getTermId());

				// INSERT文を実行(resultには追加された行数が代入される)
				int result = pStmt.executeUpdate();

				if (result != 1) {
					return false;  
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}	   
	}
}
