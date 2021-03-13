package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.EnqEventModel;

public class EnqEventPattern {

    private static final EnqEventModel ev = Main.getEnqEventModel();

    public static void setDefault() {
        ev.setTask(null);
        ev.getNo().clear();
        ev.getYes().clear();
        ev.setEnquete("");
        ev.setAberto(false);
    }

}
