// Generated from io\github\synte\aliva\parser\ScraperDSL.g4 by ANTLR 4.13.0
package io.github.synte.aliva.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ScraperDSLParser}.
 */
public interface ScraperDSLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(ScraperDSLParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(ScraperDSLParser.ScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ScraperDSLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ScraperDSLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(ScraperDSLParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(ScraperDSLParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ScraperDSLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ScraperDSLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ScraperDSLParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ScraperDSLParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ScraperDSLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ScraperDSLParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#variableRef}.
	 * @param ctx the parse tree
	 */
	void enterVariableRef(ScraperDSLParser.VariableRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#variableRef}.
	 * @param ctx the parse tree
	 */
	void exitVariableRef(ScraperDSLParser.VariableRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ScraperDSLParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ScraperDSLParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(ScraperDSLParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(ScraperDSLParser.FuncCallContext ctx);
}