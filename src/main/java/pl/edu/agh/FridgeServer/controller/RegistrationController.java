package pl.edu.agh.FridgeServer.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.FridgeServer.entity.User;
import pl.edu.agh.FridgeServer.model.SimpleMessage;
import pl.edu.agh.FridgeServer.service.FridgeService;

import java.util.logging.Logger;

@RestController
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private FridgeService fridgeService;

    @Autowired
    RegistrationController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/processRegistrationForm")
    public ResponseEntity<SimpleMessage> processRegistrationForm(
            @Valid @RequestBody User user,
            BindingResult bindingResult
            ) {

        if(bindingResult.hasErrors())
            return ResponseEntity.status(400).body(new SimpleMessage("Invalid Parameters"));

        User existingUser = fridgeService.findUserByUserName(user.getUserName());

        if(existingUser != null)
            return ResponseEntity.status(400).body(new SimpleMessage("User with this name already exists"));

        fridgeService.saveUser(user);

        logger.info("Successfully created user: " + user.getUserName());
        return ResponseEntity.status(201).body(new SimpleMessage("The user has been created correctly"));
    }
}
