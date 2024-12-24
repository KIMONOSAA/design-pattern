package com.kimo.chain;


import com.kimo.dto.ObjectDto;
import com.kimo.dto.ResourceDto;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionCheckHandler extends ChainHandler<ObjectDto,ResourceDto> {
    private ChainHandler<ObjectDto,ResourceDto> chainHandler;
    @Override
    public void setChainHandler(ChainHandler<ObjectDto,ResourceDto> chainHandler) {
        this.chainHandler = chainHandler;
    }

    public Boolean handler(ObjectDto objectDto, ResourceDto resourceDto){

        System.out.println("执行权限检验");

        if(Boolean.TRUE.equals(hashNextHandler())){
            return chainHandler.handler(objectDto,resourceDto);
        }
        return null;
    }

}
