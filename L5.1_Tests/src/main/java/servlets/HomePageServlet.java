package servlets;

import accountServer.AccountServerI;
import example.ReadXMLFileSAX;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resourceServer.ResourceServer;
import resources.TestResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author a.akbashev
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class HomePageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(HomePageServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private final AccountServerI accountServer;
    private final ResourceServer resourceServer;

    public HomePageServlet(AccountServerI accountServer, ResourceServer resourceServer) {
        this.accountServer = accountServer;
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathToResource = req.getParameter("path");
        TestResource xmlResource = (TestResource) ReadXMLFileSAX.readXML(pathToResource);
        resourceServer.setResource(xmlResource);
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        String remove = request.getParameter("remove");

        if (remove != null) {
            accountServer.removeUser();
            response.getWriter().println("Hasta la vista!");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        int limit = accountServer.getUsersLimit();
        int count = accountServer.getUsersCount();

        logger.info("Limit: {}. Count {}", limit, count);

        if (limit > count) {
            logger.info("User pass");
            accountServer.addNewUser();
//            response.getWriter().println("Hello, world!");
            response.getWriter().println(limit);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.info("User were rejected");
            response.getWriter().println("Server is closed for maintenance!");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
