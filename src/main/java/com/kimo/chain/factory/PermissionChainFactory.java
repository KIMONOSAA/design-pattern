package com.kimo.chain.factory;


import com.kimo.chain.ChainHandler;
import com.kimo.chain.RateLimitingCheckHandler;
import com.kimo.chain.ResourceCheckHandler;
import com.kimo.chain.RolePermissionCheckHandler;
import com.kimo.dto.ObjectDto;
import com.kimo.dto.ResourceDto;

import java.util.List;

public class PermissionChainFactory<T,R> {

    private ChainHandler<T,R> firstChainHandler;


    public PermissionChainFactory(List<ChainHandler<T,R>> handlerList) {
        for (int i = 0; i < handlerList.size()-1; i++) {
            handlerList.get(i).setChainHandler(handlerList.get(i+1));
        }

        firstChainHandler = handlerList.get(0);

    }

    public void executorHandler(T objectDto, R resourceDto){
        firstChainHandler.handler(objectDto,resourceDto);
    }
}
