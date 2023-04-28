package Manager;

import java.io.IOException;

/*
    @author Andrey Panasiuk
    Git @AltairPhinArev
 */

public class ManagerSaveException extends IOException  {

    private String message;
    public ManagerSaveException(String message)     {
       this.message = message;
    }
}
