package com.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class Main implements ApplicationListener<ApplicationReadyEvent> {
  private static final String CLEAR = "\u001b[H\u001b[2J";

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    try {
      // Only attempt if a console is present; some environments return null
      if (System.console() != null) {
        System.out.print(CLEAR);
        System.out.flush();
      }
    } catch (Exception ignored) {
      // best-effort; don't break startup if clearing fails
    }
  }
}
