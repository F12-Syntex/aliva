// Generated from io\github\synte\aliva\parser\ScraperDSL.g4 by ANTLR 4.13.0
package io.github.synte.aliva.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ScraperDSLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ScraperDSLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(ScraperDSLParser.ScriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ScraperDSLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(ScraperDSLParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ScraperDSLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ScraperDSLParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ScraperDSLParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(ScraperDSLParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ScraperDSLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ScraperDSLParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpr(ScraperDSLParser.LogicalOrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpr(ScraperDSLParser.LogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(ScraperDSLParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#comparisonExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(ScraperDSLParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(ScraperDSLParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(ScraperDSLParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(ScraperDSLParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ScraperDSLParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#functionLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionLiteral(ScraperDSLParser.FunctionLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ScraperDSLParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(ScraperDSLParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#mapLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapLiteral(ScraperDSLParser.MapLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(ScraperDSLParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#variableRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableRef(ScraperDSLParser.VariableRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(ScraperDSLParser.FuncCallContext ctx);
}