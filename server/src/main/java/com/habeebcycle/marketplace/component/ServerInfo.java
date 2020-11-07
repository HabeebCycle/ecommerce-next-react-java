package com.habeebcycle.marketplace.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerInfo {

    private static final Logger LOG = LoggerFactory.getLogger(ServerInfo.class);

    private final String port;
    private String serverAddress = null;

    @Autowired
    public ServerInfo(@Value("${server.port:8080}") String port){
        this.port = port;
    }

    public String getServerAddress() {
        if (serverAddress == null) {
            serverAddress = findMyHostname() + "/" + findMyIpAddress() + ":" + port;
        }
        return serverAddress;
    }

    private String findMyHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown host name";
        }
    }

    private String findMyIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown IP address";
        }
    }
}
