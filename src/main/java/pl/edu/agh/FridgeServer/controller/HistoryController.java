package pl.edu.agh.FridgeServer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.History;
import pl.edu.agh.FridgeServer.entity.User;
import pl.edu.agh.FridgeServer.service.FridgeService;

import java.util.List;

@RestController
public class HistoryController {

    private FridgeService fridgeService;

    HistoryController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping("/deviceHistory/{deviceId}")
    public ResponseEntity<List<History>> getDeviceHistory(@PathVariable String deviceId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = fridgeService.findUserByUserName(userName);

        boolean checkPermission = false;

        for (Device device: user.getDevices()) {

            if(device.getId().equals(deviceId)) {
                checkPermission = true;
                break;
            }
        }

        if(!checkPermission)
            return ResponseEntity.status(403).body(null);

        Device device = fridgeService.findDeviceById(deviceId);
        return ResponseEntity.status(200).body(device.getHistory());
    }
}
