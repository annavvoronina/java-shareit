package ru.practicum.shareit.user;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    UserServiceImpl userService;
    UserDto userDto;
    User user;

    @BeforeEach
    void beforeEach() {
        userService = new UserServiceImpl(userRepository);
        user = new User(1L, "user", "user@email.ru");
        userDto = new UserDto(1L, "user", "user@email.ru");
    }

    @Order(1)
    @Test
    void createUserTest() {
        when(userRepository.save(any())).thenReturn(user);
        UserDto userDto1 = userService.createUser(userDto);
        assertEquals(userDto.getId(), userDto1.getId());
        assertEquals(userDto.getEmail(), userDto1.getEmail());
        assertEquals(userDto.getName(), userDto1.getName());
    }

    @Order(2)
    @Test
    void updateUserTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UserDto userDto1 = userService.updateUser(1L, userDto);
        assertEquals(userDto.getId(), userDto1.getId());
        assertEquals(userDto.getEmail(), userDto1.getEmail());
        assertEquals(userDto.getName(), userDto1.getName());
        UserDto userDto2 = new UserDto(7L, "userUpdate", "userUpdate@email.ru");
        assertThrows(ObjectNotFoundException.class, () -> userService.updateUser(7L, userDto2));
    }

    @Order(3)
    @Test
    void getUserByIdTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UserDto user1 = userService.getUserById(1L);
        assertEquals(user1.getId(), user.getId());
        assertEquals(user1.getEmail(), user.getEmail());
        assertEquals(user1.getName(), user.getName());
    }

    @Order(4)
    @Test
    void getUserByIdNotExistsTest() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> userService.getUserById(6L));
    }

    @Order(5)
    @Test
    void findAllTest() {
        List<User> users = new ArrayList<>(Collections.singletonList(user));
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> usersList = userService.findAll();
        assertEquals(usersList.size(), 1);
    }

    @Order(6)
    @Test
    void removeUserByIdTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.removeUserById(user.getId());
        assertThrows(ObjectNotFoundException.class, () -> userService.removeUserById(7L));
    }

    @Order(7)
    @Test
    void removeUserByIdFailIdTest() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> userService.removeUserById(7L));
    }
}
