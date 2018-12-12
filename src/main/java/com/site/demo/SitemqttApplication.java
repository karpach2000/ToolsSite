package com.site.demo;

import com.site.demo.mqtt.Mqtt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SitemqttApplication {

    public static void main(String[] args) {
        Mqtt mqtt =new  Mqtt();
        Thread thread = new Thread(new  Runnable() {
            @Override
            public void run() {
                mqtt.start();
            }});
        thread.start();
        SpringApplication.run(SitemqttApplication.class, args);
    }
}
