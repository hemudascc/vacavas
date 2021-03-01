package net.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JMSContainerShutDownHook implements ServletContextListener {
   
	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("JMSContainerShutDownHook: Initialized called");
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	System.out.println("JMSContainerShutDownHook: Destroyed called");
        try {
        	System.out.println("JMSContainerShutDownHook: Fetching JMS Container Bean from Application Context");
            ServletContext ctx = servletContextEvent.getServletContext();
            WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            //retrieve your Spring beans here...
            
            DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) springContext.getBean(DefaultMessageListenerContainer.class);
            System.out.println("JMSContainerShutDownHook: Calling shutdown on DefaultMessageListenerContainer");
            container.shutdown();
            Thread.sleep(30000); //Wait for the container to shutdown
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        System.out.println("JMSContainerShutDownHook: Exiting:::::::::::::::::::::::::: ");
    }
}

