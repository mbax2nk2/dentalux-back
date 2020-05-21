package kz.dentalux.webapp.controllers;

import java.security.Principal;
import java.util.List;
import kz.dentalux.webapp.dto.UserDto;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    private ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<AppUser> findAll(Principal principal){
        return userService.findAll(principal);
    }

    @GetMapping("/me")
    public UserDto me(Principal principal){
        AppUser appUser = (AppUser) userService.loadUserByUsername(principal.getName());
        return modelMapper.map(appUser, UserDto.class);
    }
}
