package threephone.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import threephone.group.dto.request.ChangeAvatar;
import threephone.group.dto.request.SignIn;
import threephone.group.dto.request.SignUpForm;
import threephone.group.dto.response.JwtResponse;
import threephone.group.dto.response.ResponseMessage;
import threephone.group.model.Role;
import threephone.group.model.RoleName;
import threephone.group.model.User;
import threephone.group.security.jwt.JwtAuthTokenFilter;
import threephone.group.security.jwt.JwtProvider;
import threephone.group.security.userprincipal.UserPrinciple;
import threephone.group.service.role.IRoleService;
import threephone.group.service.user.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("nouser"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("noemail"), HttpStatus.OK);
        }

        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        String avatar = "https://firebasestorage.googleapis.com/v0/b/phulinh-fa18e.appspot.com/o/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg?alt=media&token=89849a59-f434-4921-a638-18795091b66c";
        user.setAvatar(avatar);
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignIn signIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token,userPrinciple.getAvatar(), userPrinciple.getName(), userPrinciple.getAuthorities()));
    }

    @PutMapping("/change/avatar")
    public ResponseEntity<?> changeAvatar(HttpServletRequest httpServletRequest, @Valid @RequestBody ChangeAvatar changeAvatar) {
        String jwt = jwtAuthTokenFilter.getJwt(httpServletRequest);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user;
        try {
            if (changeAvatar.getAvatar() == null || changeAvatar.getAvatar().trim().equals("")) {
                return new ResponseEntity(new ResponseMessage("no"), HttpStatus.OK);
            } else {
                user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Fount with ->" +
                        " username:" + username));
                user.setAvatar(changeAvatar.getAvatar());
                userService.save(user);
            }
            return new ResponseEntity(new ResponseMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new ResponseMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
