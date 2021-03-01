package net.mycomp.intarget;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="OnResult")
@XmlDiscriminatorValue(value="OnResult")
@Entity
@Table(name = "tb_intarget_onresult_notification")
public class IntargetOnResultNotification extends IntargetNotification implements Serializable{

	@XmlPath("OnResult/@Flags")
	@Column(name = "on_result_flags")
	private String onResultflags;
	@XmlPath("OnResult/@Code")
	@Column(name = "on_result_code")
	private String onResultcode;
	@XmlPath("OnResult/@SubCode")
	@Column(name = "on_result_sub_code")
	private String onResultSubCode;
	@XmlPath("OnResult/@Text")
	@Column(name = "on_result_text")
	private String onResultText;
	
	public IntargetOnResultNotification(){
		super();
	}
public String toString() {
		
        Field[] fields = this.getClass().getDeclaredFields();
        String str = this.getClass().getName();
        str+=super.toString();
        try {
            for (Field field : fields) {
                str += field.getName() + "=" + field.get(this) + ",";
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        } catch (IllegalAccessException ex) {
            System.out.println(ex);
        }
        return str;
    }

public String getOnResultflags() {
	return onResultflags;
}

public void setOnResultflags(String onResultflags) {
	this.onResultflags = onResultflags;
}

public String getOnResultcode() {
	return onResultcode;
}

public void setOnResultcode(String onResultcode) {
	this.onResultcode = onResultcode;
}

public String getOnResultSubCode() {
	return onResultSubCode;
}

public void setOnResultSubCode(String onResultSubCode) {
	this.onResultSubCode = onResultSubCode;
}

public String getOnResultText() {
	return onResultText;
}

public void setOnResultText(String onResultText) {
	this.onResultText = onResultText;
}

}
