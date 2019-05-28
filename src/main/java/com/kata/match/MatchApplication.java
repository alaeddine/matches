package com.kata.match;

import static com.kata.match.ApplicationInfo.APP_CONTEXT_PATH;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@ApplicationPath(APP_CONTEXT_PATH)
public class MatchApplication extends Application implements ApplicationInfo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MatchApplication.class).run(args);
    }
}
