package mg.disturb.Presence.Service;

import mg.disturb.Presence.Networking.PresenceServer;
import mg.disturb.Presence.Networking.ServerCallBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class PresenceRemoteService {
    private static PresenceServer server;
    private static boolean serverIsActive = false;

    public static void startServer(ServerCallBack serverCallBack) throws IOException {
        server = PresenceServer.getInstance();
        serverIsActive = true;
        String addr = PresenceServer.getAddress().getHostAddress();
        serverCallBack.run(addr);
    }

    public static void killServer() throws IOException {
        server.close();
        serverIsActive = false;
    }

    public static PresenceServer getServer() {
        return server;
    }

    public static void listenToMessage(ServerCallBack serverCallBack) throws IOException {
        while (serverIsActive){
            Socket socket = getServer().accept();
            System.out.println("Connected to new device...");
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            do {
                line = reader.readLine();
                System.out.println("New mwssage comming...");
                serverCallBack.run(line);
            } while (!line.equals("close"));
        }
    }
}
