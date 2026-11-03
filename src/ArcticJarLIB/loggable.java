package ArcticJarLIB;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public interface loggable {

    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String dateTime = LocalDateTime.now().format(formatterTime);
    String date =  LocalDateTime.now().format(formatterDate);
    String logPrefix();

    public ArrayList<String> getLog();
    public void log(String message, Boolean isDebugMessage) throws IOException;
    public void logUpdate() throws IOException;
    public void setLogMaxSize(Integer size);
    public Integer getLogMaxSize();

    public void setDebugStatus(boolean isLogDebug);
    public boolean getDebugStatus();

}
