package com.travelBill.splitBill.web;

import com.travelBill.api.core.user.User;
import com.travelBill.splitBill.core.user.SbUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberWebService {
    private final SbUserService sbUserService;
    private final ModelMapper modelMapper;


    public MemberWebService(SbUserService sbUserService, ModelMapper modelMapper) {
        this.sbUserService = sbUserService;
        this.modelMapper = modelMapper;
    }


    public List<UserDto> getAllContactedUsers(Long userId) {
        return sbUserService.getAllContactedUsers(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
