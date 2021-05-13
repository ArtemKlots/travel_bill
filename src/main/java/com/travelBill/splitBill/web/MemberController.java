package com.travelBill.splitBill.web;

import com.travelBill.splitBill.web.responseDto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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

    @GetMapping(value = "")
    public List<UserDto> getBillsByUserId(@RequestAttribute Long userId) {
        return memberWebService.getAllContactedUsers(userId);
    }
}
