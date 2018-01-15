package servlets;

import dbService.DBException;
import dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

//    private final AccountService accountService;
    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (login == null || pass == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            long userId = dbService.addUser(login, pass);
            resp.getWriter().println("User \"" + dbService.getUserById(userId) + "\" signed up successfully");
        } catch (DBException e) {
            e.printStackTrace();
        }

//        accountService.addNewUser(new UserProfile(login, pass, login));
//        resp.getWriter().println("User \"" + accountService.getUserByLogin(login).getLogin() + "\" signed up successfully");

    }
}
