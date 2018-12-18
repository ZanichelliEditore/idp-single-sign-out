package models.interfaces;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface IReceiver {

    void process() throws IOException, TimeoutException;

}
