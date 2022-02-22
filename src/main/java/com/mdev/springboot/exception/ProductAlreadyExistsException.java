package com.mdev.springboot.exception;

public class ProductAlreadyExistsException extends Exception{
    private String message;

    public ProductAlreadyExistsException(){}

    public ProductAlreadyExistsException(String message){
        this.message = message;
    }

}