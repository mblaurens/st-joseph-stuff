import java.io.*;
import java.math.*;
import java.util.*;

public class SJCPenney {
    private String[] items;
    private BigDecimal[] prices;
    private BigDecimal[] discPrices;
    private Scanner sc;

    public SJCPenney(String f) {
	try {
	    sc = new Scanner(new File(f));
	} catch(Exception e) {
	    System.out.println(e);
	    System.exit(0);
	    return;
	}
	readItems();
	calcDiscount();
    }

    private void readItems() {
	String line = sc.nextLine();
	int numItems = Integer.parseInt(line);
	items = new String[numItems];
	prices = new BigDecimal[numItems];
	discPrices = new BigDecimal[numItems];

	for(int i=0; i<numItems; i++) {
	    line = sc.nextLine();
	    int lastspace = line.lastIndexOf(' ');
	    items[i] = line.substring(0,lastspace);
	    prices[i] = new BigDecimal(line.substring(lastspace+1));
	}
    }
    
    private void calcDiscount() {
	if(getTotal(prices).doubleValue()<100) {
	    return;
	}
	
	BigDecimal discTotal = getTotal(prices).subtract(new BigDecimal(10));
        double ratio = discTotal.doubleValue()/getTotal(prices).doubleValue();
	BigDecimal bdratio = new BigDecimal(ratio);
	for(int i=0; i<prices.length; i++) {
	    discPrices[i] = prices[i].multiply(bdratio);
	    discPrices[i] = discPrices[i].setScale(2,RoundingMode.HALF_UP);
	}

	BigDecimal diff = getTotal(prices).subtract(getTotal(discPrices));
	discPrices[discPrices.length-1] = discPrices[discPrices.length-1]
	    .add(diff).subtract(new BigDecimal(10));
    }


    public String origReceipt() {return receipt(prices);}
    public String discReceipt() {return receipt(discPrices);}
    private String receipt(BigDecimal[] a) { 
	String r = "";
	for(int i=0; i<items.length; i++) {
	    r+=items[i]+"\t"+a[i]+"\n";
	}
        r+="TOTAL\t"+getTotal(a)+"\n";
	return r;
    }

    private BigDecimal getTotal(BigDecimal[] a) {
	BigDecimal r = new BigDecimal(0);
	for(int i=0; i<a.length; i++) {r=r.add(a[i]);}
	return r;
    }
    
    public static void main(String[] args) {
	String in;
	if(args.length<1) {in = "SJCPenneyIn.txt";} 
	else {in = args[0];}

	SJCPenney nope = new SJCPenney(in);
	//System.out.println("original:");
	//System.out.println(nope.origReceipt());
	//System.out.println("discounted:");
	System.out.println(nope.discReceipt());
    }
}
