package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.service.BolaoService;
import com.github.pk7r.simplexevents.service.LoteriaService;
import com.github.pk7r.simplexevents.service.SpeedchatService;

import java.util.Random;

public class RandomChatEvent {

    public static void randomEvent() {
        final Random dice = new Random();
        final int number = dice.nextInt(2);
        switch (number) {
            case 0: {
                if (!Main.getChatEventModel().isBolaoIniciado()) {
                    BolaoService.run(getRandomInt());
                } else {
                    if (!Main.getChatEventModel().isLoteriaIniciado()) {
                        LoteriaService.run(getRandomInt());
                    } else {
                        if (!Main.getChatEventModel().isSpeedChatIniciado()) {
                            SpeedchatService.run(getRandomInt());
                        }
                    }
                }
                break;
            }
            case 1: {
                if (!Main.getChatEventModel().isLoteriaIniciado()) {
                    LoteriaService.run(getRandomInt());
                } else {
                    if (!Main.getChatEventModel().isBolaoIniciado()) {
                        BolaoService.run(getRandomInt());
                    } else {
                        if (!Main.getChatEventModel().isSpeedChatIniciado()) {
                            SpeedchatService.run(getRandomInt());
                        }
                    }
                }
                break;
            }
            case 2: {
                if (!Main.getChatEventModel().isSpeedChatIniciado()) {
                    SpeedchatService.run(getRandomInt());
                } else {
                    if (!Main.getChatEventModel().isBolaoIniciado()) {
                        BolaoService.run(getRandomInt());
                    } else {
                        if (!Main.getChatEventModel().isLoteriaIniciado()) {
                            LoteriaService.run(getRandomInt());
                        }
                    }
                }
                break;
            }
        }
    }

    public static Integer getRandomInt() {
        Random ran = new Random();
        return ran.nextInt(5000);
    }
}