package request;


import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "WebSocketRequestServlet", urlPatterns = {"/chat"})
public class WebSocketRequestServlet extends WebSocketServlet {
    public WebSocketRequestServlet() {
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator((req, resp) -> new RequestWebSocket());
    }
}
