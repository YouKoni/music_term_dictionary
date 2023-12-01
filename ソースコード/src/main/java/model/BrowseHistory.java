package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 閲覧履歴モデルクラス
 */
public class BrowseHistory implements Serializable {
	private int id; 
	private int userId;
	private int termId;
	private String termName;
	private Timestamp checkedDate;
	private Timestamp createdAt;
	private Timestamp updateAt;

	public BrowseHistory() {}

	public int getId() { return id; }
	public void setId(int id) {this.id = id;}

	public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }

	public int getTermId() { return termId; }
	public void setTermId(int termId) { this.termId = termId; }

	public String getTermName() { return termName; }
	public void setTermName(String termName) {this.termName = termName; }

	public Timestamp getCheckedDate() { return checkedDate; }
	public void setCheckedDate(Timestamp checkedDate) { this.checkedDate = checkedDate; }

	public Timestamp getCreatedAt() { return createdAt; }
	public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

	public Timestamp getUpdateAt() { return updateAt; }
	public void setUpdateAt(Timestamp updateAt) { this.updateAt = updateAt; }

}
