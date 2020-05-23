package kz.dentalux.webapp.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.SaveUserDtoRes;
import kz.dentalux.webapp.dto.UserDto;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<UserDto> findAll(){
        return userService.findAll()
            .stream()
            .map(appUser -> modelMapper.map(appUser, UserDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/me")
    public UserDto me(Principal principal){
        AppUser appUser = (AppUser) userService.loadUserByUsername(principal.getName());
        return modelMapper.map(appUser, UserDto.class);
    }

    @PutMapping("/{id}/update-status/{enabled}")
    public UserDto updateStatus(@PathVariable Long id, @PathVariable boolean enabled) {
        AppUser user = userService.updateStatus(id, enabled);
        return modelMapper.map(user, UserDto.class);
    }

    @PutMapping("/{id}")
    public SaveUserDtoRes updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        AppUser user = modelMapper.map(userDto, AppUser.class);
        return userService.update(id, user);
    }

    @PostMapping()
    public SaveUserDtoRes createUser(@RequestBody UserDto userDto){
        AppUser user = modelMapper.map(userDto, AppUser.class);
        return userService.create(user);
    }
}
