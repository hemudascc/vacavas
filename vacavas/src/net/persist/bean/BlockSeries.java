package net.persist.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name = "tb_block_series_list")

@NamedQueries({ @NamedQuery(name = "BlockSeries.findBlockSeries", 
query = "SELECT b FROM BlockSeries b where b.seriesNo in (:seriesNo) and b.status=:status"),
	@NamedQuery(name = "BlockSeries.findAllBlockSeries", 
	query = "SELECT b FROM BlockSeries b where  b.status=:status")})


public class BlockSeries {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "series_no")
	private String seriesNo;
	private String circle;
	@Column(name = "op_id")
	private Integer opId;
	private Boolean status;
	@Column(name = "source")
	private String source;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeriesNo() {
		return seriesNo;
	}
	public void setSeriesNo(String seriesNo) {
		this.seriesNo = seriesNo;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
}
