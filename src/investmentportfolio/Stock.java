package investmentportfolio;

import java.text.DecimalFormat;

/**
 * @author      Max Mastalerz <mmastale @ uoguelph.ca>
 * @version     1.0
 */
public class Stock extends Investment {
    public Stock(String symbol, String name, int quantity, double price, double bookValue) {
        super.setSymbol(symbol);
        super.setInvestmentName(name);
        super.setQuantity(quantity);
        super.setPrice(price);
        super.setBookValue(bookValue);
    }
    
    /**
    * Calculates the gain on the investment
    * @return The gain on the investment
    */
    public double getGain() {
        return (super.getQuantity()*super.getPrice() - 9.99) - super.getBookValue();
    }
    
    /**
    * Checks if two objects have all the attributes being shared
    * @return Whether or not the objects are the same(by value)
    */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Stock)) return false;
        else {
            Stock d = (Stock) o;
            if (!(this.getSymbol().equals(d.getSymbol()))) return false;
            if (!(this.getInvestmentName().equals(d.getInvestmentName()))) return false;
            if (!(this.getQuantity() == d.getQuantity())) return false;
            if (!(this.getPrice() == d.getPrice())) return false;
            if (!(this.getBookValue() == d.getBookValue())) return false;
            return true;
        }
    }
    
    /**
    * Returns the object as a string
    * @return Object as a string
    */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        return "["+super.getSymbol()+"]: "+super.getInvestmentName()+" ("+super.getQuantity()+" * $"+super.getPrice()+") ... Book Value: "+df.format(super.getBookValue());
    }
}
