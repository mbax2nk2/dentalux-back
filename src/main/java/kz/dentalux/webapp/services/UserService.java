package kz.dentalux.webapp.services;

import java.util.List;
import kz.dentalux.webapp.dto.ResourceDto;
import kz.dentalux.webapp.dto.SaveUserDtoRes;
import kz.dentalux.webapp.dto.UserDto;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService extends AbstractAuthService implements UserDetailsService {

    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private DoctortService doctortService;
    private ModelMapper modelMapper;

    public UserService(UserRepository repository,
        PasswordEncoder passwordEncoder, DoctortService doctortService,
        ModelMapper modelMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.doctortService = doctortService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findAppUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public List<AppUser> findAll() {
        AppUser user = super.loggedInUser()
            .orElseThrow(() -> new IllegalStateException("unauthorised"));
        return repository.findAllByCompany_id(user.getCompany().getId());
    }

    public AppUser updateStatus(Long id, boolean enabled) {
        AppUser user = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("user not found"));
        user.setEnabled(enabled);
        return repository.save(user);
    }

    public SaveUserDtoRes update(Long id, AppUser user) {
        AppUser found = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("user not found"));
        found.setEnabled(user.isEnabled());
        found.setFirstName(user.getFirstName());
        found.setLastName(user.getLastName());
        found.setPatronymic(user.getPatronymic());
        found.setLivingAddress(user.getLivingAddress());
        found.setMobilePhone(user.getMobilePhone());
        found.setPhone(user.getPhone());
        found.setEmail(user.getEmail());
        found.setBirthDate(user.getBirthDate());
        found.setGender(user.getGender());
        found.setEventColor(user.getEventColor());
        found.setProfessionId(user.getProfessionId());
        found.setCanGiveDiscount(user.getCanGiveDiscount());
        if(!StringUtils.isEmpty(user.getPassword())) {
            found.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        AppUser saved = repository.save(found);
        ResourceDto resourceDto = doctortService.saveDoctor(saved);
        UserDto userDto = modelMapper.map(saved, UserDto.class);
        return new SaveUserDtoRes(userDto, resourceDto);
    }

    public SaveUserDtoRes create(AppUser user) {
        AppUser loggedInUser = super.loggedInUser()
            .orElseThrow(() -> new IllegalStateException("unauthorised"));

        if(StringUtils.isEmpty(user.getPassword())) {
            throw new IllegalStateException("password does not provided");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCompany(loggedInUser.getCompany());
        AppUser saved = repository.save(user);
        if(saved.getProfessionId() != 2) {
            ResourceDto resourceDto = doctortService.saveDoctor(saved);
            UserDto userDto = modelMapper.map(saved, UserDto.class);
            return new SaveUserDtoRes(userDto, resourceDto);
        }
        UserDto userDto = modelMapper.map(saved, UserDto.class);
        return new SaveUserDtoRes(userDto, null);
    }

    public Long findUserId(String username) {
        AppUser found = repository.findAppUserByUsername(username)
            .orElseThrow(() -> new IllegalStateException("user not found"));
        return found.getId();
    }
}
