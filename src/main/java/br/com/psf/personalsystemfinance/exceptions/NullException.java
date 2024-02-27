package br.com.psf.personalsystemfinance.exceptions;

public class NullException extends Exception{

    public NullException() {
    }
    public NullException(String message) {
        super(message);
    }

    /**
     *
     * @param object Any Object
     * @param fieldName Field name
     * @throws NullException If the object isn't null, it should be null
     */
    public static void checkFieldIsNotNull(Object object, String fieldName) throws NullException {
        if(object != null){
            throw new NullException("The " + fieldName + " should be null");
        }
    }

    /**
     * checkFieldIsNull
     * @param object Any Object
     * @param fieldName Field Name
     * @throws NullException if the object is null, it should not be null
     */
    public static void checkFieldIsNull(Object object, String fieldName) throws NullException {
        if(object == null){
            throw new NullException("The " + fieldName + " should not be null");
        }
    }

}
