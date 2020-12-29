package com.vczyh;


import org.junit.Test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

public class ApiTest {

    @Test
    public void test() throws UnknownHostException {
        byte[] ipAddr = new byte[]{127, 0, 0, 1};
//        InetAddress inetAddress = InetAddress.getLocalHost();
        InetAddress inetAddress = InetAddress.getByAddress(ipAddr);
        System.out.println(inetAddress.getCanonicalHostName());
    }
}
