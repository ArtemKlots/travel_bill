package com.travelBill.splitBill.web;

import com.travelBill.splitBill.web.responseDto.BillDto;
import com.travelBill.splitBill.web.responseDto.DetailedBillDto;
import com.travelBill.splitBill.web.responseDto.ItemDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("bills")
public class BillController {
    private final BillWebService billWebService;

    public BillController(BillWebService billWebService) {
        this.billWebService = billWebService;
    }

    // TODO: provide user id
    @GetMapping(value = "")
    public List<BillDto> getBillsByUserId(@RequestAttribute Long userId) {
        return billWebService.getBillsByUserId(userId);
    }

    @GetMapping(value = "{billId}")
    public DetailedBillDto getBillDetails(@PathVariable Long billId, @RequestAttribute Long userId) {
        throw new NullPointerException();
        // TODO: provide user id
//        return billWebService.getBillDetails(billId, userId);
    }

    @PutMapping(value = "{billId}")
    public DetailedBillDto setBillDetails(@PathVariable Long billId, @RequestBody DetailedBillDto body, @RequestAttribute Long userId) {
        // TODO: provide user id
        return billWebService.setBillDetails(billId, body, userId);
    }

    @GetMapping(value = "{billId}/items")
    public List<ItemDto> getBillItems(@PathVariable Long billId, @RequestAttribute Long userId) {
        // TODO: provide user id
        return billWebService.getBillItems(billId, userId);
    }
}
