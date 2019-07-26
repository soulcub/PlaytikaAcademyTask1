package com.company.servers;

/**
 * Represents fallible unit that can contain other inner fallible units inside.
 */
public interface FallibleWithInners {

    int getId();

    boolean isFailed();

    FallibleWithInners getInnerFallible(int number);

    int getSize();

}
