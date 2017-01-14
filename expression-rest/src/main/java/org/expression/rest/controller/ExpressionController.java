package org.expression.rest.controller;

import java.util.List;

import org.expression.model.Expression;
import org.expression.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpressionController {

  @Autowired
  private ExpressionService expressionService;

  @RequestMapping("/search")
  public List<Expression> searchExpression(@RequestParam(value = "q") String searchText) {
    return expressionService.searchExpressions(searchText);
  }

  @RequestMapping("/find-random")
  public List<Expression> searchExpression(
      @RequestParam(value = "size", required = false) Integer size) {
    return expressionService.findRandomExpressions(size);
  }

}
