package com.travelBill.splitBill.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("members")
public class MemberController {
    private final MemberWebService memberWebService;

    public MemberController(MemberWebService memberWebService) {
        this.memberWebService = memberWebService;
    }

    // TODO: provide user id
    @GetMapping(value = "")
    public List<UserDto> getBillsByUserId() {
        return memberWebService.getAllContactedUsers(1L);
    }
}
