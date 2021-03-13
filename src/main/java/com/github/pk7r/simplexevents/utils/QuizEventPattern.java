package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class QuizEventPattern {

    public static void setDefault() {
        Main.getQuizEventModel().setAberto(false);
        Main.getQuizEventModel().setResposta("");
        Main.getQuizEventModel().setPergunta("");
        Main.getQuizEventModel().setPremioItem(new ItemStack(Material.AIR));
        Main.getQuizEventModel().setPremioMoney(0);
    }
}