/**
 * This is the embedded version of the ATL model checker. 
 * Web services version: http://use-it.ro/
 * @authors Stoica L&F
 *
 */

import java.util.Iterator;
import org.json.JSONObject;
import org.json.XML;
import java.sql.Clob;
import java.sql.Connection;

public class ATLChecker {

    /**
     * 
     */
    public ATLChecker() {
    }

    public static java.lang.String checkModel(oracle.sql.CLOB model, int size, oracle.sql.CLOB[] response) {
        ATLJson atl = null;
        ATLParser parser = null;
        String s_model = "";
        String message = "success";
        
        try
        {
            if (model.getSubString(1,5).equals("<?xml"))
            {
              s_model = model.getSubString(1,size);
              JSONObject json = XML.toJSONObject(s_model, true);   
              s_model = json.toString(4); 
            }
            else 
            {
              s_model = model.getSubString(1,size);
            }
            atl = new ATLJson();
        }
        catch (Exception e ){
            try {
              message = "\nException: "+e.getMessage()+"\n";
              response[0].setString(1, message);
              return message;
            }
            catch (Exception e2 ){
              return "\nException: "+e2.getMessage()+"\n";
            }
        }
        
        try
        {
            parser = atl.ReadFromJson(s_model);
        }
        catch (Exception e ){
            try {
              message = "\nException: "+e.getMessage()+"\n" + atl.parser.GetResponse();
              response[0].setString(1, message);
              return message;
            }
            catch (Exception e2 ){
              return "\nException: "+e2.getMessage()+"\n";
            }
        }
        
        try
        {
          ATLParser.atlFormula_return result = parser.atlFormula();
          
          Iterator it = result.set.iterator();
          parser.response = parser.response.concat("States: ");
          while (it.hasNext())
          { 
            parser.response = parser.response.concat(it.next().toString() + " ");
          }
          parser.response = parser.response.concat(";\n");
          response[0].setString(1,parser.response);
          atl.CloseDatabase();
          return message;
        }
        catch (Exception e ){
            parser.response = parser.response.concat("\nException: "+e.getMessage()+"\n");
            try {
              response[0].setString(1,parser.response);
              atl.CloseDatabase();
              return parser.response;
            }
            catch (Exception e2 ){
              return "\nException: "+e2.getMessage()+"\n";
            }
        }
    }
    
    private static java.sql.Clob stringToClob2(String param, Connection conn)
    {
        Clob myClob = null;
        try {
            myClob = conn.createClob();
            myClob.setString(1, param);
        } catch (Exception e) {
        }
        return myClob;
    }
}
