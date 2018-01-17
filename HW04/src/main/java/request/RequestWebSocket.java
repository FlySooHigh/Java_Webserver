package request;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class RequestWebSocket {

    private Session session;

    public RequestWebSocket() {
    }

    @OnWebSocketConnect
    public void onOpen(Session session){
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String message){
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
