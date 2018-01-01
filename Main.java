package com.gtpalmer.Java_SAT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Boolean infile = Boolean.FALSE;
        InputStream is = System.in;
        Boolean outfile = Boolean.FALSE;
        PrintStream os = System.out;

	    for (int i = 0; i < args.length; i++) {
	        switch(args[i]) {
	            /*
	            Specifies that input will be read from a file.
	            Followed by a required filename argument. The program will search for the file in the current directory
	            and throw an exception if not found.
	             */
                case "-i": {
                    String filename = "";
                    try {
                        filename = args[++i];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Filename expected after -i argument");
                        System.exit(1);
                    }
                    try {
                        String currdir = System.getProperty("user.dir");
                        is = new FileInputStream(currdir + filename);
                        infile = Boolean.TRUE;
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found");
                        System.exit(1);
                    }
                    break;
                }
                /*
                Specifies that output will be written to a file. Filename argument must provided and the file will be
                created in the current directory.
                 */
                case "-o": {
                    String filename = "";
                    try {
                        filename = args[++i];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("No filename entered");
                        System.exit(1);
                    }
                    if (filename.charAt(0) == '-') {
                        System.err.println("No filename entered");
                        System.exit(1);
                    }
                    try {
                        String currdir = System.getProperty("user.dir");
                        os = new PrintStream(currdir + filename);
                        outfile = Boolean.TRUE;
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found");
                        System.exit(1);
                    }
                    break;
                }
                default:
                    System.err.println("Unknown Command line argument: " + args[i]);
                    System.exit(1);
            }
        }
        Random_SAT mySAT = new Random_SAT(is, os);
	    Vector<Integer> solutions = mySAT.solve();

	    if (mySAT.verify(solutions)) {
            os.println("Verified!");
            os.println("Correct ");
        }



    }
}
