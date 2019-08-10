package com.siwa.exceptions;

public class AlgorithmDuplicateName extends Exception{

    /**
     * This its the serial number Id for serialization concepts
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor of class, made to check when the duplicated errors occurs.
     * @param {String} message Message provide a string message error.
     */
    public AlgorithmDuplicateName(String message) {
        super(message);
    }
}

