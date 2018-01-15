package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserProfile user = accountService.getUserByLogin(login);

        if (user.getLogin().equals(login)){
            if (user.getPass().equals(password)){
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


    }

}
