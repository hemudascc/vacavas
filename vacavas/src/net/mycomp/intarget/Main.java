package net.mycomp.intarget;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.util.HttpURLConnectionUtil;

import org.eclipse.persistence.jaxb.JAXBContextFactory;

public class Main {

//	public static void main(String[] args) throws Exception{
//		JAXBContext moxyContext = JAXBContextFactory.createContext(new Class<?>[]{InTargetMessageRequest.class}, null);
//		 Marshaller marshaller = moxyContext.createMarshaller();
//	     marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//	        marshaller.marshal(new InTargetMessageRequest(
//	        		"SendSMS", "100", "client", "pass", "254753903308",
//					 "00020000", "2304", "GAP", "2500", "DOI USSD"
//					 
//	        		), System.out);
//	}
	
	
//	public static void main(String[] args) throws Exception{
//		 JAXBContext moxyContext = JAXBContextFactory.createContext(new Class<?>[]{
//				 IntargetNotificationMessage.class,IntargetOnReceiveSMSNotification.class,
//				 IntargetOnResultNotification.class}, null);
//		 Unmarshaller unmarshaller = moxyContext.createUnmarshaller();
//		 IntargetNotificationMessage intargetNotification=(IntargetNotificationMessage)unmarshaller.unmarshal(
//				 new File("C:\\Users\\mobitize\\Desktop\\temp\\27022019\\b.txt"));
//		 
//		 System.out.println("intargetNotification::: "+
//		 intargetNotification.getIntargetNotification().getRefNo()
//		 );
//	}
	
	
	
//	public static void main(String[] args) throws Exception{
//		JAXBContext moxyContext = JAXBContextFactory.createContext(new Class<?>[]{
//				InTargetMessageRequest.class,IntargetMessageResponse.class,
//				IntargetNotificationMessage.class
//				}, null);
//		
//		Unmarshaller unmarshaller = moxyContext.createUnmarshaller();
//		IntargetNotificationMessage intargetMessageResponse=(IntargetNotificationMessage)unmarshaller.unmarshal(
//				 new File("C:\\Users\\mobitize\\Desktop\\temp\\27022019\\a.txt"));
//		 
//		 System.out.println("IntargetNotificationMessage::: "+intargetMessageResponse
//		 );
//	}
	
	public static void main(String[] args) throws Exception{
		HttpURLConnectionUtil httpURLConnectionUtil=new HttpURLConnectionUtil(); 
		 String content = new String(Files.readAllBytes(Paths.
				 get("C:\\Users\\mobitize\\Desktop\\temp\\27022019\\b.txt")));
		 httpURLConnectionUtil.makeHTTPPOSTRequestWithSoapXML("http://localhost:8080/ccsub/cnt/hgate/notification", content);
		 
	}

}
