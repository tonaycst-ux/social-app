package com.example.social.service;

import com.example.social.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.social.repository.UserRepo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PasswordEncoder passwordEncoder;
    public boolean cheekUsername(String uname){
        if (userRepo.findByUsername(uname)!=null){
            return false;
        }else{
            return true;
        }
    }

    public User register(User u){
        System.out.println(u.getName());
        System.out.println(u.getEmail());

        u.setPassword(
                passwordEncoder.encode(u.getPassword())
        );
        return userRepo.save(u);
    }

    public User login(User u){
        System.out.println(u.getUsername());
        User x=userRepo.findByUsername(u.getUsername());

        if(passwordEncoder.matches(
                u.getPassword(),
                x.getPassword()
        )){
            System.out.println("hi");
            return x;
        }else{
            return new User();
        }
    }
    public User updateUser(User u, MultipartFile media) throws Exception {

        User currentuser = userRepo.findByUsername(u.getUsername());

        u.setPassword(currentuser.getPassword());

        if (media != null && !media.isEmpty()) {
            String imgUrl = cloudinaryService.upload(media);
            u.setProfile_pic(imgUrl);
        } else {
            u.setProfile_pic(currentuser.getProfile_pic());
        }

        return userRepo.save(u);
    }
    public User getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }
    public List<User> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        return userRepo.searchUsers(query.trim());
    }

}
