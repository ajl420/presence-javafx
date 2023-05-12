package mg.disturb.Presence.Networking;

import mg.disturb.Presence.Utils.Validation;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class PresenceServer extends ServerSocket {
    private static PresenceServer presenceServer;
    private PresenceServer() throws IOException {
        super(6789,1, getAddress());
//        super(5789);
    }

    public static PresenceServer getInstance() throws IOException {
        if(presenceServer == null){
            presenceServer = new PresenceServer();
        }
        return presenceServer;
    }

    public static InetAddress getAddress() throws SocketException, UnknownHostException {
        InetAddress chosenAddr = InetAddress.getLocalHost();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface interfaceN = interfaces.nextElement();
            List<InterfaceAddress> addressList = interfaceN.getInterfaceAddresses();
            for (InterfaceAddress addr: addressList) {
                if (Validation.isLocalIp(addr.getAddress().getHostAddress())){
                    chosenAddr = addr.getAddress();
                }
            }
        }

        InetAddress chosenAddr1 = chosenAddr;
        return chosenAddr1;
    }
}
