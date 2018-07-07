package rest;


import helpers.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import security.SandboxSecurityPolicy;

import java.net.InetAddress;
import java.security.Policy;
import java.util.concurrent.Executor;

@EnableAsync
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

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ExperimentExecutor-");
        executor.initialize();
        return executor;
    }
}