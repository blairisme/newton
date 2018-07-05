package rest;


import helpers.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import security.SandboxSecurityPolicy;

import java.net.InetAddress;
import java.security.Policy;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Policy.setPolicy(new SandboxSecurityPolicy());
        System.setSecurityManager(new SecurityManager());

        SpringApplication.run(Application.class, args);

        try {
            Constants.IP = InetAddress.getLocalHost().getHostAddress();
            //   Azure VM IP
            //   Constants.IP = "51.140.167.6";
            Constants.SERVER_URL =
                    "http://".concat(Constants.IP)
                    .concat(":")
                    .concat(Constants.PORT)
                    .concat("/");
            System.out.println("URL=" + Constants.SERVER_URL);

        }catch (Exception ignore){
        }
    }


}