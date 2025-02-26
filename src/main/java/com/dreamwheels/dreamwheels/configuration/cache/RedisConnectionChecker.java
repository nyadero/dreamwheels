package com.dreamwheels.dreamwheels.configuration.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionChecker {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

//    public RedisConnectionChecker(RedisConnectionFactory redisConnectionFactory) {
//        this.redisConnectionFactory = redisConnectionFactory;
//    }

    @Bean
    public CommandLineRunner checkRedisConnection() {
        return args -> {
            boolean isConnected = checkConnection();
            if (isConnected) {
                System.out.println("Connected to Redis successfully!");
            } else {
                System.err.println("Failed to connect to Redis.");
            }
        };
    }

    private boolean checkConnection(){
        try{
            var connection = redisConnectionFactory.getConnection();
            System.out.println(connection.ping());
            // Sends a PING command to test the connection
            connection.ping();
            return true;
        }catch (Exception e){
//            log.error("e: ", e);
            return false;
        }
    }

}
