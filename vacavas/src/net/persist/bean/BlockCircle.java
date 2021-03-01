package net.persist.bean;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name = "tb_block_circle")
@NamedQueries({ @NamedQuery(name = "BlockCircle.findBlockCircle", 
query = "SELECT b FROM BlockCircle b where b.status=:status ")})
public class BlockCircle {

	@Id
	private Integer id;
	@Column(name = "circle_id")
	private Integer circleId;
	
	@Column(name = "op_id")
	private Integer opId;
	
	
	private Boolean status;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	
}
