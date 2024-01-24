package pl.edu.agh.FridgeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import pl.edu.agh.FridgeServer.entity.User;
import pl.edu.agh.FridgeServer.model.SimpleMessage;
import pl.edu.agh.FridgeServer.service.FridgeService;

@RestController
public class AuthenticationController {

    private FridgeService fridgeService;

    @Autowired
    AuthenticationController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<User> getCurrentUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User currentUser = fridgeService.findUserByUserName(userName);

        return ResponseEntity.status(200).body(currentUser);
    }

    @GetMapping("/session")
    public ResponseEntity<SimpleMessage> checkToken() {

        return ResponseEntity.status(200).body(new SimpleMessage("Token is valid"));
    }
}
