package com.smartfitai;

import com.kumuluz.ee.EeApplication;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
@OpenAPIDefinition(
    info = @Info(
        title = "Workout Service API",
        version = "1.0.0",
        description = "API for workout plans, exercise logging, and session tracking in SmartFit AI application."
    ),
    servers = {
        @Server(url = "http://4.232.72.237:8082/", description = "Production server"),
        @Server(url = "http://localhost:8082/", description = "Local development server")
    }
)
public class WorkoutApplication extends Application {
    
    public static void main(String[] args) {
        EeApplication.main(args);
    }
}
