/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Web application lifecycle listener.
 *
 * @author GraphX
 */
public class MyServletRequestListener implements ServletRequestListener {

   public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    	ServletRequest servletRequest = servletRequestEvent.getServletRequest();
    	System.out.println("ServletRequest destroyed. Remote IP="+servletRequest.getRemoteAddr());
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    	ServletRequest servletRequest = servletRequestEvent.getServletRequest();
    	System.out.println("ServletRequest initialized. Remote IP="+servletRequest.getRemoteAddr());
    }
}
