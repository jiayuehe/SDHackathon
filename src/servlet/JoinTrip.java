package servlet;

import database.JdbcClass;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/JoinTrip")
public class JoinTrip extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripName = request.getParameter("tripHandle");
        boolean result = false;
        if (JdbcClass.checkIfTripNameExist(tripName)) {
            result = true;
        }

        if (result) {
            getServletContext().getRequestDispatcher("/tripRegister.jsp").forward(request, response);
        }
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/home.jsp");
        request.getSession().setAttribute("tripName", tripName);
        dispatch.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
