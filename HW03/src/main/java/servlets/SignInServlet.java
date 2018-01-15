package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

//    private final AccountService accountService;
    private final DBService dbService;

    public SignInServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

//        UserProfile user = accountService.getUserByLogin(login);
//        try {
            UsersDataSet user = dbService.getUserByName(login);
            //        if (user.getLogin().equals(login)){
            if (user.getName().equals(login)){
//            if (user.getPass().equals(password)){
                if (user.getPassword().equals(password)){
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Authorized: " + login);
                }
                else{
                    resp.getWriter().println("Login and Password do not match");
                }
            }
            else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().println("Unauthorized");
            }
//        } catch (DBException e) {
//            e.printStackTrace();
//        }

    }

}
