package com.btbatux.taskmanagement.exception;
    /**
     * ResourceNotFoundException, belirli bir kaynağın bulunamadığı durumlarda fırlatılan özel bir istisnadır.
     */
    public class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


