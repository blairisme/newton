package rest;


import helpers.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
     //   SpringApplication.addListeners(new EngineApplicationListener());

        try {
            Constants.IP = InetAddress.getLocalHost().getHostAddress();
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