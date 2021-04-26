package com.travelBill.splitBill.web.bill;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.debt.DebtService;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.splitBill.core.ClosedBillException;
import com.travelBill.splitBill.core.DebtCalculator;
import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.bill.SbBillService;
import com.travelBill.splitBill.web.responseDto.BillDto;
import com.travelBill.splitBill.web.responseDto.DebtDto;
import com.travelBill.splitBill.web.responseDto.DetailedBillDto;
import com.travelBill.splitBill.web.responseDto.ItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillWebService {
    private final SbBillService sbBillService;
    private final UserService userService;
    private final DebtService debtService;
    private final ModelMapper modelMapper;


    public BillWebService(SbBillService sbBillService, UserService userService, DebtService debtService, ModelMapper modelMapper) {
        this.sbBillService = sbBillService;
        this.userService = userService;
        this.debtService = debtService;
        this.modelMapper = modelMapper;
    }

    public List<BillDto> getBillsForUser(Long userId) {
        return sbBillService.getAllBillsForUser(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BillDto> getBillsByUserId(Long userId) {
        return sbBillService.getBillsByOwnerId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public DetailedBillDto addBill(DetailedBillDto newBill, Long userId) {
        SbBill sbBill = modelMapper.map(newBill, SbBill.class);
        SbBill bill = sbBillService.add(sbBill, userId);
        return modelMapper.map(bill, DetailedBillDto.class);
    }

    public DetailedBillDto getBillDetails(Long billId, Long userId) {
        SbBill bill = sbBillService.findById(billId, userId);
        DetailedBillDto detailedBillDto = modelMapper.map(bill, DetailedBillDto.class);
        List<Debt> debts = new DebtCalculator().calculate(bill);
        List<DebtDto> debtsDto = debts.stream().map(debt -> modelMapper.map(debt, DebtDto.class)).collect(Collectors.toList());
        detailedBillDto.setDebts(debtsDto);
        return detailedBillDto;
    }

    public DetailedBillDto setBillDetails(Long billId, DetailedBillDto newValues, Long userId) {
        SbBill sbBill = sbBillService.findById(billId, userId);
        List<Long> providedUserIds = newValues.getMembers()
                .stream()
                .map(userDto -> modelMapper.map(userDto, User.class).getId())
                .collect(Collectors.toList());
        List<User> users = userService.findAllById(providedUserIds);

        sbBill.setTitle(newValues.getTitle());
        sbBill.setCurrency(newValues.getCurrency());
        sbBill.setMembers(users);
        SbBill updatedBill = sbBillService.save(sbBill, userId);

        return modelMapper.map(updatedBill, DetailedBillDto.class);
    }

    @Transactional
    public DetailedBillDto closeBill(Long billId, Long userId) {
        SbBill closedBill = sbBillService.close(billId, userId);
        List<Debt> debts = new DebtCalculator().calculate(closedBill);

        List<DebtDto> debtsDto = debts.stream().map(debt -> modelMapper.map(debt, DebtDto.class)).collect(Collectors.toList());
        DetailedBillDto detailedBillDto = modelMapper.map(closedBill, DetailedBillDto.class);
        detailedBillDto.setDebts(debtsDto);

        List<Debt> debtsWithoutOwner = debts.stream().filter(debt -> debt.getDebtor().getId().equals(closedBill.getOwner().getId())).collect(Collectors.toList());
        debtService.saveAll(debtsWithoutOwner);
        return detailedBillDto;
    }

    public void deleteBill(Long billId, Long userId) {
        sbBillService.delete(billId, userId);
    }


    private BillDto convertToDto(SbBill sbBill) {
        return modelMapper.map(sbBill, BillDto.class);
    }

    public List<ItemDto> getBillItems(Long billId, Long userId) {
        //TODO: check access
        return sbBillService.findById(billId, userId)
                .getItems()
                .stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
    }
}
