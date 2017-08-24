package com.gtpalmer.Rank_SAT;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;
import java.util.HashMap;

/**
 * Created by gtpalmer on 7/5/17.
 */
public abstract class SAT {

    //How can we use polymorphism to make this less wasteful?
    static class Variable {
        vector<Integer> pos_sat;
        vector<Integer> neg_sat;
    }

    private vector<Variable> vars;


    SAT() {
    }

    /*
    Expected Input From a file is as follows:

    1. Any number of comment lines with a 'c' as the first character are ignored.
    2. A single line led with a 'p' character is followed by two numbers: the number of variables and then the number
        of clauses.
    3. The next n lines should correspond to clauses. A clause consists of any number of space separated variables with
        absolute values between 1-num_vars specified in (2). Each line should end with a 0 and a newline to denote the end of
        clause. Note: Only the 0 is necessary to signal the end of the clause - newlines are recommended for readability.
    4. Input will stop being read after num_clauses 0's have been parsed.

    Example:
    c this is a comment
    p 4 5
    2 3 0
    -1 4 3 0
    3 1 -2 0
    1 2 -3 4 0
    2 -3 0
     */
    SAT(InputStream is) {
        Scanner sc = new Scanner(is);
        while (sc.next() == "c") {
            sc.nextLine();
        }
        if (sc.next() != "p") {
            Standart.out.println("Error: First non-comment line must begin with 'p'");
            System.exit(1);
        }
        sc.next();
        int num_vars = sc.nextInt();
        int num_clauses = sc.nextInt();
        

    }
}
