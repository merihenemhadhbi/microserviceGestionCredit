package com.example.microserviceGestionCedit.exception;

public class InvalidBalanceException extends Exception{

    public InvalidBalanceException(String str){
        super(str);
    }

}
