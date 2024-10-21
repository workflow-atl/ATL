/**
 * This is the embedded version of the ATL model checker. 
 * Web services version: http://use-it.ro/
 * @authors Stoica L&F
 *
 */

 // The authentication credentials must be specified

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.StringReader;
import javax.json.*;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import javax.sql.PooledConnection;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

public class ATLJson {
    ATLParser parser;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String connection = "oracle";
    String user = "user"; // username
    String password = "password"; // password
    String client = "ATL";
    
    public ATLJson() {
        initDB();
    }
    
    public void initDB() {
        try {
            this.connection = "oracle";
            OracleConnectionPoolDataSource ocpds = new OracleConnectionPoolDataSource();
            ocpds.setDriverType("thin");
            ocpds.setServerName("localhost");
            ocpds.setNetworkProtocol("tcp");
            ocpds.setServiceName("FREEPDB1");
            ocpds.setPortNumber(1521);
            ocpds.setUser(user);
            ocpds.setPassword(password);
            PooledConnection pc = ocpds.getPooledConnection();
            conn = pc.getConnection();
            
            stmt = conn.createStatement();
            stmt.executeUpdate("alter session set NLS_COMP='BINARY'");
            stmt.close();
            conn.setAutoCommit(false);
               
        } catch (SQLException ex) {
            parser.response = parser.response.concat("\nSQLException: " + ex.getMessage() + "\n");
        } 
    }  
    
    public ATLParser ReadFromJson(String jsonModel) {
        String formula, A;
        int[] agents = null;
        boolean debug;
        JsonObject graph = null;

        try {
            
            JsonReader jr = Json.createReader(new StringReader(jsonModel));
            JsonObject jo = jr.readObject();
            graph = jo.getJsonObject("graph");
            JsonObject params = graph.getJsonObject("params");
            
            formula = params.getJsonString("formula").getString();
            A = params.getJsonString("A").getString();
            
            String delimiter = ",";
            String[] temp = A.split(delimiter);

            agents = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
               agents[i]=Integer.parseInt(temp[i].trim());
            }
            
            debug = params.getJsonString("debug").getString().toLowerCase().equals("true") ? true : false;
            
            CharStream cs = new ANTLRStringStream(formula);
            ATLLexer lexer = new ATLLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream();
            tokens.setTokenSource(lexer);
            parser = new ATLParser(tokens);
           
            this.parser.DEBUG = debug;
            
        } catch (Exception ex) {
            parser.response = parser.response.concat("\nJSONException: " + ex.getMessage() + "\n");
            return null;
        }
        
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("delete from " + client);
            stmt.close();
            conn.commit();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("select BEGIN, END, LABEL from " + client + " for UPDATE");
        } catch (Exception ex) {
            parser.response = parser.response.concat("\nSQLException: " + ex.getMessage() + "\n");
            return null;
        }
        
        try {
            JsonArray vertices = null;
            try
            {
                vertices = graph.getJsonObject("vertices").getJsonArray("vertex");
            }
            catch (Exception ex)
            {
                try {
                  vertices = Json.createArrayBuilder().add(graph.getJsonObject("vertices").getJsonObject("vertex")).build();
                }
                catch (Exception ex2) {
                    parser.response = parser.response.concat("\nVertices Exception:: " + ex2.getMessage() + "\n");
                    return null;
                }
            }
            
            parser.NR_STATES = vertices.size();
            parser.CLIENT = client;
            parser.CONNECTION = connection;
            parser.conn = conn;
            parser.init();

            for (int v = 0; v < vertices.size(); v++) {
                String label = vertices.getJsonObject(v).getString("label").trim();
                String[] temp;
                /* delimiter */
                String delimiter = ",";
                temp = label.split(delimiter);
                for (int i = 0; i < temp.length; i++) {
                    parser.p[v].add(temp[i].trim());
                }
            }
            
            JsonArray edges = null;
            try
            {
                edges = graph.getJsonObject("edges").getJsonArray("edge");
            }
            catch (Exception ex)
            {
                try {
                    edges = Json.createArrayBuilder().add(graph.getJsonObject("edges").getJsonObject("edge")).build();
                }
                catch (Exception ex2) {
                    parser.response = parser.response.concat("\nEdges Exception:: " + ex2.getMessage() + "\n");
                    return null;
                }
            }
            
            for (int e = 0; e < edges.size(); e++) {
                JsonObject edge = edges.getJsonObject(e);
                int from = Integer.parseInt(edge.getString("from"));
                int to = Integer.parseInt(edge.getString("to"));
                String label = edge.getString("label").trim();
                String[] temp;
                String[] temp2;
                /* delimitator */
                String delimiter = ">";
                String delimiter2 = ",";

                temp = label.split(delimiter);

                rs.moveToInsertRow();

                for (int i = 0; i < temp.length; i++) {

                    String movesA = "";

                    if (!temp[i].equals("")) {
                        temp[i] = temp[i].trim();
                        int k = temp[i].indexOf('<');
                        temp[i] = temp[i].substring(k + 1);

                        temp2 = temp[i].split(delimiter2);

                        for (int j = 0; j < agents.length; j++) {
                            movesA = movesA.concat(temp2[agents[j]] + ",");
                        }

                    } else {
                        for (int j = 0; j < agents.length; j++) {
                            movesA = movesA.concat("*,");
                        }
                    }

                    if (movesA.endsWith(",")) {
                        movesA = movesA.substring(0, movesA.length() - 1);
                    }

                    rs.updateString("LABEL", movesA);
                    rs.updateInt("BEGIN", from-1);
                    rs.updateInt("END", to-1);
                    rs.insertRow();
                }
            }
            
            conn.commit();
            rs.close();
            stmt.close();
            
            return parser;
            
        }
        catch (SQLException e) {
            parser.response = parser.response.concat("\nSQLException: " + e.getMessage() + "\n");
            return null;
        } 
        catch (Exception e) {
            parser.response = parser.response.concat("\nException2: " + e.getMessage() + "\n");
            return null;
        }
        finally {
            try {
                conn.setAutoCommit(false);
            } catch (Exception e) {
            }
        }
    }
    
    public void CloseDatabase()
    {
        try{
        conn.close();}
        catch (SQLException e){
            parser.response = parser.response.concat("\nSQLException: " + e.getMessage() + "\n");
        }
    }
}
