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
    public ItemDto addItem(@RequestBody ItemDto item) {
        return itemWebService.save(item, 1L);
    }

    @PutMapping(value = "{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId, @RequestBody ItemDto item) {
        item.setId(itemId);
        return itemWebService.save(item, 1L);
    }

    @DeleteMapping(value = "{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemWebService.delete(itemId, 1L);
    }
}
