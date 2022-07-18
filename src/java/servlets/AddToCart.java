/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.connection.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GraphX
 */
public class AddToCart extends HttpServlet {
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = 0;

        //Getting all the parameters from the user
        int productId = Integer.parseInt(request.getParameter("productId"));
        String price = request.getParameter("price");
        String mrp_price = request.getParameter("mrp_price");
        HttpSession hs = request.getSession();
        try {
            //If user session is null user have to re-login
            if ((String) hs.getAttribute("name") == null) {
                response.sendRedirect("customer-login.jsp");
                //Inserting cart details to the database
            } else {
                int customerId = (int) hs.getAttribute("id");
                //Querying to the database.
                int addToCart = DatabaseConnection.insertUpdateFromSqlQuery("insert into tblcart values('" + id + "','" + price + "',1,'" + price + "','" + customerId + "','" + productId + "','" + mrp_price + "')");
                if (addToCart > 0) {
                    response.sendRedirect("index.jsp");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
