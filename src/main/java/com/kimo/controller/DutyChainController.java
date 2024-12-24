package com.kimo.controller;


import com.kimo.dto.ObjectDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DutyChainController {


    @PostMapping("/yewuluoji")
    public void getYeWuLuoJi(ObjectDto objectsDto, MultipartFile file){

    }


}
