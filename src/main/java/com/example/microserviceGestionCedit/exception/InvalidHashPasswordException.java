package com.example.microserviceGestionCedit.exception;

public class InvalidHashPasswordException extends Exception{

    public InvalidHashPasswordException(String str){
        super(str);
    }

}
