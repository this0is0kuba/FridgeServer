package pl.edu.agh.FridgeServer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.User;
import pl.edu.agh.FridgeServer.model.SimpleMessage;
import pl.edu.agh.FridgeServer.service.FridgeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class DeviceController {


    private Logger logger = Logger.getLogger(getClass().getName());
    private FridgeService fridgeService;


    DeviceController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }


    @PostMapping("/addDevice/{number}")
    public ResponseEntity<SimpleMessage> addDeviceToUser(@PathVariable String number) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = fridgeService.findUserByUserName(userName);

        Device device = new Device(number, number);
        device.setUser(user);

        fridgeService.saveDevice(device);
        logger.info("Successfully created Device with number: " + device.getId());

        return ResponseEntity.status(200).body(new SimpleMessage(("Successfully created device")));
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user =  fridgeService.findUserByUserName(userName);

        return ResponseEntity.status(200).body(user.getDevices());
    }
}
