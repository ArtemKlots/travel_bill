package com.travelBill.splitBill.web;

import com.travelBill.splitBill.core.item.ItemService;
import com.travelBill.splitBill.web.responseDto.AssignDto;
import com.travelBill.splitBill.web.responseDto.ItemDto;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // TODO: provide user id
    @PostMapping(value = "")
    public ItemDto addItem(@RequestBody ItemDto item, @RequestAttribute Long userId) {
        return itemService.save(item, userId);
    }

    @PutMapping(value = "{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId, @RequestBody ItemDto item, @RequestAttribute Long userId) {
        item.setId(itemId);
        return itemService.save(item, userId);
    }

    @DeleteMapping(value = "{itemId}")
    public void deleteItem(@PathVariable Long itemId, @RequestAttribute Long userId) {
        itemService.delete(itemId, userId);
    }

    @PostMapping(value = "{itemId}/assign")
    public AssignDto assign(@PathVariable Long itemId, @RequestBody AssignDto assigning, @RequestAttribute Long userId) {
        return itemService.assign(itemId, assigning, userId);
    }
}
