package com.sparta.wuzuzu.domain.user.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.user.dto.MyInformReadResponse;
import com.sparta.wuzuzu.domain.user.service.MyInformService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import io.lettuce.core.protocol.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId}")
public class MyInformController {

    private final MyInformService myInformService;

    @GetMapping
    public ResponseEntity<CommonResponse<MyInformReadResponse>> readMyInform(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId){
        MyInformReadResponse myInformReadResponse = myInformService.readMyInform(userDetails.getUser());
        return CommonResponse.ofDataWithHttpStatus(myInformReadResponse, HttpStatus.OK);
    }
}
