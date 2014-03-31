import java.io.*;
import java.util.*;

public class Polly {
    private Scanner sc;
    private String[] items;

    public Polly(String f) {
	try {
	    sc = new Scanner(new File(f));
	} catch(Exception e) {
	    System.out.println(e);
	    System.exit(0);
	    return;
	}
	readInput();
    }

    public void readInput() {
	String line = sc.nextLine();
	int numItems = Integer.parseInt(line);
	items = new String[numItems];
    }

    public String solve() {
	String r = "";
	for(int i=0; i<items.length; i++) {
	    String line = sc.nextLine();
	    //System.out.println(line);
	    r+=outputLine(checkLine(line),line);
	}
	return r;
    }

    private int[] checkLine(String s) {
	char[] seq;
	for(int rlen=1; rlen<=s.length()/2; rlen++) {
	    seq = new char[rlen];
	    for(int i=0; i<rlen; i++) {
		seq[i] = s.charAt(s.length()-1-i);
	    }
	    boolean ok = true;
	    for(int i=0; i<s.length(); i++) {
		//System.out.println(""+s.charAt(s.length()-1-i)+seq[i%seq.length]);
		if(s.charAt(s.length()-1-i) != seq[i%seq.length]) {
		    if(i>seq.length*2-1) {
			return new int[]{seq.length,s.length()-i};
		    } else {
			ok = false;
			break;
		    }
		}
	    }
	    if(ok) {return new int[]{seq.length,0};}
	    //System.out.println(Arrays.toString(seq));
	}
	return null;
    }

    private String outputLine(int[] sol, String s) {
	if(sol==null) {return "Polly had a bad day\n";}
	return s.substring(sol[1],sol[1]+sol[0])+" "+sol[1]+"\n";
    }
    
    public static void main(String[] args) {
	String in;
	if(args.length<1) {in = "PollyIn.txt";} 
	else {in = args[0];}

	Polly nope = new Polly(in);
	System.out.println(nope.solve());
    }

}
