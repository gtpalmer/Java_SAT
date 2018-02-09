package com.gtpalmer.Java_SAT;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

/**
 * Created by gtpalmer on 7/14/17.
 */
public class Max_Min_SAT extends SAT {

    static class Var_data {
        int pos_size;
        int neg_size;
        int weight;
    }

    private Vector<Var_data> var_data;

    Max_Min_SAT(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public Vector<Integer> solve() {
        return null;
    }

    @Override
    public Boolean verify() {
        return null;
    }
}
