package br.com.psf.personalsystemfinance.exceptions;

public class FieldEmptyException extends Exception{


    public FieldEmptyException(){

    }

    public FieldEmptyException(String message){
        super(message);
    }

    /**
     *
     * @param field The String that will be tested
     * @param fieldName The field name that will be tested
     * @throws FieldEmptyException if the string is empty, the string should not be empty
     */
    public static void checkStringField(String field, String fieldName) throws FieldEmptyException {
        if(field == null || field.isEmpty() || field.isBlank()){
            throw new FieldEmptyException("The field " + fieldName + " is empty");
        }
    }
}
