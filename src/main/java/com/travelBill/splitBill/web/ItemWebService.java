package com.travelBill.splitBill.web;

import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.item.Item;
import com.travelBill.splitBill.core.item.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemWebService {
    private final ItemService itemService;
    private final BillWebService billWebService;
    private final ModelMapper modelMapper;


    public ItemWebService(ItemService itemService, BillWebService billWebService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.billWebService = billWebService;
        this.modelMapper = modelMapper;
    }


    public ItemDto save(ItemDto itemDto, Long userId) {
        // TODO: check access
        SbBill bill = billWebService.findById(itemDto.getBillId(), userId);
        Item item = modelMapper.map(itemDto, Item.class);
        item.setBill(bill);
        return modelMapper.map(itemService.save(item), ItemDto.class);
    }
}
