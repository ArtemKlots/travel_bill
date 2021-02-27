package com.travelBill.splitBill.web;

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
    public List<BillDto> getBillsByUserId() {
        return billWebService.getBillsByUserId(1L);
    }

    @GetMapping(value = "{billId}")
    public DetailedBillDto getBillDetails(@PathVariable Long billId) {
        // TODO: provide user id
        return billWebService.getBillDetails(billId, 1L);
    }

    @PutMapping(value = "{billId}")
    public BillDto setBillDetails(@PathVariable Long billId, @RequestBody BillDto body) {
        // TODO: provide user id
        return billWebService.setBillDetails(billId, body, 1L);
    }


}
