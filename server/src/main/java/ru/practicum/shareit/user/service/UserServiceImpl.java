package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toUser(new User(), userDto);
        User createdUser = userRepository.save(user);
        return UserMapper.toUserDto(createdUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден" + id));
        if (user == null || !id.equals(user.getId())) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        UserMapper.toUser(user, userDto);
        user.setId(id);
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        return UserMapper.toUserDto(user.get());
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        existUserById(id);
        userRepository.deleteById(id);
    }

    private void existUserById(Long id) {
        getUserById(id);
    }
}
