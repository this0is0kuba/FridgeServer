package pl.edu.agh.FridgeServer.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.FridgeServer.dao.DeviceDao;
import pl.edu.agh.FridgeServer.dao.HistoryDao;
import pl.edu.agh.FridgeServer.dao.RoleDao;
import pl.edu.agh.FridgeServer.dao.UserDao;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.History;
import pl.edu.agh.FridgeServer.entity.Role;
import pl.edu.agh.FridgeServer.entity.User;

import java.util.*;

@Service
public class FridgeServiceImpl implements FridgeService {

    private UserDao userDao;
    private RoleDao roleDao;
    private DeviceDao deviceDao;
    private HistoryDao historyDao;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    FridgeServiceImpl(UserDao userDAO, RoleDao roleDao, DeviceDao deviceDao, HistoryDao historyDao,
                      BCryptPasswordEncoder passwordEncoder) {

        this.userDao = userDAO;
        this.roleDao = roleDao;
        this.deviceDao = deviceDao;
        this.historyDao = historyDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByUserName(String userName) {
        return userDao.findByName(userName);
    }

    @Override
    public Device findDeviceById(String id) {
        return deviceDao.findById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role role = roleDao.findByName("ROLE_USER");

        user.setPassword(encodedPassword);
        user.setRoles(Collections.singletonList(role));

        userDao.save(user);
    }

    @Override
    @Transactional
    public void saveDevice(Device device) {
        deviceDao.save(device);
    }

    @Override
    @Transactional
    public void saveHistory(History history) {
        historyDao.save(history);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByName(username);

        if(user == null)
            throw new UsernameNotFoundException("There is no user with name: " + username);

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles) {

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            authorities.add(authority);
        }

        return authorities;
    }
}
