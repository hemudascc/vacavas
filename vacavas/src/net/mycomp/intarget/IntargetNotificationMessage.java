package net.mycomp.intarget;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class IntargetNotificationMessage {
	
	@XmlPath("Response")
	
	private IntargetNotification intargetNotification;
	
public String toString() {
		
        Field[] fields = this.getClass().getDeclaredFields();
        String str = this.getClass().getName();
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

public IntargetNotification getIntargetNotification() {
	return intargetNotification;
}

public void setIntargetNotification(IntargetNotification intargetNotification) {
	this.intargetNotification = intargetNotification;
}
}
