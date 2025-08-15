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
	 * Visit a parse tree produced by {@link ScraperDSLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ScraperDSLParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ScraperDSLParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#variableRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableRef(ScraperDSLParser.VariableRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ScraperDSLParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScraperDSLParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(ScraperDSLParser.FuncCallContext ctx);
}