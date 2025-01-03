/**
 * This is the embedded version of the ATL model checker. 
 * Web services version: http://use-it.ro/
 * @authors Stoica L&F
 *
 */

import org.antlr.runtime.*;
public class ATLLexer extends Lexer {
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int WS=6;
    public static final int T__15=15;
    public static final int NEWLINE=5;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;
    public static final int AP=4;

            @Override
            public void reportError(RecognitionException re) {
               throw new RuntimeException("Lexical error!\n\n" +
                                          "Position: " + re.line + ":" + re.charPositionInLine + " erroneous character: '" + (char)re.c + "'"); 
            }

    public ATLLexer() {;} 
    public ATLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ATLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return ".\\ATL-Compiler\\ATL.g"; }

    // $ANTLR start "T__7"
    public final void mT__7() throws RecognitionException {
        try {
            int _type = T__7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("<<A>> "); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__7"

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match('U'); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("<<A>>~"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("<<A>>@"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("<<A>>#"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("=>"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("or"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("and"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("not"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match('('); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match(')'); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("true"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            match("false"); 
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "AP"
    public final void mAP() throws RecognitionException {
        try {
            int _type = AP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='!'||(LA1_0>='$' && LA1_0<='&')||LA1_0=='*'||LA1_0=='-'||(LA1_0>='/' && LA1_0<='9')||LA1_0=='?'||(LA1_0>='A' && LA1_0<='[')||(LA1_0>=']' && LA1_0<='_')||(LA1_0>='a' && LA1_0<='~')) ) {
                    alt1=1;
                }

                switch (alt1) {
            	case 1 :
            	    {
            	    if ( input.LA(1)=='!'||(input.LA(1)>='$' && input.LA(1)<='&')||input.LA(1)=='*'||input.LA(1)=='-'||(input.LA(1)>='/' && input.LA(1)<='9')||input.LA(1)=='?'||(input.LA(1)>='A' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='~') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}
            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);
            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AP"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\r') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    {
                    match('\r'); 
                    }
                    break;
            }

            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\t'||LA3_0==' ') ) {
                    alt3=1;
                }

                switch (alt3) {
            	case 1 :
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}
            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        int alt4=16;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                {
                mT__7(); 
                }
                break;
            case 2 :
                {
                mT__8(); 
                }
                break;
            case 3 :
                {
                mT__9(); 
                }
                break;
            case 4 :
                {
                mT__10(); 
                }
                break;
            case 5 :
                {
                mT__11(); 
                }
                break;
            case 6 :
                {
                mT__12(); 
                }
                break;
            case 7 :
                {
                mT__13(); 
                }
                break;
            case 8 :
                {
                mT__14(); 
                }
                break;
            case 9 :
                {
                mT__15(); 
                }
                break;
            case 10 :
                {
                mT__16(); 
                }
                break;
            case 11 :
                {
                mT__17(); 
                }
                break;
            case 12 :
                {
                mT__18(); 
                }
                break;
            case 13 :
                {
                mT__19(); 
                }
                break;
            case 14 :
                {
                mAP(); 
                }
                break;
            case 15 :
                {
                mNEWLINE(); 
                }
                break;
            case 16 :
                {
                mWS(); 
                }
                break;
        }
    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\2\uffff\1\17\1\uffff\3\13\2\uffff\2\13\5\uffff\1\26\4\13\2\uffff"+
        "\1\34\1\35\2\13\3\uffff\1\41\1\13\2\uffff\1\47\5\uffff";
    static final String DFA4_eofS =
        "\50\uffff";
    static final String DFA4_minS =
        "\1\11\1\74\1\41\1\uffff\1\162\1\156\1\157\2\uffff\1\162\1\141\3"+
        "\uffff\1\101\1\uffff\1\41\1\144\1\164\1\165\1\154\1\76\1\uffff\2"+
        "\41\1\145\1\163\1\76\2\uffff\1\41\1\145\1\40\1\uffff\1\41\5\uffff";
    static final String DFA4_maxS =
        "\1\176\1\74\1\176\1\uffff\1\162\1\156\1\157\2\uffff\1\162\1\141"+
        "\3\uffff\1\101\1\uffff\1\176\1\144\1\164\1\165\1\154\1\76\1\uffff"+
        "\2\176\1\145\1\163\1\76\2\uffff\1\176\1\145\1\176\1\uffff\1\176"+
        "\5\uffff";
    static final String DFA4_acceptS =
        "\3\uffff\1\6\3\uffff\1\12\1\13\2\uffff\1\16\1\17\1\20\1\uffff\1"+
        "\2\6\uffff\1\7\5\uffff\1\10\1\11\3\uffff\1\14\1\uffff\1\1\1\3\1"+
        "\4\1\5\1\15";
    static final String DFA4_specialS =
        "\50\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\15\1\14\2\uffff\1\14\22\uffff\1\15\1\13\2\uffff\3\13\1\uffff"+
            "\1\7\1\10\1\13\2\uffff\1\13\1\uffff\13\13\2\uffff\1\1\1\3\1"+
            "\uffff\1\13\1\uffff\24\13\1\2\6\13\1\uffff\3\13\1\uffff\1\5"+
            "\4\13\1\12\7\13\1\6\1\4\4\13\1\11\12\13",
            "\1\16",
            "\1\13\2\uffff\3\13\3\uffff\1\13\2\uffff\1\13\1\uffff\13\13"+
            "\5\uffff\1\13\1\uffff\33\13\1\uffff\3\13\1\uffff\36\13",
            "",
            "\1\20",
            "\1\21",
            "\1\22",
            "",
            "",
            "\1\23",
            "\1\24",
            "",
            "",
            "",
            "\1\25",
            "",
            "\1\13\2\uffff\3\13\3\uffff\1\13\2\uffff\1\13\1\uffff\13\13"+
            "\5\uffff\1\13\1\uffff\33\13\1\uffff\3\13\1\uffff\36\13",
            "\1\27",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "",
            "\1\13\2\uffff\3\13\3\uffff\1\13\2\uffff\1\13\1\uffff\13\13"+
            "\5\uffff\1\13\1\uffff\33\13\1\uffff\3\13\1\uffff\36\13",
            "\1\13\2\uffff\3\13\3\uffff\1\13\2\uffff\1\13\1\uffff\13\13"+
            "\5\uffff\1\13\1\uffff\33\13\1\uffff\3\13\1\uffff\36\13",
            "\1\36",
            "\1\37",
            "\1\40",
            "",
            "",
            "\1\13\2\uffff\3\13\3\uffff\1\13\2\uffff\1\13\1\uffff\13\13"+
            "\5\uffff\1\13\1\uffff\33\13\1\uffff\3\13\1\uffff\36\13",
            "\1\42",
            "\1\43\2\uffff\1\46\34\uffff\1\45\75\uffff\1\44",
            "",
            "\1\13\2\uffff\3\13\3\uffff\1\13\2\uffff\1\13\1\uffff\13\13"+
            "\5\uffff\1\13\1\uffff\33\13\1\uffff\3\13\1\uffff\36\13",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | AP | NEWLINE | WS );";
        }
    }
}