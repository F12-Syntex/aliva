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
	 * Enter a parse tree produced by {@link ScraperDSLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ScraperDSLParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ScraperDSLParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(ScraperDSLParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(ScraperDSLParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(ScraperDSLParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(ScraperDSLParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ScraperDSLParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ScraperDSLParser.BlockContext ctx);
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
	 * Enter a parse tree produced by {@link ScraperDSLParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(ScraperDSLParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(ScraperDSLParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(ScraperDSLParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(ScraperDSLParser.LogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(ScraperDSLParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(ScraperDSLParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(ScraperDSLParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(ScraperDSLParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(ScraperDSLParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(ScraperDSLParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(ScraperDSLParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(ScraperDSLParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(ScraperDSLParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(ScraperDSLParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ScraperDSLParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ScraperDSLParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#functionLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFunctionLiteral(ScraperDSLParser.FunctionLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#functionLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFunctionLiteral(ScraperDSLParser.FunctionLiteralContext ctx);
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
	 * Enter a parse tree produced by {@link ScraperDSLParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(ScraperDSLParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(ScraperDSLParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#mapLiteral}.
	 * @param ctx the parse tree
	 */
	void enterMapLiteral(ScraperDSLParser.MapLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#mapLiteral}.
	 * @param ctx the parse tree
	 */
	void exitMapLiteral(ScraperDSLParser.MapLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScraperDSLParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void enterMapEntry(ScraperDSLParser.MapEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScraperDSLParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void exitMapEntry(ScraperDSLParser.MapEntryContext ctx);
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