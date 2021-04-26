package com.travelBill.splitBill.web.bill;

import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("invite")
public class InviteController {
    private final BillWebService billWebService;

    public InviteController(BillWebService billWebService) {
        this.billWebService = billWebService;
    }

    @GetMapping(value = "{inviteId}")
    public Long performInvite(@PathVariable String inviteId, @RequestAttribute Long userId) {
        return billWebService.invite(inviteId, userId);
    }
}
