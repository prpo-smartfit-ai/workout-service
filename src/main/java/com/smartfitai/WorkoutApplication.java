package com.smartfitai;

import com.kumuluz.ee.EeApplication;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
public class WorkoutApplication extends Application {
    
    public static void main(String[] args) {
        EeApplication.main(args);
    }
}
