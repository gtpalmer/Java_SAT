package com.gtpalmer.Java_SAT;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Vector;
import java.lang.Math;
import java.util.HashSet;

/**
 * Created by gtpalmer on 7/5/17.
 */
public abstract class SAT {

    //How can we use polymorphism to make this less wasteful?
    static class Variable {
        Vector<Integer> pos_sat;
        Vector<Integer> neg_sat;
    }
    static class Clause {
        Vector<Integer> satisfiers;
        int count = 0;
    }

    InputStream in_str;
    OutputStream out_str;
    Vector<Variable> vars;
    Vector<Clause> clauses;
    Vector<Boolean> curr_clauses;

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
    SAT(InputStream is, OutputStream os) {

        in_str = is;
        out_str = os;
        vars = new Vector<Variable>();
        clauses = new Vector<Clause>();
        curr_clauses = new Vector<Boolean>();

        Scanner sc = new Scanner(is);
        while (sc.next().equals("c")) {
            sc.nextLine();
        }
        if (!sc.next().equals("p")) {
            System.err.println("Error: First non-comment line must begin with 'p'");
            System.exit(1);
        }
        sc.next();
        int num_vars = sc.nextInt();
        int num_clauses = sc.nextInt();

        vars.setSize(num_vars + 1);
        clauses.setSize(num_clauses);
        int clause_num = 0;

        while (sc.hasNextInt()) {
            int temp = sc.nextInt();
            if (temp != 0) {
                clauses.get(clause_num).satisfiers.add(temp);
                clauses.get(clause_num).count++;
                if (temp > 0) {
                    vars.get(temp).pos_sat.add(clause_num);
                }
                else {
                    temp = Math.abs(temp);
                    vars.get(temp).neg_sat.add(clause_num);
                }
            }
            else {
                clause_num++;
            }
        }
        
        //Start with all clauses
        curr_clauses.setSize(num_clauses);
        for (int i = 0; i < num_clauses; i++) {
            curr_clauses.set(i, Boolean.TRUE);
        }
    }
    public Boolean verify(Vector<Integer> sol) {
        HashSet<Integer> unsatisfied = new HashSet<Integer>();
        for (int i = 0; i < clauses.size(); i++) {
            unsatisfied.add(i);
        }
        for (Integer var : sol) {
            if (var > 0) {
                for (Integer idx : vars.get(var).pos_sat) {
                    unsatisfied.remove(idx);
                }
            }
            else if (var < 0){
                for (Integer idx : vars.get(-var).neg_sat) {
                    unsatisfied.remove(idx);
                }
            }
            else {
                throw new RuntimeException("Error: Variable cannot equal 0");
            }
        }

        if (unsatisfied.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    abstract public Vector<Integer> solve();



}
