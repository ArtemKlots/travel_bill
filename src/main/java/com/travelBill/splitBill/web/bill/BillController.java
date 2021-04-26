package com.travelBill.splitBill.web.bill;

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

    @GetMapping(value = "")
    public List<BillDto> getBillsByUserId(@RequestAttribute Long userId) {
        return billWebService.getBillsForUser(userId);
    }

    @PostMapping(value = "")
    public DetailedBillDto addBill(@RequestBody DetailedBillDto body, @RequestAttribute Long userId) {
        return billWebService.addBill(body, userId);
    }

    @GetMapping(value = "{billId}")
    public DetailedBillDto getBillDetails(@PathVariable Long billId, @RequestAttribute Long userId) {
        return billWebService.getBillDetails(billId, userId);
    }

    @PutMapping(value = "{billId}")
    public DetailedBillDto setBillDetails(@PathVariable Long billId, @RequestBody DetailedBillDto body, @RequestAttribute Long userId) {
        return billWebService.setBillDetails(billId, body, userId);
    }

    @DeleteMapping(value = "{billId}")
    public void deleteBill(@PathVariable Long billId, @RequestAttribute Long userId) {
        billWebService.deleteBill(billId, userId);
    }

    @GetMapping(value = "{billId}/items")
    public List<ItemDto> getBillItems(@PathVariable Long billId, @RequestAttribute Long userId) {
        return billWebService.getBillItems(billId, userId);
    }

    @PostMapping(value = "{billId}/close")
    public DetailedBillDto closeBill(@PathVariable Long billId, @RequestAttribute Long userId) {
        return billWebService.closeBill(billId, userId);
    }
}
