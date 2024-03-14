package mg.disturb.Presence.Networking;

import com.sun.net.httpserver.HttpServer;
import mg.disturb.Presence.Utils.Validation;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class PresenceServer {

    public static HttpServer startServer() throws IOException {
        return HttpServer.create(convertToSocketAddr(getAddress()),0);
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

        return chosenAddr;
    }

    public static InetSocketAddress convertToSocketAddr(InetAddress inetAddress){
        return new InetSocketAddress(inetAddress,6789);
    }
}
