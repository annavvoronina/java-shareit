package ru.practicum.shareit.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {
    public static ItemRequestResponseDto toItemRequestResponseDto(ItemRequest itemRequest) {
        return new ItemRequestResponseDto(itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated(),
                new ArrayList<>());
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User user) {
        return new ItemRequest(itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                user,
                LocalDateTime.now());
    }
}
