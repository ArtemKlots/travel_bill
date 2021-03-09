package com.travelBill.splitBill.core.item;

import com.travelBill.api.core.user.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public ItemService(ItemRepository itemRepository,
                       UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
    }
}
