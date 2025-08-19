// Generated from io\github\synte\aliva\parser\ScraperDSL.g4 by ANTLR 4.13.0
package io.github.synte.aliva.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ScraperDSLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, STRING_TYPE=34, NUMBER_TYPE=35, BOOLEAN_TYPE=36, LIST_TYPE=37, 
		MAP_TYPE=38, BOOLEAN=39, NULL=40, NUMBER=41, STRING=42, ID=43, ARGUMENTS=44, 
		WS=45, COMMENT=46;
	public static final int
		RULE_script = 0, RULE_statement = 1, RULE_varDecl = 2, RULE_assignment = 3, 
		RULE_ifStatement = 4, RULE_whileStatement = 5, RULE_forStatement = 6, 
		RULE_block = 7, RULE_expression = 8, RULE_logicalOrExpr = 9, RULE_logicalAndExpr = 10, 
		RULE_equalityExpr = 11, RULE_comparisonExpr = 12, RULE_additiveExpr = 13, 
		RULE_multiplicativeExpr = 14, RULE_unaryExpr = 15, RULE_primary = 16, 
		RULE_functionLiteral = 17, RULE_literal = 18, RULE_listLiteral = 19, RULE_mapLiteral = 20, 
		RULE_mapEntry = 21, RULE_variableRef = 22, RULE_funcCall = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"script", "statement", "varDecl", "assignment", "ifStatement", "whileStatement", 
			"forStatement", "block", "expression", "logicalOrExpr", "logicalAndExpr", 
			"equalityExpr", "comparisonExpr", "additiveExpr", "multiplicativeExpr", 
			"unaryExpr", "primary", "functionLiteral", "literal", "listLiteral", 
			"mapLiteral", "mapEntry", "variableRef", "funcCall"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "','", "')'", "'break'", "';'", "'continue'", "'='", "'['", 
			"']'", "'if'", "'else'", "'while'", "'for'", "'in'", "'{'", "'}'", "'?'", 
			"':'", "'||'", "'&&'", "'=='", "'!='", "'<'", "'<='", "'>'", "'>='", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "'fun'", "'string'", "'number'", 
			"'boolean'", "'list'", "'map'", null, "'null'", null, null, null, "'arguments'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "STRING_TYPE", 
			"NUMBER_TYPE", "BOOLEAN_TYPE", "LIST_TYPE", "MAP_TYPE", "BOOLEAN", "NULL", 
			"NUMBER", "STRING", "ID", "ARGUMENTS", "WS", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ScraperDSL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ScraperDSLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ScriptContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ScraperDSLParser.EOF, 0); }
		public TerminalNode ARGUMENTS() { return getToken(ScraperDSLParser.ARGUMENTS, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(ScraperDSLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ScraperDSLParser.ID, i);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterScript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitScript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitScript(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARGUMENTS) {
				{
				setState(48);
				match(ARGUMENTS);
				setState(49);
				match(T__0);
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(50);
					match(ID);
					setState(55);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(51);
						match(T__1);
						setState(52);
						match(ID);
						}
						}
						setState(57);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(60);
				match(T__2);
				}
			}

			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9328668980304L) != 0)) {
				{
				{
				setState(63);
				statement();
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				varDecl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				assignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(73);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(74);
				whileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(75);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(76);
				match(T__3);
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(77);
					match(T__4);
					}
				}

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(80);
				match(T__5);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(81);
					match(T__4);
					}
				}

				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(84);
				funcCall();
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(85);
					match(T__4);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ScraperDSLParser.ID, 0); }
		public TerminalNode STRING_TYPE() { return getToken(ScraperDSLParser.STRING_TYPE, 0); }
		public TerminalNode NUMBER_TYPE() { return getToken(ScraperDSLParser.NUMBER_TYPE, 0); }
		public TerminalNode BOOLEAN_TYPE() { return getToken(ScraperDSLParser.BOOLEAN_TYPE, 0); }
		public TerminalNode LIST_TYPE() { return getToken(ScraperDSLParser.LIST_TYPE, 0); }
		public TerminalNode MAP_TYPE() { return getToken(ScraperDSLParser.MAP_TYPE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitVarDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 532575944704L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(91);
			match(ID);
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(92);
				match(T__6);
				setState(93);
				expression();
				}
			}

			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(96);
				match(T__4);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ScraperDSLParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignment);
		int _la;
		try {
			setState(114);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(ID);
				setState(100);
				match(T__6);
				setState(101);
				expression();
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(102);
					match(T__4);
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(ID);
				setState(106);
				match(T__7);
				setState(107);
				expression();
				setState(108);
				match(T__8);
				setState(109);
				match(T__6);
				setState(110);
				expression();
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(111);
					match(T__4);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(T__9);
			setState(117);
			match(T__0);
			setState(118);
			expression();
			setState(119);
			match(T__2);
			setState(120);
			block();
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(121);
				match(T__10);
				setState(122);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(T__11);
			setState(126);
			match(T__0);
			setState(127);
			expression();
			setState(128);
			match(T__2);
			setState(129);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ScraperDSLParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(T__12);
			setState(132);
			match(T__0);
			setState(133);
			match(ID);
			setState(134);
			match(T__13);
			setState(135);
			expression();
			setState(136);
			match(T__2);
			setState(137);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(T__14);
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9328668980304L) != 0)) {
				{
				{
				setState(140);
				statement();
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(146);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public LogicalOrExprContext logicalOrExpr() {
			return getRuleContext(LogicalOrExprContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			logicalOrExpr();
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__16) {
				{
				setState(149);
				match(T__16);
				setState(150);
				expression();
				setState(151);
				match(T__17);
				setState(152);
				expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalOrExprContext extends ParserRuleContext {
		public List<LogicalAndExprContext> logicalAndExpr() {
			return getRuleContexts(LogicalAndExprContext.class);
		}
		public LogicalAndExprContext logicalAndExpr(int i) {
			return getRuleContext(LogicalAndExprContext.class,i);
		}
		public LogicalOrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterLogicalOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitLogicalOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitLogicalOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExprContext logicalOrExpr() throws RecognitionException {
		LogicalOrExprContext _localctx = new LogicalOrExprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_logicalOrExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			logicalAndExpr();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(157);
				match(T__18);
				setState(158);
				logicalAndExpr();
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndExprContext extends ParserRuleContext {
		public List<EqualityExprContext> equalityExpr() {
			return getRuleContexts(EqualityExprContext.class);
		}
		public EqualityExprContext equalityExpr(int i) {
			return getRuleContext(EqualityExprContext.class,i);
		}
		public LogicalAndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterLogicalAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitLogicalAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitLogicalAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExprContext logicalAndExpr() throws RecognitionException {
		LogicalAndExprContext _localctx = new LogicalAndExprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_logicalAndExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			equalityExpr();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(165);
				match(T__19);
				setState(166);
				equalityExpr();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprContext extends ParserRuleContext {
		public Token op;
		public List<ComparisonExprContext> comparisonExpr() {
			return getRuleContexts(ComparisonExprContext.class);
		}
		public ComparisonExprContext comparisonExpr(int i) {
			return getRuleContext(ComparisonExprContext.class,i);
		}
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitEqualityExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_equalityExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			comparisonExpr();
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__20 || _la==T__21) {
				{
				{
				setState(173);
				((EqualityExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__20 || _la==T__21) ) {
					((EqualityExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(174);
				comparisonExpr();
				}
				}
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExprContext extends ParserRuleContext {
		public Token op;
		public List<AdditiveExprContext> additiveExpr() {
			return getRuleContexts(AdditiveExprContext.class);
		}
		public AdditiveExprContext additiveExpr(int i) {
			return getRuleContext(AdditiveExprContext.class,i);
		}
		public ComparisonExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterComparisonExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitComparisonExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitComparisonExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExprContext comparisonExpr() throws RecognitionException {
		ComparisonExprContext _localctx = new ComparisonExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_comparisonExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			additiveExpr();
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 125829120L) != 0)) {
				{
				{
				setState(181);
				((ComparisonExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 125829120L) != 0)) ) {
					((ComparisonExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(182);
				additiveExpr();
				}
				}
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExprContext extends ParserRuleContext {
		public Token op;
		public List<MultiplicativeExprContext> multiplicativeExpr() {
			return getRuleContexts(MultiplicativeExprContext.class);
		}
		public MultiplicativeExprContext multiplicativeExpr(int i) {
			return getRuleContext(MultiplicativeExprContext.class,i);
		}
		public AdditiveExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterAdditiveExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitAdditiveExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitAdditiveExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExprContext additiveExpr() throws RecognitionException {
		AdditiveExprContext _localctx = new AdditiveExprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_additiveExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			multiplicativeExpr();
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__26 || _la==T__27) {
				{
				{
				setState(189);
				((AdditiveExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
					((AdditiveExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(190);
				multiplicativeExpr();
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicativeExprContext extends ParserRuleContext {
		public Token op;
		public List<UnaryExprContext> unaryExpr() {
			return getRuleContexts(UnaryExprContext.class);
		}
		public UnaryExprContext unaryExpr(int i) {
			return getRuleContext(UnaryExprContext.class,i);
		}
		public MultiplicativeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterMultiplicativeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitMultiplicativeExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitMultiplicativeExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExprContext multiplicativeExpr() throws RecognitionException {
		MultiplicativeExprContext _localctx = new MultiplicativeExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_multiplicativeExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			unaryExpr();
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3758096384L) != 0)) {
				{
				{
				setState(197);
				((MultiplicativeExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3758096384L) != 0)) ) {
					((MultiplicativeExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(198);
				unaryExpr();
				}
				}
				setState(203);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExprContext extends ParserRuleContext {
		public Token op;
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitUnaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_unaryExpr);
		int _la;
		try {
			setState(207);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				((UnaryExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__27 || _la==T__31) ) {
					((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(205);
				unaryExpr();
				}
				break;
			case T__0:
			case T__7:
			case T__14:
			case T__32:
			case BOOLEAN:
			case NULL:
			case NUMBER:
			case STRING:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				primary(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ListLiteralContext listLiteral() {
			return getRuleContext(ListLiteralContext.class,0);
		}
		public MapLiteralContext mapLiteral() {
			return getRuleContext(MapLiteralContext.class,0);
		}
		public VariableRefContext variableRef() {
			return getRuleContext(VariableRefContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public FunctionLiteralContext functionLiteral() {
			return getRuleContext(FunctionLiteralContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		return primary(0);
	}

	private PrimaryContext primary(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryContext _localctx = new PrimaryContext(_ctx, _parentState);
		PrimaryContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_primary, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(210);
				literal();
				}
				break;
			case 2:
				{
				setState(211);
				listLiteral();
				}
				break;
			case 3:
				{
				setState(212);
				mapLiteral();
				}
				break;
			case 4:
				{
				setState(213);
				variableRef();
				}
				break;
			case 5:
				{
				setState(214);
				funcCall();
				}
				break;
			case 6:
				{
				setState(215);
				functionLiteral();
				}
				break;
			case 7:
				{
				setState(216);
				match(T__0);
				setState(217);
				expression();
				setState(218);
				match(T__2);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(229);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PrimaryContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_primary);
					setState(222);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(223);
					match(T__7);
					setState(224);
					expression();
					setState(225);
					match(T__8);
					}
					} 
				}
				setState(231);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionLiteralContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterFunctionLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitFunctionLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitFunctionLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionLiteralContext functionLiteral() throws RecognitionException {
		FunctionLiteralContext _localctx = new FunctionLiteralContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_functionLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(T__32);
			setState(233);
			match(T__0);
			setState(234);
			match(T__2);
			setState(235);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(ScraperDSLParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(ScraperDSLParser.NUMBER, 0); }
		public TerminalNode BOOLEAN() { return getToken(ScraperDSLParser.BOOLEAN, 0); }
		public TerminalNode NULL() { return getToken(ScraperDSLParser.NULL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8246337208320L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListLiteralContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ListLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitListLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitListLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListLiteralContext listLiteral() throws RecognitionException {
		ListLiteralContext _localctx = new ListLiteralContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_listLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(T__7);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17055583600898L) != 0)) {
				{
				setState(240);
				expression();
				setState(245);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(241);
					match(T__1);
					setState(242);
					expression();
					}
					}
					setState(247);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(250);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapLiteralContext extends ParserRuleContext {
		public List<MapEntryContext> mapEntry() {
			return getRuleContexts(MapEntryContext.class);
		}
		public MapEntryContext mapEntry(int i) {
			return getRuleContext(MapEntryContext.class,i);
		}
		public MapLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterMapLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitMapLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitMapLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapLiteralContext mapLiteral() throws RecognitionException {
		MapLiteralContext _localctx = new MapLiteralContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_mapLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(T__14);
			setState(261);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(253);
				mapEntry();
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(254);
					match(T__1);
					setState(255);
					mapEntry();
					}
					}
					setState(260);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(263);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapEntryContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(ScraperDSLParser.STRING, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterMapEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitMapEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitMapEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			match(STRING);
			setState(266);
			match(T__17);
			setState(267);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableRefContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ScraperDSLParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterVariableRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitVariableRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitVariableRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableRefContext variableRef() throws RecognitionException {
		VariableRefContext _localctx = new VariableRefContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_variableRef);
		try {
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(269);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				match(ID);
				setState(271);
				match(T__7);
				setState(272);
				expression();
				setState(273);
				match(T__8);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncCallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ScraperDSLParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).enterFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScraperDSLListener ) ((ScraperDSLListener)listener).exitFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScraperDSLVisitor ) return ((ScraperDSLVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(ID);
			setState(278);
			match(T__0);
			setState(287);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17055583600898L) != 0)) {
				{
				setState(279);
				expression();
				setState(284);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(280);
					match(T__1);
					setState(281);
					expression();
					}
					}
					setState(286);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(289);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return primary_sempred((PrimaryContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean primary_sempred(PrimaryContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001.\u0124\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u00006\b\u0000\n\u0000\f\u0000"+
		"9\t\u0000\u0003\u0000;\b\u0000\u0001\u0000\u0003\u0000>\b\u0000\u0001"+
		"\u0000\u0005\u0000A\b\u0000\n\u0000\f\u0000D\t\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001O\b\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u0001S\b\u0001\u0001\u0001\u0001\u0001\u0003\u0001W\b\u0001\u0003\u0001"+
		"Y\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"_\b\u0002\u0001\u0002\u0003\u0002b\b\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003h\b\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003q\b"+
		"\u0003\u0003\u0003s\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004|\b\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0005\u0007\u008e\b\u0007\n"+
		"\u0007\f\u0007\u0091\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0003\b\u009b\b\b\u0001\t\u0001\t\u0001\t\u0005"+
		"\t\u00a0\b\t\n\t\f\t\u00a3\t\t\u0001\n\u0001\n\u0001\n\u0005\n\u00a8\b"+
		"\n\n\n\f\n\u00ab\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00b0"+
		"\b\u000b\n\u000b\f\u000b\u00b3\t\u000b\u0001\f\u0001\f\u0001\f\u0005\f"+
		"\u00b8\b\f\n\f\f\f\u00bb\t\f\u0001\r\u0001\r\u0001\r\u0005\r\u00c0\b\r"+
		"\n\r\f\r\u00c3\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00c8"+
		"\b\u000e\n\u000e\f\u000e\u00cb\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0003\u000f\u00d0\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0003\u0010\u00dd\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0005\u0010\u00e4\b\u0010\n\u0010\f\u0010\u00e7"+
		"\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005"+
		"\u0013\u00f4\b\u0013\n\u0013\f\u0013\u00f7\t\u0013\u0003\u0013\u00f9\b"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0005\u0014\u0101\b\u0014\n\u0014\f\u0014\u0104\t\u0014\u0003\u0014"+
		"\u0106\b\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0003\u0016\u0114\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0005\u0017\u011b\b\u0017\n\u0017\f\u0017\u011e"+
		"\t\u0017\u0003\u0017\u0120\b\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0000\u0001 \u0018\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \"$&(*,.\u0000\u0007\u0001\u0000\"&\u0001"+
		"\u0000\u0015\u0016\u0001\u0000\u0017\u001a\u0001\u0000\u001b\u001c\u0001"+
		"\u0000\u001d\u001f\u0002\u0000\u001c\u001c  \u0001\u0000\'*\u0136\u0000"+
		"=\u0001\u0000\u0000\u0000\u0002X\u0001\u0000\u0000\u0000\u0004Z\u0001"+
		"\u0000\u0000\u0000\u0006r\u0001\u0000\u0000\u0000\bt\u0001\u0000\u0000"+
		"\u0000\n}\u0001\u0000\u0000\u0000\f\u0083\u0001\u0000\u0000\u0000\u000e"+
		"\u008b\u0001\u0000\u0000\u0000\u0010\u0094\u0001\u0000\u0000\u0000\u0012"+
		"\u009c\u0001\u0000\u0000\u0000\u0014\u00a4\u0001\u0000\u0000\u0000\u0016"+
		"\u00ac\u0001\u0000\u0000\u0000\u0018\u00b4\u0001\u0000\u0000\u0000\u001a"+
		"\u00bc\u0001\u0000\u0000\u0000\u001c\u00c4\u0001\u0000\u0000\u0000\u001e"+
		"\u00cf\u0001\u0000\u0000\u0000 \u00dc\u0001\u0000\u0000\u0000\"\u00e8"+
		"\u0001\u0000\u0000\u0000$\u00ed\u0001\u0000\u0000\u0000&\u00ef\u0001\u0000"+
		"\u0000\u0000(\u00fc\u0001\u0000\u0000\u0000*\u0109\u0001\u0000\u0000\u0000"+
		",\u0113\u0001\u0000\u0000\u0000.\u0115\u0001\u0000\u0000\u000001\u0005"+
		",\u0000\u00001:\u0005\u0001\u0000\u000027\u0005+\u0000\u000034\u0005\u0002"+
		"\u0000\u000046\u0005+\u0000\u000053\u0001\u0000\u0000\u000069\u0001\u0000"+
		"\u0000\u000075\u0001\u0000\u0000\u000078\u0001\u0000\u0000\u00008;\u0001"+
		"\u0000\u0000\u000097\u0001\u0000\u0000\u0000:2\u0001\u0000\u0000\u0000"+
		":;\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<>\u0005\u0003\u0000"+
		"\u0000=0\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>B\u0001\u0000"+
		"\u0000\u0000?A\u0003\u0002\u0001\u0000@?\u0001\u0000\u0000\u0000AD\u0001"+
		"\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000"+
		"CE\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000EF\u0005\u0000\u0000"+
		"\u0001F\u0001\u0001\u0000\u0000\u0000GY\u0003\u0004\u0002\u0000HY\u0003"+
		"\u0006\u0003\u0000IY\u0003\b\u0004\u0000JY\u0003\n\u0005\u0000KY\u0003"+
		"\f\u0006\u0000LN\u0005\u0004\u0000\u0000MO\u0005\u0005\u0000\u0000NM\u0001"+
		"\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OY\u0001\u0000\u0000\u0000"+
		"PR\u0005\u0006\u0000\u0000QS\u0005\u0005\u0000\u0000RQ\u0001\u0000\u0000"+
		"\u0000RS\u0001\u0000\u0000\u0000SY\u0001\u0000\u0000\u0000TV\u0003.\u0017"+
		"\u0000UW\u0005\u0005\u0000\u0000VU\u0001\u0000\u0000\u0000VW\u0001\u0000"+
		"\u0000\u0000WY\u0001\u0000\u0000\u0000XG\u0001\u0000\u0000\u0000XH\u0001"+
		"\u0000\u0000\u0000XI\u0001\u0000\u0000\u0000XJ\u0001\u0000\u0000\u0000"+
		"XK\u0001\u0000\u0000\u0000XL\u0001\u0000\u0000\u0000XP\u0001\u0000\u0000"+
		"\u0000XT\u0001\u0000\u0000\u0000Y\u0003\u0001\u0000\u0000\u0000Z[\u0007"+
		"\u0000\u0000\u0000[^\u0005+\u0000\u0000\\]\u0005\u0007\u0000\u0000]_\u0003"+
		"\u0010\b\u0000^\\\u0001\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_a"+
		"\u0001\u0000\u0000\u0000`b\u0005\u0005\u0000\u0000a`\u0001\u0000\u0000"+
		"\u0000ab\u0001\u0000\u0000\u0000b\u0005\u0001\u0000\u0000\u0000cd\u0005"+
		"+\u0000\u0000de\u0005\u0007\u0000\u0000eg\u0003\u0010\b\u0000fh\u0005"+
		"\u0005\u0000\u0000gf\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000"+
		"hs\u0001\u0000\u0000\u0000ij\u0005+\u0000\u0000jk\u0005\b\u0000\u0000"+
		"kl\u0003\u0010\b\u0000lm\u0005\t\u0000\u0000mn\u0005\u0007\u0000\u0000"+
		"np\u0003\u0010\b\u0000oq\u0005\u0005\u0000\u0000po\u0001\u0000\u0000\u0000"+
		"pq\u0001\u0000\u0000\u0000qs\u0001\u0000\u0000\u0000rc\u0001\u0000\u0000"+
		"\u0000ri\u0001\u0000\u0000\u0000s\u0007\u0001\u0000\u0000\u0000tu\u0005"+
		"\n\u0000\u0000uv\u0005\u0001\u0000\u0000vw\u0003\u0010\b\u0000wx\u0005"+
		"\u0003\u0000\u0000x{\u0003\u000e\u0007\u0000yz\u0005\u000b\u0000\u0000"+
		"z|\u0003\u000e\u0007\u0000{y\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000"+
		"\u0000|\t\u0001\u0000\u0000\u0000}~\u0005\f\u0000\u0000~\u007f\u0005\u0001"+
		"\u0000\u0000\u007f\u0080\u0003\u0010\b\u0000\u0080\u0081\u0005\u0003\u0000"+
		"\u0000\u0081\u0082\u0003\u000e\u0007\u0000\u0082\u000b\u0001\u0000\u0000"+
		"\u0000\u0083\u0084\u0005\r\u0000\u0000\u0084\u0085\u0005\u0001\u0000\u0000"+
		"\u0085\u0086\u0005+\u0000\u0000\u0086\u0087\u0005\u000e\u0000\u0000\u0087"+
		"\u0088\u0003\u0010\b\u0000\u0088\u0089\u0005\u0003\u0000\u0000\u0089\u008a"+
		"\u0003\u000e\u0007\u0000\u008a\r\u0001\u0000\u0000\u0000\u008b\u008f\u0005"+
		"\u000f\u0000\u0000\u008c\u008e\u0003\u0002\u0001\u0000\u008d\u008c\u0001"+
		"\u0000\u0000\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001"+
		"\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0092\u0001"+
		"\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0092\u0093\u0005"+
		"\u0010\u0000\u0000\u0093\u000f\u0001\u0000\u0000\u0000\u0094\u009a\u0003"+
		"\u0012\t\u0000\u0095\u0096\u0005\u0011\u0000\u0000\u0096\u0097\u0003\u0010"+
		"\b\u0000\u0097\u0098\u0005\u0012\u0000\u0000\u0098\u0099\u0003\u0010\b"+
		"\u0000\u0099\u009b\u0001\u0000\u0000\u0000\u009a\u0095\u0001\u0000\u0000"+
		"\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u0011\u0001\u0000\u0000"+
		"\u0000\u009c\u00a1\u0003\u0014\n\u0000\u009d\u009e\u0005\u0013\u0000\u0000"+
		"\u009e\u00a0\u0003\u0014\n\u0000\u009f\u009d\u0001\u0000\u0000\u0000\u00a0"+
		"\u00a3\u0001\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a1"+
		"\u00a2\u0001\u0000\u0000\u0000\u00a2\u0013\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a1\u0001\u0000\u0000\u0000\u00a4\u00a9\u0003\u0016\u000b\u0000\u00a5"+
		"\u00a6\u0005\u0014\u0000\u0000\u00a6\u00a8\u0003\u0016\u000b\u0000\u00a7"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a8\u00ab\u0001\u0000\u0000\u0000\u00a9"+
		"\u00a7\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa"+
		"\u0015\u0001\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ac"+
		"\u00b1\u0003\u0018\f\u0000\u00ad\u00ae\u0007\u0001\u0000\u0000\u00ae\u00b0"+
		"\u0003\u0018\f\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0\u00b3\u0001"+
		"\u0000\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b1\u00b2\u0001"+
		"\u0000\u0000\u0000\u00b2\u0017\u0001\u0000\u0000\u0000\u00b3\u00b1\u0001"+
		"\u0000\u0000\u0000\u00b4\u00b9\u0003\u001a\r\u0000\u00b5\u00b6\u0007\u0002"+
		"\u0000\u0000\u00b6\u00b8\u0003\u001a\r\u0000\u00b7\u00b5\u0001\u0000\u0000"+
		"\u0000\u00b8\u00bb\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000"+
		"\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u0019\u0001\u0000\u0000"+
		"\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bc\u00c1\u0003\u001c\u000e"+
		"\u0000\u00bd\u00be\u0007\u0003\u0000\u0000\u00be\u00c0\u0003\u001c\u000e"+
		"\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000\u00c0\u00c3\u0001\u0000\u0000"+
		"\u0000\u00c1\u00bf\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000"+
		"\u0000\u00c2\u001b\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c4\u00c9\u0003\u001e\u000f\u0000\u00c5\u00c6\u0007\u0004\u0000"+
		"\u0000\u00c6\u00c8\u0003\u001e\u000f\u0000\u00c7\u00c5\u0001\u0000\u0000"+
		"\u0000\u00c8\u00cb\u0001\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000"+
		"\u0000\u00c9\u00ca\u0001\u0000\u0000\u0000\u00ca\u001d\u0001\u0000\u0000"+
		"\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000\u00cc\u00cd\u0007\u0005\u0000"+
		"\u0000\u00cd\u00d0\u0003\u001e\u000f\u0000\u00ce\u00d0\u0003 \u0010\u0000"+
		"\u00cf\u00cc\u0001\u0000\u0000\u0000\u00cf\u00ce\u0001\u0000\u0000\u0000"+
		"\u00d0\u001f\u0001\u0000\u0000\u0000\u00d1\u00d2\u0006\u0010\uffff\uffff"+
		"\u0000\u00d2\u00dd\u0003$\u0012\u0000\u00d3\u00dd\u0003&\u0013\u0000\u00d4"+
		"\u00dd\u0003(\u0014\u0000\u00d5\u00dd\u0003,\u0016\u0000\u00d6\u00dd\u0003"+
		".\u0017\u0000\u00d7\u00dd\u0003\"\u0011\u0000\u00d8\u00d9\u0005\u0001"+
		"\u0000\u0000\u00d9\u00da\u0003\u0010\b\u0000\u00da\u00db\u0005\u0003\u0000"+
		"\u0000\u00db\u00dd\u0001\u0000\u0000\u0000\u00dc\u00d1\u0001\u0000\u0000"+
		"\u0000\u00dc\u00d3\u0001\u0000\u0000\u0000\u00dc\u00d4\u0001\u0000\u0000"+
		"\u0000\u00dc\u00d5\u0001\u0000\u0000\u0000\u00dc\u00d6\u0001\u0000\u0000"+
		"\u0000\u00dc\u00d7\u0001\u0000\u0000\u0000\u00dc\u00d8\u0001\u0000\u0000"+
		"\u0000\u00dd\u00e5\u0001\u0000\u0000\u0000\u00de\u00df\n\u0001\u0000\u0000"+
		"\u00df\u00e0\u0005\b\u0000\u0000\u00e0\u00e1\u0003\u0010\b\u0000\u00e1"+
		"\u00e2\u0005\t\u0000\u0000\u00e2\u00e4\u0001\u0000\u0000\u0000\u00e3\u00de"+
		"\u0001\u0000\u0000\u0000\u00e4\u00e7\u0001\u0000\u0000\u0000\u00e5\u00e3"+
		"\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6!\u0001"+
		"\u0000\u0000\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005"+
		"!\u0000\u0000\u00e9\u00ea\u0005\u0001\u0000\u0000\u00ea\u00eb\u0005\u0003"+
		"\u0000\u0000\u00eb\u00ec\u0003\u000e\u0007\u0000\u00ec#\u0001\u0000\u0000"+
		"\u0000\u00ed\u00ee\u0007\u0006\u0000\u0000\u00ee%\u0001\u0000\u0000\u0000"+
		"\u00ef\u00f8\u0005\b\u0000\u0000\u00f0\u00f5\u0003\u0010\b\u0000\u00f1"+
		"\u00f2\u0005\u0002\u0000\u0000\u00f2\u00f4\u0003\u0010\b\u0000\u00f3\u00f1"+
		"\u0001\u0000\u0000\u0000\u00f4\u00f7\u0001\u0000\u0000\u0000\u00f5\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000\u0000\u0000\u00f6\u00f9"+
		"\u0001\u0000\u0000\u0000\u00f7\u00f5\u0001\u0000\u0000\u0000\u00f8\u00f0"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa"+
		"\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005\t\u0000\u0000\u00fb\'\u0001"+
		"\u0000\u0000\u0000\u00fc\u0105\u0005\u000f\u0000\u0000\u00fd\u0102\u0003"+
		"*\u0015\u0000\u00fe\u00ff\u0005\u0002\u0000\u0000\u00ff\u0101\u0003*\u0015"+
		"\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0101\u0104\u0001\u0000\u0000"+
		"\u0000\u0102\u0100\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000"+
		"\u0000\u0103\u0106\u0001\u0000\u0000\u0000\u0104\u0102\u0001\u0000\u0000"+
		"\u0000\u0105\u00fd\u0001\u0000\u0000\u0000\u0105\u0106\u0001\u0000\u0000"+
		"\u0000\u0106\u0107\u0001\u0000\u0000\u0000\u0107\u0108\u0005\u0010\u0000"+
		"\u0000\u0108)\u0001\u0000\u0000\u0000\u0109\u010a\u0005*\u0000\u0000\u010a"+
		"\u010b\u0005\u0012\u0000\u0000\u010b\u010c\u0003\u0010\b\u0000\u010c+"+
		"\u0001\u0000\u0000\u0000\u010d\u0114\u0005+\u0000\u0000\u010e\u010f\u0005"+
		"+\u0000\u0000\u010f\u0110\u0005\b\u0000\u0000\u0110\u0111\u0003\u0010"+
		"\b\u0000\u0111\u0112\u0005\t\u0000\u0000\u0112\u0114\u0001\u0000\u0000"+
		"\u0000\u0113\u010d\u0001\u0000\u0000\u0000\u0113\u010e\u0001\u0000\u0000"+
		"\u0000\u0114-\u0001\u0000\u0000\u0000\u0115\u0116\u0005+\u0000\u0000\u0116"+
		"\u011f\u0005\u0001\u0000\u0000\u0117\u011c\u0003\u0010\b\u0000\u0118\u0119"+
		"\u0005\u0002\u0000\u0000\u0119\u011b\u0003\u0010\b\u0000\u011a\u0118\u0001"+
		"\u0000\u0000\u0000\u011b\u011e\u0001\u0000\u0000\u0000\u011c\u011a\u0001"+
		"\u0000\u0000\u0000\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u0120\u0001"+
		"\u0000\u0000\u0000\u011e\u011c\u0001\u0000\u0000\u0000\u011f\u0117\u0001"+
		"\u0000\u0000\u0000\u011f\u0120\u0001\u0000\u0000\u0000\u0120\u0121\u0001"+
		"\u0000\u0000\u0000\u0121\u0122\u0005\u0003\u0000\u0000\u0122/\u0001\u0000"+
		"\u0000\u0000 7:=BNRVX^agpr{\u008f\u009a\u00a1\u00a9\u00b1\u00b9\u00c1"+
		"\u00c9\u00cf\u00dc\u00e5\u00f5\u00f8\u0102\u0105\u0113\u011c\u011f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}