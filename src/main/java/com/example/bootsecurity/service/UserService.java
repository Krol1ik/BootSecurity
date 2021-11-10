package com.example.bootsecurity.service;

import com.example.bootsecurity.model.Role;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser (User user){
        User userFromBD = userRepository.findByUsername(user.getUsername());
        if(userFromBD != null){
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));   //зашифровали пароль пользователя

        userRepository.save(user);

        sendMessag(user);
        return true;
    }

    private void sendMessag(User user) {
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to my site. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(),"Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if(user == null){   //если пользователь не будет найден по коду, то активация не удалась
            return false;
        }

        user.setActivationCode(null);  //означает, что пользователь подвердил активацию
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())        //Получаем список ролей, чтобы проверить, что они установленны данному пользователю
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {  // проверяем, что наша форма содержит роли для нашего пользователя
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userPassword = user.getPassword();

        boolean isPasswordChanged = (password != null && !password.equals(userPassword)
                || password !=null && !userPassword.equals(email));

        if (!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));

            if (!StringUtils.isEmpty(password)){   //если пользователь установил пароль
                user.setActivationCode(UUID.randomUUID().toString());   //то мы присваеваем новый код активации
            }
        }
        user.setActive(false);
        userRepository.save(user);
        if(isPasswordChanged){
            sendMessag(user);
        }
    }
}
