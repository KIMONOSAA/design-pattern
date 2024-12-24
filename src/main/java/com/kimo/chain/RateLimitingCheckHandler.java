package com.kimo.chain;


import com.kimo.dto.ObjectDto;
import com.kimo.dto.ResourceDto;
import org.springframework.stereotype.Service;

@Service
public class RateLimitingCheckHandler extends ChainHandler<ObjectDto,ResourceDto> {

    private ChainHandler<ObjectDto,ResourceDto> chainHandler;

    @Override
    public void setChainHandler(ChainHandler<ObjectDto,ResourceDto> chainHandler) {
        this.chainHandler = chainHandler;
    }

    public Boolean handler(ObjectDto objectDto, ResourceDto resourceDto){

        //执行限流业务
        System.out.println("执行限流业务");

        if(Boolean.TRUE.equals(hashNextHandler())){
            return chainHandler.handler(objectDto,resourceDto);
        }
        return null;
    }
}
