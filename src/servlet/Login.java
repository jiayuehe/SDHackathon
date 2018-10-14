package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import classes.*;
import database.*;


@WebServlet(name = "Login",urlPatterns ={"/Login"})

public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null) {
            int flag = 0;
            String errorMessage = "";

            if (username.length() == 0) {
                errorMessage += "The username field should not be empty. ";
                flag = -1;
            }

            if (password.length() == 0) {
                errorMessage += "The password field should not be empty. ";
                flag = -1;
            }
            if(!JdbcClass.isUser(username)){
                errorMessage += "Username is not valid!";
                flag = -1;
            }
            if(JdbcClass.LoginSuccess(username, password) == 0){
                errorMessage += "Username and password don't match!";
                flag = -1;
            }else{
                flag = 1;
            }
            if(flag == 1){
                User currentuser = JdbcClass.getUser(username);
                request.getSession().setAttribute("currentuser", currentuser);
                response.sendRedirect("/create.jsp");
                return;
            } else {
                // If flag is not 1, that means there were errors
                System.out.println(username);
                request.setAttribute("error", errorMessage);
            }
        }
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/login.jsp");
        dispatch.forward(request, response);
    }

}
