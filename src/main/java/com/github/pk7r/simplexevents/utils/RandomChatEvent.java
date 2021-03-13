package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.ChatEventModel;
import com.github.pk7r.simplexevents.service.BolaoService;
import com.github.pk7r.simplexevents.service.LoteriaService;
import com.github.pk7r.simplexevents.service.SpeedchatService;

import java.util.Random;

public class RandomChatEvent {

    private static final ChatEventModel ev = Main.getChatEventModel();

    public static void randomEvent() {
        final Random dice = new Random();
        final int number = dice.nextInt(2);
        switch (number) {
            case 0: {
                if (!ev.isBolaoIniciado()) {
                    BolaoService.run(getRandomInt());
                } else {
                    if (!ev.isLoteriaIniciado()) {
                        LoteriaService.run(getRandomInt());
                    } else {
                        if (!ev.isSpeedChatIniciado()) {
                            SpeedchatService.run(getRandomInt());
                        }
                    }
                }
                break;
            }
            case 1: {
                if (!ev.isLoteriaIniciado()) {
                    LoteriaService.run(getRandomInt());
                } else {
                    if (!ev.isBolaoIniciado()) {
                        BolaoService.run(getRandomInt());
                    } else {
                        if (!ev.isSpeedChatIniciado()) {
                            SpeedchatService.run(getRandomInt());
                        }
                    }
                }
                break;
            }
            case 2: {
                if (!ev.isSpeedChatIniciado()) {
                    SpeedchatService.run(getRandomInt());
                } else {
                    if (!ev.isBolaoIniciado()) {
                        BolaoService.run(getRandomInt());
                    } else {
                        if (!ev.isLoteriaIniciado()) {
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