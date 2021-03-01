package net.persist.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@Table(name = "tb_operator_circles")
@NamedQueries({
	@NamedQuery(name = "OperatorCircles.findEnableOperatorCircles",
        query = "SELECT b FROM OperatorCircles b WHERE b.status=:status")})
public class OperatorCircles {

	@Id
	@GeneratedValue
	@Column(name = "circle_id", unique = true, nullable = false)
	private Integer circleId;
	@Column(name = "operator_id")
	private Integer operatorId;
	@Column(name = "circle_name")
	private String circleName;
	@Column(name = "zone_name")
	private String zoneName;
	private Boolean status;
	private Integer lrn;
	@Column(name = "op_circle_code")
	private String opCircleCode;
	@Column(name = "op_circle_code_descp")
	private String opCircleCodeDescp;
	
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getLrn() {
		return lrn;
	}
	public void setLrn(Integer lrn) {
		this.lrn = lrn;
	}
	public String getOpCircleCode() {
		return opCircleCode;
	}
	public void setOpCircleCode(String opCircleCode) {
		this.opCircleCode = opCircleCode;
	}
	public String getOpCircleCodeDescp() {
		return opCircleCodeDescp;
	}
	public void setOpCircleCodeDescp(String opCircleCodeDescp) {
		this.opCircleCodeDescp = opCircleCodeDescp;
	}
}
