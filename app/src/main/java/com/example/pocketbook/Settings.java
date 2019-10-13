package com.example.pocketbook;

public class Settings {
    private String heading;

    public Settings(){

    }

    public Settings(String heading) {
        this.heading = heading;
    }
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}

