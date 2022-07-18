/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import com.connection.DatabaseConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author GraphX
 */
public class AppContextListener implements ServletContextListener {

   public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();
    	
    	String url = ctx.getInitParameter("DBURL");
    	String u = ctx.getInitParameter("DBUSER");
    	String p = ctx.getInitParameter("DBPWD");
    	
    	//create database connection from init parameters and set it to context
    	DatabaseConnection dbManager = new DatabaseConnection(url, u, p);
    	ctx.setAttribute("DBManager", dbManager);
    	System.out.println("Database connection initialized for Application.");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();
    	DatabaseConnection dbManager = (DatabaseConnection) ctx.getAttribute("DBManager");
    	dbManager.CloseConnection();
    	System.out.println("Database connection closed for Application.");
    	
    }
}
