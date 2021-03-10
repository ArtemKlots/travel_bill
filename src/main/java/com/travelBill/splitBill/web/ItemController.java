package com.travelBill.splitBill.web;

import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("items")
public class ItemController {
    private final ItemWebService itemWebService;

    public ItemController(ItemWebService itemWebService) {
        this.itemWebService = itemWebService;
    }

    // TODO: provide user id
    @PostMapping(value = "")
    public ItemDto addItem(@RequestBody ItemDto item, @RequestAttribute Long userId) {
        return itemWebService.save(item, userId);
    }

    @PutMapping(value = "{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId, @RequestBody ItemDto item, @RequestAttribute Long userId) {
        item.setId(itemId);
        return itemWebService.save(item, userId);
    }

    @DeleteMapping(value = "{itemId}")
    public void deleteItem(@PathVariable Long itemId, @RequestAttribute Long userId) {
        itemWebService.delete(itemId, userId);
    }

    @PostMapping(value = "{itemId}/assign")
    public AssignDto assign(@PathVariable Long itemId, @RequestBody AssignDto assigning, @RequestAttribute Long userId) {
        return itemWebService.assign(itemId, assigning, userId);
    }
}
