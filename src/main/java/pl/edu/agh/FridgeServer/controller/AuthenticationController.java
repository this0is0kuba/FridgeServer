package pl.edu.agh.FridgeServer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.edu.agh.FridgeServer.entity.User;
import pl.edu.agh.FridgeServer.model.CustomResponseBody;
import pl.edu.agh.FridgeServer.model.SimpleMessage;
import pl.edu.agh.FridgeServer.service.FridgeService;

import java.util.Date;

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
    public ResponseEntity<CustomResponseBody> checkToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        int userId = fridgeService.findUserByUserName(userName).getId();


        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        Date startTime = new Date(session.getCreationTime());
        Date endTime = new Date (System.currentTimeMillis() + session.getMaxInactiveInterval() * 1000L);

        return ResponseEntity.status(200).body(new CustomResponseBody(userId, startTime, endTime, userName));
    }
}
