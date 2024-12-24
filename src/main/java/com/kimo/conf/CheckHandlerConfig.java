package com.kimo.conf;


import com.kimo.chain.ChainHandler;
import com.kimo.chain.factory.PermissionChainFactory;
import com.kimo.dto.ObjectDto;
import com.kimo.dto.ResourceDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CheckHandlerConfig {


    @Bean
    public PermissionChainFactory permissionChainFactory(List<ChainHandler<ObjectDto, ResourceDto>> handlerList){
        return new PermissionChainFactory(handlerList);
    }

}
