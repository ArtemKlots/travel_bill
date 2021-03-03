package com.travelBill.splitBill.web;

import com.travelBill.api.core.user.User;
import com.travelBill.splitBill.core.AccessDeniedException;
import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.bill.SbBillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BillWebService {
    private final SbBillService sbBillService;
    private final ModelMapper modelMapper;


    public BillWebService(SbBillService sbBillService, ModelMapper modelMapper) {
        this.sbBillService = sbBillService;
        this.modelMapper = modelMapper;
    }

    public SbBill findById(Long billId, Long userId) {
        SbBill bill = sbBillService.findById(billId);
        if (!Objects.equals(bill.getOwner().getId(), userId)) {
            throw new AccessDeniedException();
        }
        return bill;
    }

    public List<BillDto> getBillsByUserId(Long userId) {
        return sbBillService.getBillsByOwnerId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DetailedBillDto getBillDetails(Long billId, Long userId) {
        //TODO: check access
        return modelMapper.map(sbBillService.findById(billId), DetailedBillDto.class);
    }

    public DetailedBillDto setBillDetails(Long billId, DetailedBillDto newValues, Long userId) {
        SbBill sbBill = sbBillService.findById(billId);
        if (!sbBill.getOwner().getId().equals(userId)) throw new AccessDeniedException();
        // Check if user able to rename user
        List<User> users = newValues.getMembers()
                .stream()
                .map(userDto -> modelMapper.map(userDto, User.class))
                .collect(Collectors.toList());
        boolean containsOwner = users.stream().anyMatch(user -> Objects.equals(user.getId(), userId));
        if (!containsOwner) throw new RuntimeException("You cannot remove yourself from members");

        sbBill.setTitle(newValues.getTitle());
        sbBill.setCurrency(newValues.getCurrency());
        sbBill.setMembers(users);

        return this.convertToDetailedDto(sbBillService.save(sbBill));
    }

    private BillDto convertToDto(SbBill sbBill) {
        return modelMapper.map(sbBill, BillDto.class);
    }

    public List<ItemDto> getBillItems(Long billId, Long userId) {
        //TODO: check access
        return sbBillService.findById(billId)
                .getItems()
                .stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
    }

    private DetailedBillDto convertToDetailedDto(SbBill sbBill) {
        return modelMapper.map(sbBill, DetailedBillDto.class);
    }

    private SbBill convertToEntity(DetailedBillDto billDto) throws ParseException {
        return modelMapper.map(billDto, SbBill.class);
    }
}
