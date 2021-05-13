package com.travelBill.splitBill.core.item;

import com.travelBill.TravelBillException;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.splitBill.core.AccessDeniedException;
import com.travelBill.splitBill.core.ClosedBillException;
import com.travelBill.splitBill.core.assigning.Assign;
import com.travelBill.splitBill.core.assigning.AssignsRepository;
import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.bill.SbBillService;
import com.travelBill.splitBill.web.responseDto.AssignDto;
import com.travelBill.splitBill.web.responseDto.ItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@Transactional
public class ItemService {
    private final SbBillService sbBillService;
    //TODO: remove repository dependency from here and make closed bill chack in service
    private final AssignsRepository assigningRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;


    public ItemService(SbBillService sbBillService, AssignsRepository assigningRepository, UserService userService, ModelMapper modelMapper, ItemRepository itemRepository) {
        this.sbBillService = sbBillService;
        this.assigningRepository = assigningRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
    }

    private void deleteById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    private Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
    }


    public ItemDto save(ItemDto itemDto, Long userId) {
        SbBill bill = sbBillService.findById(itemDto.getBillId(), userId);
        Item item = modelMapper.map(itemDto, Item.class);
        if (!item.getBill().isOpened()) throw new ClosedBillException();
        item.setBill(bill);
        return modelMapper.map(itemRepository.save(item), ItemDto.class);
    }

    public void delete(Long itemId, Long userId) {
        Item item = findById(itemId);
        SbBill bill = item.getBill();
        if (!bill.isOpened()) {
            throw new ClosedBillException();
        }

        if (!Objects.equals(bill.getOwner().getId(), userId)) {
            throw new AccessDeniedException();
        }

        deleteById(itemId);
    }

    public AssignDto assign(Long itemId, AssignDto assignDto, Long userId) {
        Item item = findById(itemId);
        if (!item.getBill().isOpened()) throw new ClosedBillException();
        Assign existingAssign = item.getAssigns().stream().filter(i -> i.getUser().getId().equals(userId)).findFirst().orElse(null);

        if (existingAssign == null && assignDto.getAmount() == 0) {
            throw new TravelBillException("Cannot create new assignment with amount 0");
        }

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
