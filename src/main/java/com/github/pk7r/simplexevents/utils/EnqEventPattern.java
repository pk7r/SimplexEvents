package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;

public class EnqEventPattern {

    public static void setDefault() {
        Main.getEnqEventModel().setTask(null);
        Main.getEnqEventModel().getNo().clear();
        Main.getEnqEventModel().getYes().clear();
        Main.getEnqEventModel().setEnquete("");
        Main.getEnqEventModel().setAberto(false);
    }

}
