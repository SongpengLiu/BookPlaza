package com.example.bookplaza.trees;

/**
 * Set the operator for the expected tree result.
 * e.g. currentValue > boundaryValue
 * EQUAL: Return the exact value or return null.
 * EQUAL_OR_LARGER: Return the exact value or return the nearest larger value
 * EQUAL_OR_SMALLER: Return the exact value or return the nearest smaller value
 * @author Yucheng Zhu
 */
public enum TreeResultOperator {
    EQUAL, EQUAL_OR_LARGER, EQUAL_OR_SMALLER, LARGER, SMALLER
}
