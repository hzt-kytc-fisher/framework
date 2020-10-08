package com.kytc.framework.cache.aop;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author liningning
 * @date 2019年4月22日 上午11:16:38
 */
@Slf4j
public class SpExpressionParser {

	private final static ExpressionParser parser = new SpelExpressionParser();

	public static String getKey(String key, String[] paramNames, Object[] arguments) {
		try {
			Expression expression = parser.parseExpression(key);
			EvaluationContext context = new StandardEvaluationContext();
			int length = paramNames.length;
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					context.setVariable(paramNames[i], arguments[i]);
				}
			}
			return expression.getValue(context, String.class);
		} catch (Exception e) {
			log.info("el表达式解析失败,ex={}", e);
			return null;
		}
	}

}