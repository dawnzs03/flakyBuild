// Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
// or more contributor license agreements. Licensed under the Elastic License
// 2.0; you may not use this file except in compliance with the Elastic License
// 2.0.
package org.elasticsearch.xpack.esql.expression.predicate.operator.arithmetic;

import java.lang.Override;
import java.lang.String;
import org.elasticsearch.compute.data.Block;
import org.elasticsearch.compute.data.IntBlock;
import org.elasticsearch.compute.data.IntVector;
import org.elasticsearch.compute.data.Page;
import org.elasticsearch.compute.operator.EvalOperator;

/**
 * {@link EvalOperator.ExpressionEvaluator} implementation for {@link Sub}.
 * This class is generated. Do not edit it.
 */
public final class SubIntsEvaluator implements EvalOperator.ExpressionEvaluator {
  private final EvalOperator.ExpressionEvaluator lhs;

  private final EvalOperator.ExpressionEvaluator rhs;

  public SubIntsEvaluator(EvalOperator.ExpressionEvaluator lhs,
      EvalOperator.ExpressionEvaluator rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public Block eval(Page page) {
    Block lhsUncastBlock = lhs.eval(page);
    if (lhsUncastBlock.areAllValuesNull()) {
      return Block.constantNullBlock(page.getPositionCount());
    }
    IntBlock lhsBlock = (IntBlock) lhsUncastBlock;
    Block rhsUncastBlock = rhs.eval(page);
    if (rhsUncastBlock.areAllValuesNull()) {
      return Block.constantNullBlock(page.getPositionCount());
    }
    IntBlock rhsBlock = (IntBlock) rhsUncastBlock;
    IntVector lhsVector = lhsBlock.asVector();
    if (lhsVector == null) {
      return eval(page.getPositionCount(), lhsBlock, rhsBlock);
    }
    IntVector rhsVector = rhsBlock.asVector();
    if (rhsVector == null) {
      return eval(page.getPositionCount(), lhsBlock, rhsBlock);
    }
    return eval(page.getPositionCount(), lhsVector, rhsVector).asBlock();
  }

  public IntBlock eval(int positionCount, IntBlock lhsBlock, IntBlock rhsBlock) {
    IntBlock.Builder result = IntBlock.newBlockBuilder(positionCount);
    position: for (int p = 0; p < positionCount; p++) {
      if (lhsBlock.isNull(p) || lhsBlock.getValueCount(p) != 1) {
        result.appendNull();
        continue position;
      }
      if (rhsBlock.isNull(p) || rhsBlock.getValueCount(p) != 1) {
        result.appendNull();
        continue position;
      }
      result.appendInt(Sub.processInts(lhsBlock.getInt(lhsBlock.getFirstValueIndex(p)), rhsBlock.getInt(rhsBlock.getFirstValueIndex(p))));
    }
    return result.build();
  }

  public IntVector eval(int positionCount, IntVector lhsVector, IntVector rhsVector) {
    IntVector.Builder result = IntVector.newVectorBuilder(positionCount);
    position: for (int p = 0; p < positionCount; p++) {
      result.appendInt(Sub.processInts(lhsVector.getInt(p), rhsVector.getInt(p)));
    }
    return result.build();
  }

  @Override
  public String toString() {
    return "SubIntsEvaluator[" + "lhs=" + lhs + ", rhs=" + rhs + "]";
  }
}
