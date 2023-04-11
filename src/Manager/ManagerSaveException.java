package Manager;

import java.io.FileNotFoundException;
import java.io.IOException;
public class ManagerSaveException extends IOException  {

    private String message;
    public ManagerSaveException(String message)     {
       this.message = message;
    }
}
