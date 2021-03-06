package com.bt.gpa;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {
    
    /**
     * @param args
     */
	public static void main(String[] args) throws Exception {

	        // 1. The port that we should run on can be set into an environment variable
	        //Look for that variable and default to 8080 if it isn't there.
                String webPort = System.getenv("PORT");
	        if(webPort == null || webPort.isEmpty()) {
	 		webPort = "8080";
  	        }
	        Server server = new Server(Integer.valueOf(webPort));

		// 2. Creating the WebAppContext for the created content
		WebAppContext ctx = new WebAppContext();
		ctx.setResourceBase("src/main/webapp");
		ctx.setContextPath("/");
		
		//3. Including the JSTL jars for the webapp.
		ctx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
	
		//4. Enabling the Annotation based configuration
		org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
                classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
                classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
        
                //5. Setting the handler and starting the Server
		server.setHandler(ctx);
		server.start();
		server.join();
	}
	
}
