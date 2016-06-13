import org.joda.time.DateTimeZone;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

/**
 * Automatically generates timezones.xml
 */
class GenerateTimezonesXml {

  public static void main(String[] args) {
    
    String filename = "timezones.xml";
  
    Set<String> ids = DateTimeZone.getAvailableIDs();
    ArrayList<String> sortIDs = new ArrayList<>(ids.size());
    sortIDs.addAll(ids);
    Collections.sort(sortIDs);

    String output = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                  + "<resources>\n"
                  + "\t<!-- Timezone IDs generated on " + getCurrentDate() + " -->\n"
                  + "\t<string-array name=\"prefs_timezone_ids\">\n";
    
    for (String id : sortIDs) {
      output += "\t\t<item>" + id + "</item>\n";
    }
    
    output += "\t</string-array>\n" +
              "</resources>";
    
    //System.out.println(output);
    writeFile(output, filename);
  }
  
  private static void writeFile(String output, String fname) {
    try {
      File file = new File(fname);
      file.createNewFile();
      PrintWriter writer = new PrintWriter(file, "UTF-8");
      writer.println(output);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static String getCurrentDate() {
    DateFormat df = new SimpleDateFormat("MMM dd yyyy");
    Date date = new Date();
    
    return df.format(date);
  }
  
}