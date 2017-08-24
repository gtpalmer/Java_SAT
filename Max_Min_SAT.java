package com.gtpalmer.Rank_SAT;

import java.io.InputStream;

/**
 * Created by gtpalmer on 7/14/17.
 */
public class Max_Min_SAT extends SAT {

    static class Var_data {
        int pos_size;
        int neg_size;
        int weight;
    }

    private vector<Var_data> var_data;

    Max_Min_SAT() {
    }

    Max_Min_SAT(InputStream is) {
        super(is);
    }
}
