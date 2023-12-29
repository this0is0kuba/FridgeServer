package pl.edu.agh.FridgeServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String getHomeInfo() {

        return "Home Page";
    }
}
