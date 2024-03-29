package ru.practicum.shareit.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemResponseDto> findAll(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from, @RequestParam(name = "size", defaultValue = "100") Integer size) {
        return itemService.getItemsByUserId(userId, from, size);
    }

    @PostMapping
    public ItemResponseDto createItem(@RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto updateItem(@RequestBody ItemDto itemDto, @PathVariable Long itemId, @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.updateItem(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto getItem(@PathVariable Long itemId, @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemResponseDto> searchItems(@RequestParam(name = "text") String text,
                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @RequestParam(name = "size", defaultValue = "100") Integer size) {
        return itemService.searchItems(text, from, size);
    }

    @DeleteMapping("/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        itemService.removeItemById(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto createComment(@RequestBody CommentDto commentDto,
                                            @RequestHeader(X_SHARER_USER_ID) Long userId,
                                            @PathVariable Long itemId) {
        return itemService.createComment(commentDto, userId, itemId);
    }
}
