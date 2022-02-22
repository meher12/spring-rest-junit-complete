package com.mdev.springboot.exception;

public class ProductAlreadyExistsException extends Exception{
    private String message;

    public ProductAlreadyExistsException(){
        super();
    }

    public ProductAlreadyExistsException(String message){
        super();
        this.message = message;
    }

}