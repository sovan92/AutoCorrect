package com.oracle.exceptions;

public class DictionaryInitializeException extends Exception{

    @Override
    public String getMessage() {
        return "The Dictionary Hasn't been initialized.";
    }
}
