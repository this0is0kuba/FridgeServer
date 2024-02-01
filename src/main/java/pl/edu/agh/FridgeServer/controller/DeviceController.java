package pl.edu.agh.FridgeServer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.User;
import pl.edu.agh.FridgeServer.model.SimpleMessage;
import pl.edu.agh.FridgeServer.service.FridgeService;

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
    public ResponseEntity<SimpleMessage> addDeviceToUser(
            @PathVariable String number,
            @RequestBody SimpleMessage deviceInfo
    ) {
        Device device = fridgeService.findDeviceById(number);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        if(device != null && !device.getUser().getUserName().equals(userName))
            return ResponseEntity.status(403).body(null);

        User user = fridgeService.findUserByUserName(userName);

        Device newDevice = new Device(number, deviceInfo.getInfo());
        newDevice.setUser(user);

        fridgeService.saveDevice(newDevice);
        logger.info("Successfully created Device with number: " + newDevice.getId());

        return ResponseEntity.status(200).body(
                new SimpleMessage(("Successfully created device: " + deviceInfo.getInfo()))
        );
    }

    @DeleteMapping("/deleteDevice/{number}")
    public ResponseEntity<SimpleMessage> deleteDevice(
            @PathVariable String number
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = fridgeService.findUserByUserName(userName);

        boolean checkPermission = false;

        for (Device device: user.getDevices()) {

            if(device.getId().equals(number)) {
                checkPermission = true;
                break;
            }
        }

        if(!checkPermission)
            return ResponseEntity.status(403).body(null);

        fridgeService.deleteDevice(number);
        return ResponseEntity.status(200).body(new SimpleMessage("Device deleted successfully"));
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user =  fridgeService.findUserByUserName(userName);

        return ResponseEntity.status(200).body(user.getDevices());
    }
}
