package org.fomky.ratpack.core.controller;


import ratpack.func.Action;
import ratpack.handling.Chain;

/**
 * @author Created by Fomky on 2017/3/311:10.
 */
public interface CoreHandle extends Action<Chain> {
    String SESSION_USERID = "USERID";
}
