package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.*;
import database.*;


@WebServlet(name = "Signup",urlPatterns ={"/Signup"})
public class Signup extends javax.servlet.http.HttpServlet{
    private static final long serialVersionUID = 1L;
    protected void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            int flag = 1;
            String errorMessage = "";
            if(username.length() == 0){
                errorMessage += "The username field should not be empty. ";
                flag = -1;
            }
            if(password.length() == 0){
                errorMessage += "The password field should not be empty. ";
                flag = -1;
            }
            if(JdbcClass.isUser(username)){
                errorMessage += "This username has been used!";
                flag = -1;
            }
            if(flag == 1){
                JdbcClass.registerUser(username, password);
                User currentuser = JdbcClass.getUser(username);
                request.getSession().setAttribute("currentuser", currentuser);
                response.sendRedirect("/profile.jsp");
                return;
            }else {
                // If flag is -1, that means there were errors
                request.setAttribute("error", errorMessage);
            }
        }
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/signup.jsp");
        dispatch.forward(request, response);
    }
}

