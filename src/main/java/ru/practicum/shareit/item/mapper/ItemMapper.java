package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null);
    }

    public static ItemResponseDto toItemResponseDto(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                null,
                null,
                new ArrayList<>(),
                item.getRequest() != null ? item.getRequest().getId() : null);
    }

    public static Item toItem(Item item, ItemDto itemDto) {
        item.setId(itemDto.getId());
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getAvailable()).ifPresent(item::setAvailable);
        Optional.ofNullable(itemDto.getDescription()).ifPresent(item::setDescription);
        return item;
    }

    public static ItemResponseDto toMap(Item item, List<CommentResponseDto> comments, Booking lastBooking, Booking nextBooking) {
        BookingDto nextBookingDto = null;
        BookingDto lastBookingDto = null;
        if (lastBooking != null) {
            lastBookingDto = new BookingDto(lastBooking.getId(), lastBooking.getStart(), lastBooking.getEnd(), lastBooking.getItem().getId(), lastBooking.getBooker().getId());
        }
        if (nextBooking != null) {
            nextBookingDto = new BookingDto(nextBooking.getId(), nextBooking.getStart(), nextBooking.getEnd(), nextBooking.getItem().getId(), nextBooking.getBooker().getId());
        }
        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBookingDto,
                nextBookingDto,
                comments,
                item.getRequest() != null ? item.getRequest().getId() : null);
    }
}
