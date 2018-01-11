package investmentportfolio;

/**
 *
 * @author max
 */
public class Investment {
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;
    
    /**
    * Mutator for the investment symbol
    * @param  symbol  The value to be stored in this.symbol
    */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    /**
    * Accessor for the investment symbol
    * @return The symbol string of the instance
    */
    public String getSymbol() {
        return this.symbol;
    }
    
    /**
    * Mutator for the investment name
    * @param investmentName The name to be saved into the stock name
    */
    public void setInvestmentName(String investmentName) {
        this.name = investmentName;
    }
    
    /**
    * Accessor for the investment name
    * @return the name of the investment
    */
    public String getInvestmentName() {
        return this.name;
    }
    
    /**
    * Mutator for the number of type x investment held
    * @param quantity The number of stocks of type x in an instance
    */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
    * Accessor to get the number of investments held
    * @return the number of type x investments held in the instance
    */
    public int getQuantity() {
        return this.quantity;
    }
    
    /**
    * Mutator to set the price of an investment
    * @param price The price to be set
    */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
    * Accessor for the price of the investment
    * @return the price of the investment
    */
    public double getPrice() {
        return this.price;
    }
    
    /**
    * Mutator for bookValue
    * @param bookValue the value to be set
    */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }
    
    /**
    * Accessor for bookValue
    * @return Returns the bookValue of the instance
    */
    public double getBookValue() {
        return this.bookValue;
    }
}
