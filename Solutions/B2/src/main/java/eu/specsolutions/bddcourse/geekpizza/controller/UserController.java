package eu.specsolutions.bddcourse.geekpizza.controller;

import eu.specsolutions.bddcourse.geekpizza.dto.RegisterRequestDto;
import eu.specsolutions.bddcourse.geekpizza.model.User;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping
    public User register(@RequestBody RegisterRequestDto registerRequestDto){
        if (ObjectUtils.isEmpty(registerRequestDto.getUserName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name must be provided");

        if (ObjectUtils.isEmpty(registerRequestDto.getPassword()) ||
                ObjectUtils.isEmpty(registerRequestDto.getPasswordReEnter()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and password re-enter must be provided");

        if (!registerRequestDto.getPassword().equals(registerRequestDto.getPasswordReEnter()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Re-entered password is different");

        if (registerRequestDto.getPassword().length() < 4)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters long");

        GeekPizzaRepository repository = new GeekPizzaRepository();
        User existingUser = repository.findUserByName(registerRequestDto.getUserName());
        if (existingUser != null)
            repository.getUsers().remove(existingUser);

        User user = new User(registerRequestDto.getUserName(), registerRequestDto.getPassword());

        repository.getUsers().add(user);
        repository.saveChanges();

        return user;
    }

}
