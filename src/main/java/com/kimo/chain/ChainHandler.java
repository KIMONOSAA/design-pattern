package com.kimo.chain;


import com.kimo.dto.ObjectDto;
import com.kimo.dto.ResourceDto;

public abstract class ChainHandler<T,R> {
    public ChainHandler<T,R> nextChainHandler;


    public void setChainHandler(ChainHandler<T,R> chainHandler) {
        this.nextChainHandler = chainHandler;
    }

    public Boolean hashNextHandler(){
        return this.nextChainHandler != null;
    }

    public Boolean handler(T objectDto, R resourceDto){
        if(Boolean.TRUE.equals(hashNextHandler())){
            return nextChainHandler.handler(objectDto,resourceDto);
        }
        return null;
    }

}
