package servlet;

import database.JdbcClass;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateTrip")
public class CreateTrip extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripName = request.getParameter("tripHandle");
        boolean result = true;
        if (JdbcClass.checkIfTripNameExist(tripName)) {
           result = false;
        } else {
            System.out.println("The name does not exist");
            JdbcClass.createTrip(tripName,"North America");
        }

        if (result) {
            getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        } else{
            System.out.println("We should be here");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/tripRegister.jsp");
            request.getSession().setAttribute("tripName", tripName);
            dispatch.forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
