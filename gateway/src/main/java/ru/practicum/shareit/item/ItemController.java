package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemClient itemClient;
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                          @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                          @Positive @RequestParam(name = "size", defaultValue = "100") Integer size) {
        return itemClient.findAll(userId, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@Validated({Create.class}) @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemClient.createItem(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable Long id, @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemClient.updateItem(id, itemDto, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable Long itemId, @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam(name = "text") String text,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "100") Integer size) {
        return text.isBlank() ? ResponseEntity.ok(Collections.emptyList()) : itemClient.searchItems(text, from, size);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> removeItem(@PathVariable Long itemId) {
        return itemClient.removeItem(itemId);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,
                                                @RequestHeader(X_SHARER_USER_ID) Long userId,
                                                @PathVariable Long itemId) {
        return itemClient.createComment(commentDto, userId, itemId);
    }
}
