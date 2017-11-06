package com.box;

import com.box.sdk.BoxConfig;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import com.box.sdk.BoxUser;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Playground {

  @RequestMapping("/")
  String home() {
    Path configPath = Paths.get("./app/config.json");
    try (BufferedReader reader = Files.newBufferedReader(configPath, Charset.forName("UTF-8"))) {
      BoxConfig boxConfig = BoxConfig.readFrom(reader);
      BoxDeveloperEditionAPIConnection serviceAccountClient = BoxDeveloperEditionAPIConnection
          .getAppEnterpriseConnection(boxConfig);
      BoxUser serviceAccountUser = BoxUser.getCurrentUser(serviceAccountClient);
      BoxUser.Info serviceAccountUserInfo = serviceAccountUser.getInfo();
      System.out.println(serviceAccountUserInfo.getLogin());
      return serviceAccountUserInfo.getName();
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return e.getLocalizedMessage();
    }
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Playground.class, args);
  }
}