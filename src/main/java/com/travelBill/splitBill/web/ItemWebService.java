package com.travelBill.splitBill.web;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.splitBill.core.ClosedBillException;
import com.travelBill.splitBill.core.assigning.Assign;
import com.travelBill.splitBill.core.assigning.AssignsRepository;
import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.bill.SbBillService;
import com.travelBill.splitBill.core.item.Item;
import com.travelBill.splitBill.core.item.ItemService;
import com.travelBill.splitBill.web.responseDto.AssignDto;
import com.travelBill.splitBill.web.responseDto.ItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemWebService {
    private final ItemService itemService;
    private final SbBillService sbBillService;
    //TODO: remove repository dependency from here and make closed bill chack in service
    private final AssignsRepository assigningRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    public ItemWebService(ItemService itemService, SbBillService sbBillService, AssignsRepository assigningRepository, UserService userService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.sbBillService = sbBillService;
        this.assigningRepository = assigningRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    public ItemDto save(ItemDto itemDto, Long userId) {
        SbBill bill = sbBillService.findById(itemDto.getBillId(), userId);
        Item item = modelMapper.map(itemDto, Item.class);
        item.setBill(bill);
        return modelMapper.map(itemService.save(item), ItemDto.class);
    }

    public void delete(Long itemId, Long userId) {
        // TODO: check access
        itemService.deleteById(itemId);
    }

    public AssignDto assign(Long itemId, AssignDto assignDto, Long userId) {
        Item item = itemService.findById(itemId);
        if (!item.getBill().isOpened()) throw new ClosedBillException();
        Assign existingAssign = item.getAssigns().stream().filter(i -> i.getUser().getId().equals(userId)).findFirst().orElse(null);

        if (existingAssign != null && assignDto.getAmount() == 0) {
            assigningRepository.deleteById(existingAssign.getId());
            return null;
        }

        if (existingAssign != null) {
            existingAssign.setAmount(assignDto.getAmount());
            return modelMapper.map(assigningRepository.save(existingAssign), AssignDto.class);
        } else {
            Assign assign = new Assign();
            assign.setAmount(assignDto.getAmount());
            // TODO: check access (if invited to bill)
            User user = userService.findById(userId);
            assign.setUser(user);
            assign.setItem(item);
            return modelMapper.map(assigningRepository.save(assign), AssignDto.class);
        }
    }
}
