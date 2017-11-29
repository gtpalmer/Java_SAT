package com.gtpalmer.Java_SAT;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.Iterator;
import java.lang.Math;
import java.util.ArrayDeque;

public class Random_SAT extends SAT {
    Random_SAT(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public Vector<Integer> solve() {

        HashSet<Integer> curr_vars = new HashSet<Integer>();
        //Create set of indices of available variables
        for (int i = 1; i < vars.size(); i++) {
            curr_vars.add(i);
        }
        Vector<Integer> unit_clauses = new Vector<>();
        HashSet<Integer> chosen = new HashSet<>();
        Integer clauses_remaining = clauses.size();
        ArrayDeque<Integer> choices = new ArrayDeque<>();

        //Picks a new variable at random (based on order of hashset) and adds it to our choices
        get_next_var(curr_vars, choices);
        chosen.add(choices.getLast());

        while (!choices.isEmpty()) {
            //Returns false if a constraint is broken
            Integer choice = choices.getLast();
            if (update_forward(choice, clauses_remaining, unit_clauses)) {
                chosen.add(choice);
                if (clauses_remaining == 0) {
                    return new Vector<>(chosen);
                }
                else if (clauses_remaining < 0) {
                    throw new RuntimeException("Error: Less than 0 clauses remain");
                }
                else {
                    get_next_var(curr_vars, choices);
                }
            }
            else {
                Integer last_element = choices.removeLast();
                chosen.remove(last_element);
                update_backward(last_element, clauses_remaining, chosen);
                if (choices.isEmpty()) {
                    break;
                }
                while (last_element != -choices.getLast()) {
                    last_element = choices.removeLast();
                    chosen.remove(last_element);
                    update_backward(last_element, clauses_remaining, chosen);
                    if (choices.isEmpty()) {
                        break;
                    }
                }
            }
        }
        return new Vector<>();
    }

    @Override
    public Boolean verify() {
        return null;
    }


    private Integer get_next_var(HashSet<Integer> curr_vars, ArrayDeque<Integer> choices) {
        Integer curr_var = new Integer(0);
        try {
            Iterator<Integer> curr_var_iter = curr_vars.iterator();
            curr_var = curr_var_iter.next();
            choices.add(curr_var);
            choices.add(-curr_var);
            curr_vars.remove(vars.get(curr_var));
        } catch(NoSuchElementException e) {
            System.err.println("No variables present - caught exception: " + e.getMessage());
            System.exit(1);
        }
        return curr_var;
    }
    private boolean update_forward(Integer choice, Integer clauses_remaining, Vector<Integer> unit_clauses) {

        Boolean constraint_broken = false;

        if (choice > 0) {
            for (Integer idx : vars.get(choice).pos_sat) {
                if (curr_clauses.get(idx) == true) {
                    clauses_remaining--;
                }
                curr_clauses.set(idx, Boolean.FALSE);
            }
            for (Integer idx : vars.get(choice).neg_sat) {
                Integer count = --clauses.get(idx).count;
                if (count == 1) {
                    unit_clauses.add(idx);
                }
                else if (count == 0) {
                    constraint_broken = true;
                }
                else if (count < 0) {
                    System.err.println("Error: Count of clause " + idx + " is less than 0");
                }
            }
        }
        else {
            for (Integer idx : vars.get(-choice).neg_sat) {
                if (curr_clauses.get(idx) == true) {
                    clauses_remaining--;
                }
                curr_clauses.set(idx, Boolean.FALSE);
            }
            for (Integer idx : vars.get(-choice).pos_sat) {
                Integer count = --clauses.get(idx).count;
                if (count == 1) {
                    unit_clauses.add(idx);
                }
                else if (count == 0) {
                    constraint_broken = true;
                }
                else if (count < 0) {
                    System.err.println("Error: Count of clause " + idx + " is less than 0");
                }
            }
        }
        return !constraint_broken;
    }

    private void update_backward(Integer choice, Integer clauses_remaining, HashSet<Integer> chosen) {

        if (choice > 0) {
            for (Integer idx : vars.get(choice).pos_sat) {
                Clause clause = clauses.get(idx);
                curr_clauses.set(idx, true);
                clauses_remaining++;
                for (Integer satisfier : clause.satisfiers) {
                    if (chosen.contains(satisfier)) {
                        curr_clauses.set(idx, false);
                        clauses_remaining--;
                    }
                }
            }
            for (Integer idx : vars.get(choice).neg_sat) {
                clauses.get(idx).count++;
            }
        }
        else {
            for (Integer idx : vars.get(-choice).neg_sat) {
                Clause clause = clauses.get(idx);
                curr_clauses.set(idx, true);
                clauses_remaining++;
                for (Integer satisfier : clause.satisfiers) {
                    if (chosen.contains(satisfier)) {
                        curr_clauses.set(idx, false);
                        clauses_remaining--;
                    }
                }
            }
            for (Integer idx : vars.get(-choice).pos_sat) {
                clauses.get(idx).count++;
            }
        }
    }
}
