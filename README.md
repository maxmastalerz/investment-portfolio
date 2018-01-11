Investment Portfolio
==========================================================================

This java application was developed for an introductory java course at the Univrsity of Guelph.

Project purpose: A tool that can keep track of mutual fund & stock investments in a GUI format

Assumptions & Limitations:
 - This program does not keep track of cash collected outside of the portfolio.
 - This program uses one data file, investments.csv to store data. It is the default file the program is started with
 - This program will have unexpected results when passed in a csv file that does not follow the expected format of [stock/mutualfund],symbol,full name,quantity,price,book value

User guide:
Open project in netbeans & compile the Portfolio.java file
You will see a GUI open up. Use the commands button in the menu bar to swap in between interfaces. The interfaces available are:
 - Buy      : Screen to buy and/or update investments
 - Sell     : Screen to sell existing investments
 - Update   : Screen to look through investments and update their prices
 - Get Gain : Screen to get the individual gains of investments, along with the total gain of the portfolio
 - Search   : Screen to search for an investment via certain user-defined filters


Possible improvements:
- Modularizing code in the Portfolio.java file
- Keeping JFrame component in tact upon resizing

Test Plan:
==========================================================================
To test the code I did the following:

Loaded in the following investments.csv file:

stock,HBC,Hudsons Bay company,100,11.21,1130.99
stock,BMO,Bank of Montreal,20,99.56,2001.19
stock,RY,Royal Bank of Canada,10,100.45,1014.49
stock,GOOG,Alphabet Inc.,5,1063.29,5336.43
mutualfund,CIG677,CI Signature Select,5,28.57,142.85
mutualfund,CIGYX,AB concentrated international growth portfolio,3,11.88,35.64

INITIAL INTERFACE TEST PLAN:
============================
- Does the initial welcome screen clearly define how to use the program
- Does the commands menu switch interfaces properly
- Does the program quit upon clicking the quit button

BUY INTERFACE TEST PLAN:
========================
- Try to buy an investment with no fields filled in
- Try to buy an investment with a permutation of fields in, with others left out
- Try to buy an investment with a negative value in the quantity, or price fields
- Try to buy an investment that already exists: Does the investment name field auto-fill? Can you buy more investments than there exist for that investment?
- Attempting to edit the messages text area
- See if the reset button clears all the text fields
- See if you can buy a mutual fund and stock with the same investment symbols

SELL INTERFACE TEST PLAN:
=========================
- Try to sell an investment without any fields filled in
- Try to sell an investment with and without certain fields filled in (attempt all permuations)
- Try to sell an investment at a negative price and/or negative quantity
- Try to sell an investment which doesn't exist in the portfolio
- Try to sell more of an investmnet than actually exist
- See if the reset button clears the text fields
- Try editing the messages area in the sell interface
- See if the format of the messages area is conscise and clear

UPDATE INTERFACE TEST PLAN:
===========================
- Does the prev button grey out when on the lowest end of the investments list
- Does the next button grey out when at the highest end of the investments list
- Does the price of the investment dispaly correctly
- Does channging the investment price, and clicking the save button update the price
- The messages area should be non-editable and view only
- Does the symbol, ex. HBC, display the correct parallel name, ex. Hudson's Bay Company?

GET GAIN INTERFACE TEST PLAN:
=============================
- Is the gain calculated properly. It should be the sum of all individual gains
- Are individual gains calculated properly? Look at assignment description to determine this on a per case basis
- Do the investments display with their symbol, associated name, and positive/negative gain
- Check if the total gain field is greyed out

SEARCH INTERFACE TEST PLAN:
===========================
- Try to search for all investments with no fields filled in. All investments should display
- Try to edit the search reults text area. It should be non-editable
- Check to see if the reset button clears all text fields

:Search by symbol
- If searching just by symbol, one investment should be displayed in the messages area. Is this the correct investment?

:Search by keywords
- If searching just by keywords, try one word first, then two, then three. The more words you input the smaller the search results should be across a large collection of investments.
- The keywords should be seperated by space
- What happens if a keyword is inputted that doesn't have a match. Are any search results displayed. There shouldn't be any returning

:Search by low price
- If only the low price is inputted, ex. 100, only investments with a price of 100 and up should be displayed
- What happens when you input non-numeric input into the field. It should not be allowed

:Search by high price
- If only the high price is inputted, ex. 100, only investments with a price of 100 and down should be displayed
- What happens when you input non-numeric input into the field. It should not be allowed

:Search by a combination of low price and high price
- If both the high price and low price are the same number, the resulting search should only display investments with exactly that price
- If both the high price and low price are different, the resulting search should only display investments with a price inbetween the high and low rage

:Search by a combination of the above
- Check to see if any combination of the above delivers expected results

QUIT FUNCTIONALITY TEST PLAN:
=============================
- Try to quit the program from all possible interfaces
- See if the program saves it's state onto the disk(investments.csv)