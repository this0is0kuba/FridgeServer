package pl.edu.agh.FridgeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/successfulAuthentication")
    public ResponseEntity<SimpleMessage> showSuccessInfo() {

        return ResponseEntity.status(200).body(new SimpleMessage("Successful Authentication"));
    }

    @GetMapping("/unsuccessfulAuthentication")
    public ResponseEntity<SimpleMessage> showFailureInfo() {

        return ResponseEntity.status(401).body(new SimpleMessage("Unsuccessful Authentication"));
    }

    @GetMapping("/defaultPage")
    public ResponseEntity<SimpleMessage> sendDefaultMessage(
            @RequestParam(value = "logout", defaultValue = "false") boolean logout
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication.getAuthorities().stream().anyMatch(role ->
                role.getAuthority().equals("ROLE_USER")
        );

        if(isAuthenticated)
            return ResponseEntity.status(200).body(new SimpleMessage("Default Page"));

        if(logout)
            return ResponseEntity.status(200).body(new SimpleMessage("You have logged out successfully."));

        return ResponseEntity.status(401).body(new SimpleMessage("You are not authenticated to access this resource."));
    }

    @GetMapping("/userInfo")
    public ResponseEntity<User> getCurrentUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User currentUser = fridgeService.findUserByUserName(userName);

        return ResponseEntity.status(200).body(currentUser);
    }
}
