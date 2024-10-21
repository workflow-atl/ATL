/**
 * This is the embedded version of the ATL model checker. 
 * Web services version: http://use-it.ro/
 * @authors Stoica L&F
 *
 */

import org.antlr.runtime.*;
import java.util.HashSet;
import java.util.Iterator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class ATLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AP", "NEWLINE", "WS", "'<<A>> '", "'U'", "'<<A>>~'", "'<<A>>@'", "'<<A>>#'", "'=>'", "'or'", "'and'", "'not'", "'('", "')'", "'true'", "'false'"
    };
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;
    public static final int T__19=19;
    public static final int WS=6;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int NEWLINE=5;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int AP=4;

    public ATLParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public ATLParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
            
    }
        
    public String[] getTokenNames() { return ATLParser.tokenNames; }
    public String getGrammarFileName() { return ".\\ATL-Compiler\\ATL.g"; }


    @Override
    public void reportError(RecognitionException re) {
        throw new RuntimeException("Syntactical error!"); 
    }
    public String response = "";

    HashSet all_setS;

    public boolean DEBUG = false;
    public int NR_STATES;
    public HashSet p[];
    public String CLIENT="test";
    public String CONNECTION="";

    public Connection conn = null;

    Statement stmt = null;
    ResultSet rs = null;
     
         void init() {
           response = "";
           all_setS = new HashSet();
           p = new HashSet[NR_STATES];
           for (int i=0; i < NR_STATES; i++)
           {
             p[i]=new HashSet();
             all_setS.add(new Integer(i));
           }   
         }

         HashSet Pre(HashSet r)
             {
                String set = "";
                String val_set = "";
                HashSet rez = new HashSet();
                String item = "";
                int i = -1;

                if (r.size()==0) return rez;
                
                int longArray[] = new int[r.size()];

                Iterator it = r.iterator();
                while (it.hasNext()) {
                    i++;
                    longArray[i] = (int)it.next();
                    //longArray[i] = ((Integer)it.next()).longValue();
                }
                if (set.endsWith(",")) {
                    set = set.substring(0, set.length() - 1);
                }
                if (val_set.endsWith(",")) {
                    val_set = val_set.substring(0, val_set.length() - 1);
                }

                String sql="";
                
                try {
                     //conn.setAutoCommit(false);
                     
                     sql = "delete from R";
                     stmt = conn.createStatement();
                     stmt.executeQuery(sql);
                     stmt.close();

                     sql = "delete from NOT_R";
                     stmt = conn.createStatement();
                     stmt.executeUpdate(sql);
                     stmt.close();

                     sql = "delete from B";
                     stmt = conn.createStatement();
                     stmt.executeUpdate(sql);
                     stmt.close();

                     sql = "delete from NOT_B";
                     stmt = conn.createStatement();
                     stmt.executeUpdate(sql);
                     stmt.close();
                     
                     conn.commit();
                     
                    // The type must be created first in the database
                    // CREATE TYPE NUMBERTABLE AS TABLE OF NUMBER;
                    ArrayDescriptor desc = ArrayDescriptor.createDescriptor("NUMBERTABLE", conn);
                    ARRAY array_to_pass = new ARRAY(desc, conn, longArray);
                    OraclePreparedStatement pstat
                            = (OraclePreparedStatement) conn.prepareStatement(
                                    "INSERT INTO R "
                                    + " (SELECT distinct * FROM TABLE (SELECT CAST(? AS NUMBERTABLE) FROM DUAL))");
                    pstat.setARRAY(1, array_to_pass);
                    pstat.executeUpdate();
                    pstat.close();
                    // end

                     sql = "insert into NOT_R "
                             + "select distinct END E from " + CLIENT + " left join R on END = E where E is null";
                     stmt = conn.createStatement();
                     stmt.executeUpdate(sql);
                     stmt.close();

                     sql = "insert into B "
                             + "select distinct BEGIN B, LABEL L from " + CLIENT + " inner join R on END = E ";
                     stmt = conn.createStatement();
                     stmt.executeUpdate(sql);
                     stmt.close();

                     sql = "insert into NOT_B "
                             + "select distinct BEGIN B, LABEL L from " + CLIENT + " inner join NOT_R on END = E "
                             + "inner join B on BEGIN = B";
                     stmt = conn.createStatement();
                     stmt.executeUpdate(sql);
                     stmt.close();
                     conn.commit();

                 } catch (SQLException ex) {
                     response = response.concat("\nSQLException: " + ex.getMessage() + "\n");
                 }

                 sql = " select distinct a.B BEGIN "
                         + "  from B a left join NOT_B b "
                         + "  on a.B = b.B and a.L = b.L "
                         + "  where a.L != NVL(b.L,'***') ";
                 
                 try {
                    stmt = conn.createStatement();
                    stmt.setQueryTimeout(300);
                    rs = stmt.executeQuery(sql);

                    if (rs != null) {
                        while(rs.next())
                            rez.add(new Integer(rs.getInt("BEGIN")));
                    }
                    stmt.close();
                    rs.close();
                    conn.commit();

                } catch (SQLException ex) {
                    response = response.concat("\nSQLException: " + ex.getMessage() + "\n");
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException sqlEx) {
                        } // ignore
                        rs = null;
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException sqlEx) {
                        } // ignore
                        stmt = null;
                    }
                }
                if (DEBUG) printPre(r,rez);
                return rez;
            }

        void printSet(String formula, HashSet set)
        {
          Iterator it = set.iterator();
          response = response.concat("    " + formula + ": ");
          while (it.hasNext())
          { 
            response = response.concat(it.next().toString() + " ");
          }
          response = response.concat(";\n");
        }

        void printPre(HashSet set, HashSet rez)
        {
           Iterator it = set.iterator();
           response = response.concat(" Pre(");
           while (it.hasNext())
           {
             response = response.concat(it.next().toString() + " ");
           }
           response = response.concat(") = ");
           it = rez.iterator();
           while (it.hasNext())
           {
             response = response.concat(it.next().toString() + " ");
           }
           response = response.concat(";\n");
        }

        void trace(String rule, int alternative) {
          response = response.concat(rule + " - " + Integer.toString(alternative)+ "\n");
        }

        public String GetResponse()
        {
          return response;
        }

    public static class atlFormula_return extends ParserRuleReturnScope {
        public HashSet set;
    };

    // $ANTLR start "atlFormula"
    // .\\ATL-Compiler\\ATL.g:344:1: atlFormula returns [HashSet set] : ( '<<A>> ' e1= implExpr 'U' e2= implExpr | '<<A>>~' e= implExpr | '<<A>>@' e= implExpr | '<<A>>#' e= implExpr | e= implExpr );
    public final ATLParser.atlFormula_return atlFormula() throws RecognitionException {
        ATLParser.atlFormula_return retval = new ATLParser.atlFormula_return();
        retval.start = input.LT(1);

        ATLParser.implExpr_return e1 = null;

        ATLParser.implExpr_return e2 = null;

        ATLParser.implExpr_return e = null;

        try {
            // .\\ATL-Compiler\\ATL.g:346:2: ( '<<A>> ' e1= implExpr 'U' e2= implExpr | '<<A>>~' e= implExpr | '<<A>>@' e= implExpr | '<<A>>#' e= implExpr | e= implExpr )
            int alt1=5;
            switch ( input.LA(1) ) {
            case 7:
                {
                alt1=1;
                }
                break;
            case 9:
                {
                alt1=2;
                }
                break;
            case 10:
                {
                alt1=3;
                }
                break;
            case 11:
                {
                alt1=4;
                }
                break;
            case AP:
            case 15:
            case 16:
            case 18:
            case 19:
                {
                alt1=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // .\\ATL-Compiler\\ATL.g:347:2: '<<A>> ' e1= implExpr 'U' e2= implExpr
                    {
                    match(input,7,FOLLOW_7_in_atlFormula65); if (state.failed) return retval;
                    pushFollow(FOLLOW_implExpr_in_atlFormula69);
                    e1=implExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,8,FOLLOW_8_in_atlFormula71); if (state.failed) return retval;
                    pushFollow(FOLLOW_implExpr_in_atlFormula75);
                    e2=implExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      		    HashSet r = new HashSet();
                      		    HashSet p = (e2!=null?e2.set:null);
                      		    while (!r.containsAll(p))
                      		    {
                      		        r.addAll(p);
                      		        p = Pre(r);
                      		        p.retainAll((e1!=null?e1.set:null));
                      		    }
                      		    retval.set = r;
                      		    if (DEBUG)
                      		    {
                      		        trace("atlFormula",1);
                      	                printSet("( <<A>> " + (e1!=null?input.toString(e1.start,e1.stop):null) + " U " + (e2!=null?input.toString(e2.start,e2.stop):null) + " ) ",r);  
                      	            }   	
                      		
                    }

                    }
                    break;
                case 2 :
                    // .\\ATL-Compiler\\ATL.g:364:10: '<<A>>~' e= implExpr
                    {
                    match(input,9,FOLLOW_9_in_atlFormula91); if (state.failed) return retval;
                    pushFollow(FOLLOW_implExpr_in_atlFormula95);
                    e=implExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      	            HashSet Q = new HashSet(all_setS);
                      	            HashSet r = new HashSet();
                      		    HashSet p = (e!=null?e.set:null);
                      		    while (!r.containsAll(p))
                      		    {
                      		        r.addAll(p);
                      		        p = Pre(r);
                      		        p.retainAll(Q);
                      		    }
                      		    retval.set = r;
                      		    if (DEBUG)
                      		    {
                      		        trace("atlFormula",2);
                      	                printSet("<<A>>~ " + (e!=null?input.toString(e.start,e.stop):null),r); 
                      	            }  
                      	        
                    }

                    }
                    break;
                case 3 :
                    // .\\ATL-Compiler\\ATL.g:382:10: '<<A>>@' e= implExpr
                    {
                    match(input,10,FOLLOW_10_in_atlFormula118); if (state.failed) return retval;
                    pushFollow(FOLLOW_implExpr_in_atlFormula122);
                    e=implExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      	          HashSet rez = Pre((e!=null?e.set:null));
                      	          
                      	          retval.set = rez; 
                      	          if (DEBUG)
                      	          {  
                      	              trace("atlFormula",3);
                      	              printSet("<<A>>@ " + (e!=null?input.toString(e.start,e.stop):null),rez); 
                      	          }
                      	         
                      	        
                    }

                    }
                    break;
                case 4 :
                    // .\\ATL-Compiler\\ATL.g:394:10: '<<A>>#' e= implExpr
                    {
                    match(input,11,FOLLOW_11_in_atlFormula145); if (state.failed) return retval;
                    pushFollow(FOLLOW_implExpr_in_atlFormula149);
                    e=implExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      	            HashSet r = new HashSet(all_setS);
                      	            HashSet p = (e!=null?e.set:null);
                      	            while (!p.containsAll(r))
                      	            {
                      	                r = new HashSet(p);
                      	                p = Pre(r);
                      	                p.retainAll((e!=null?e.set:null));
                      	            }
                      	            retval.set = r;
                      	            if (DEBUG)
                      	            {
                      	                trace("atlFormula",4);
                      	                printSet("<<A>># " + (e!=null?input.toString(e.start,e.stop):null),r);
                      	            }
                      	        
                    }

                    }
                    break;
                case 5 :
                    // .\\ATL-Compiler\\ATL.g:411:4: e= implExpr
                    {
                    pushFollow(FOLLOW_implExpr_in_atlFormula170);
                    e=implExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      	            retval.set = new HashSet((e!=null?e.set:null));
                      	            if (DEBUG)
                      	            {
                      	                trace("atlFormula", 5);
                      		        printSet((e!=null?input.toString(e.start,e.stop):null),retval.set); 
                      		    }
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException ex) {

                               reportError(ex);
                            
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atlFormula"

    public static class implExpr_return extends ParserRuleReturnScope {
        public HashSet set;
    };

    // $ANTLR start "implExpr"
    // .\\ATL-Compiler\\ATL.g:424:1: implExpr returns [HashSet set] : e1= orExpr ( '=>' e2= orExpr )* ;
    public final ATLParser.implExpr_return implExpr() throws RecognitionException {
        ATLParser.implExpr_return retval = new ATLParser.implExpr_return();
        retval.start = input.LT(1);

        ATLParser.orExpr_return e1 = null;

        ATLParser.orExpr_return e2 = null;


        try {
            // .\\ATL-Compiler\\ATL.g:425:2: (e1= orExpr ( '=>' e2= orExpr )* )
            // .\\ATL-Compiler\\ATL.g:425:10: e1= orExpr ( '=>' e2= orExpr )*
            {
            pushFollow(FOLLOW_orExpr_in_implExpr216);
            e1=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              retval.set = new HashSet((e1!=null?e1.set:null));
            }
            // .\\ATL-Compiler\\ATL.g:426:10: ( '=>' e2= orExpr )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==12) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // .\\ATL-Compiler\\ATL.g:426:12: '=>' e2= orExpr
            	    {
            	    match(input,12,FOLLOW_12_in_implExpr231); if (state.failed) return retval;
            	    pushFollow(FOLLOW_orExpr_in_implExpr235);
            	    e2=orExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {

            	      		    HashSet rez = new HashSet(all_setS);
            	      		    rez.removeAll((e1!=null?e1.set:null));
            	      		    rez.addAll((e2!=null?e2.set:null));
            	      		    retval.set = rez;
            	      		    if (DEBUG)
            	      		    {
            	      		        trace("implExpr",1);
            	       		        printSet((e1!=null?input.toString(e1.start,e1.stop):null) + " => " + (e2!=null?input.toString(e2.start,e2.stop):null),rez); 
            	       		    }
            	      		  
            	    }

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException ex) {

                               reportError(ex);
                            
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "implExpr"

    public static class orExpr_return extends ParserRuleReturnScope {
        public HashSet set;
    };

    // $ANTLR start "orExpr"
    // .\\ATL-Compiler\\ATL.g:443:1: orExpr returns [HashSet set] : e1= andExpr ( 'or' e2= andExpr )* ;
    public final ATLParser.orExpr_return orExpr() throws RecognitionException {
        ATLParser.orExpr_return retval = new ATLParser.orExpr_return();
        retval.start = input.LT(1);

        ATLParser.andExpr_return e1 = null;

        ATLParser.andExpr_return e2 = null;

        try {
            // .\\ATL-Compiler\\ATL.g:444:2: (e1= andExpr ( 'or' e2= andExpr )* )
            // .\\ATL-Compiler\\ATL.g:444:4: e1= andExpr ( 'or' e2= andExpr )*
            {
            pushFollow(FOLLOW_andExpr_in_orExpr314);
            e1=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              retval.set = new HashSet((e1!=null?e1.set:null));
            }
            // .\\ATL-Compiler\\ATL.g:445:10: ( 'or' e2= andExpr )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==13) ) {
                    alt3=1;
                }

                switch (alt3) {
            	case 1 :
            	    // .\\ATL-Compiler\\ATL.g:445:12: 'or' e2= andExpr
            	    {
            	    match(input,13,FOLLOW_13_in_orExpr329); if (state.failed) return retval;
            	    pushFollow(FOLLOW_andExpr_in_orExpr333);
            	    e2=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {

            	      		    HashSet rez = new HashSet((e1!=null?e1.set:null));
            	      		    rez.addAll((e2!=null?e2.set:null));
            	      		    retval.set = rez;
            	      		    if (DEBUG)
            	      		    {
            	      		        trace("orExpr",1);
            	      		        printSet((e1!=null?input.toString(e1.start,e1.stop):null) + " or " + (e2!=null?input.toString(e2.start,e2.stop):null),rez);
            	      		    }
            	      		  
            	    }

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException ex) {

                               reportError(ex);
                            
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "orExpr"

    public static class andExpr_return extends ParserRuleReturnScope {
        public HashSet set;
    };

    // $ANTLR start "andExpr"
    // .\\ATL-Compiler\\ATL.g:461:1: andExpr returns [HashSet set] : e1= notExpr ( 'and' e2= notExpr )* ;
    public final ATLParser.andExpr_return andExpr() throws RecognitionException {
        ATLParser.andExpr_return retval = new ATLParser.andExpr_return();
        retval.start = input.LT(1);

        ATLParser.notExpr_return e1 = null;

        ATLParser.notExpr_return e2 = null;

        try {
            // .\\ATL-Compiler\\ATL.g:462:2: (e1= notExpr ( 'and' e2= notExpr )* )
            // .\\ATL-Compiler\\ATL.g:462:4: e1= notExpr ( 'and' e2= notExpr )*
            {
            pushFollow(FOLLOW_notExpr_in_andExpr387);
            e1=notExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              retval.set = new HashSet((e1!=null?e1.set:null));
            }
            // .\\ATL-Compiler\\ATL.g:463:10: ( 'and' e2= notExpr )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==14) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // .\\ATL-Compiler\\ATL.g:463:12: 'and' e2= notExpr
            	    {
            	    match(input,14,FOLLOW_14_in_andExpr402); if (state.failed) return retval;
            	    pushFollow(FOLLOW_notExpr_in_andExpr406);
            	    e2=notExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {

            	      		    HashSet rez = new HashSet((e1!=null?e1.set:null));
            	      		    rez.retainAll((e2!=null?e2.set:null));
            	      		    retval.set = rez;
            	      		    if (DEBUG)
            	      		    {
            	      		        trace("andExpr",1);
            	      		        printSet((e1!=null?input.toString(e1.start,e1.stop):null) + " and " + (e2!=null?input.toString(e2.start,e2.stop):null),rez);
            	      		    }		    
            	      		  
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException ex) {

                               reportError(ex);
                            
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "andExpr"

    public static class notExpr_return extends ParserRuleReturnScope {
        public HashSet set;
    };

    // $ANTLR start "notExpr"
    // .\\ATL-Compiler\\ATL.g:479:1: notExpr returns [HashSet set] : ( 'not' e= atomExp | e= atomExp );
    public final ATLParser.notExpr_return notExpr() throws RecognitionException {
        ATLParser.notExpr_return retval = new ATLParser.notExpr_return();
        retval.start = input.LT(1);

        ATLParser.atomExp_return e = null;


        try {
            // .\\ATL-Compiler\\ATL.g:480:2: ( 'not' e= atomExp | e= atomExp )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==15) ) {
                alt5=1;
            }
            else if ( (LA5_0==AP||LA5_0==16||(LA5_0>=18 && LA5_0<=19)) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // .\\ATL-Compiler\\ATL.g:480:10: 'not' e= atomExp
                    {
                    match(input,15,FOLLOW_15_in_notExpr463); if (state.failed) return retval;
                    pushFollow(FOLLOW_atomExp_in_notExpr468);
                    e=atomExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      		    HashSet rez = new HashSet(all_setS);
                      		    rez.removeAll((e!=null?e.set:null));
                      		    retval.set = rez;
                      		    if (DEBUG)
                      		    {
                         		        trace("notExpr",1);
                      		        printSet(" not " + (e!=null?input.toString(e.start,e.stop):null),rez);  
                      		    }
                      		
                    }

                    }
                    break;
                case 2 :
                    // .\\ATL-Compiler\\ATL.g:491:4: e= atomExp
                    {
                    pushFollow(FOLLOW_atomExp_in_notExpr479);
                    e=atomExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       
                      		    retval.set = new HashSet((e!=null?e.set:null));
                      		    if (DEBUG)
                      		    {
                      		        trace("notExpr",2);
                      		        printSet((e!=null?input.toString(e.start,e.stop):null),retval.set); 
                      		    }
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException ex) {

                               reportError(ex);
                            
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "notExpr"

    public static class atomExp_return extends ParserRuleReturnScope {
        public HashSet set;
    };

    // $ANTLR start "atomExp"
    // .\\ATL-Compiler\\ATL.g:504:1: atomExp returns [HashSet set] : ( '(' f= atlFormula ')' | AP | 'true' | 'false' );
    public final ATLParser.atomExp_return atomExp() throws RecognitionException {
        ATLParser.atomExp_return retval = new ATLParser.atomExp_return();
        retval.start = input.LT(1);

        Token AP1=null;
        ATLParser.atlFormula_return f = null;


        try {
            // .\\ATL-Compiler\\ATL.g:505:2: ( '(' f= atlFormula ')' | AP | 'true' | 'false' )
            int alt6=4;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt6=1;
                }
                break;
            case AP:
                {
                alt6=2;
                }
                break;
            case 18:
                {
                alt6=3;
                }
                break;
            case 19:
                {
                alt6=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // .\\ATL-Compiler\\ATL.g:505:10: '(' f= atlFormula ')'
                    {
                    match(input,16,FOLLOW_16_in_atomExp520); if (state.failed) return retval;
                    pushFollow(FOLLOW_atlFormula_in_atomExp524);
                    f=atlFormula();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,17,FOLLOW_17_in_atomExp526); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      	            retval.set = new HashSet((f!=null?f.set:null));
                      	            if (DEBUG)
                      	            {
                            		        trace("atomExp",1);  
                      		        printSet("("+(f!=null?input.toString(f.start,f.stop):null)+")",retval.set); 
                      		    }
                      		
                    }

                    }
                    break;
                case 2 :
                    // .\\ATL-Compiler\\ATL.g:515:4: AP
                    {
                    AP1=(Token)match(input,AP,FOLLOW_AP_in_atomExp540); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      		    HashSet rez = new HashSet();
                      	            for (int i=0; i < NR_STATES; i++)
                      	              if (p[i].contains((String)(AP1!=null?AP1.getText():null))) rez.add(new Integer(i)); 
                      	            retval.set = rez;
                      	            if (DEBUG)
                      	            {
                            		        trace("atomExp",2);  
                      	                printSet((AP1!=null?AP1.getText():null),rez);  
                                          }
                      	        
                    }

                    }
                    break;
                case 3 :
                    // .\\ATL-Compiler\\ATL.g:527:4: 'true'
                    {
                    match(input,18,FOLLOW_18_in_atomExp550); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      		    HashSet rez = new HashSet(all_setS);
                      		    retval.set = rez;  
                      		    if (DEBUG)
                      		    {
                            		        trace("atomExp",3);  
                                              printSet(" true ",rez);  
                      	            }
                      	        
                    }

                    }
                    break;
                case 4 :
                    // .\\ATL-Compiler\\ATL.g:537:4: 'false'
                    {
                    match(input,19,FOLLOW_19_in_atomExp560); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                      		    retval.set = new HashSet();
                      		    if (DEBUG)
                      		    {
                            		        trace("atomExp",4);  		    
                                              printSet(" false ",retval.set);  
                                          }
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException ex) {

                               reportError(ex);
                            
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atomExp"

    // Delegated rules
    
    public static final BitSet FOLLOW_7_in_atlFormula65 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_implExpr_in_atlFormula69 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_atlFormula71 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_implExpr_in_atlFormula75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_atlFormula91 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_implExpr_in_atlFormula95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_atlFormula118 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_implExpr_in_atlFormula122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_atlFormula145 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_implExpr_in_atlFormula149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_implExpr_in_atlFormula170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_implExpr216 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_12_in_implExpr231 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_orExpr_in_implExpr235 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr314 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_orExpr329 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_andExpr_in_orExpr333 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_notExpr_in_andExpr387 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14_in_andExpr402 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_notExpr_in_andExpr406 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_15_in_notExpr463 = new BitSet(new long[]{0x00000000000D8010L});
    public static final BitSet FOLLOW_atomExp_in_notExpr468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomExp_in_notExpr479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_atomExp520 = new BitSet(new long[]{0x00000000000D8E90L});
    public static final BitSet FOLLOW_atlFormula_in_atomExp524 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_atomExp526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AP_in_atomExp540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_atomExp550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_atomExp560 = new BitSet(new long[]{0x0000000000000002L});

}