package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 音楽用語・記号モデルクラス
 */
public class MusicTerm implements Serializable {
	private int id; 
	private String term;
	private String image;
	private String termExplain;
	private Timestamp createdAt;
	private Timestamp updateAt;

	public MusicTerm() {}
	public MusicTerm(String term, String image, String termExplain) {
		this.term = term;
		this.image = image;
		this.termExplain = termExplain;
	}

	public int getId() { return id; }
	public void setId(int id) {this.id = id;}

	public String getTerm() { return term; }
	public void setTerm(String term) { this.term = term; }

	public String getImage() { return image; }
	public void setImage(String image) { this.image = image; }

	public String getTermExplain() { return termExplain; }
	public void setTermExplain(String termExplain) { this.termExplain = termExplain; }

	public Timestamp getCreatedAt() { return createdAt; }
	public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

	public Timestamp getUpdateAt() { return updateAt; }
	public void setUpdateAt(Timestamp updateAt) { this.updateAt = updateAt; }

}
