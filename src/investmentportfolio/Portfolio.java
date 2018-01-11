package investmentportfolio;

import java.text.DecimalFormat;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author      Max Mastalerz <mmastale @ uoguelph.ca>
 * @version     1.2
 */
public class Portfolio extends JFrame {
    public Portfolio() {
        super("Investment Portfolio");
        
        ArrayList<Investment> investments = new ArrayList<>(); //array to store investments
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                saveAndQuit(investments);
            }
        });
        
        //:::::::::::::
        //:::PROGRAM:::
        //:::::::::::::
        HashMap<String, ArrayList<Integer>> hmap = new HashMap<String, ArrayList<Integer>>();
        
        //Attempt to read in investments.csv into ArrayList
        try {
            File file = new File("investments.csv");
            file.createNewFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String toks[] = line.split(",");
                String tokens[] = line.split(",")[2].split(" ");

                ArrayList<Integer> intArray = new ArrayList<Integer>();
                for(int i=0; i<tokens.length; i++) {
                    String lowerCase = tokens[i].toLowerCase();
                    if(hmap.containsKey(lowerCase)) {
                        intArray = hmap.get(lowerCase);
                        intArray.add(investments.size());
                    } else {
                        intArray = new ArrayList<>();
                        intArray.add(investments.size());
                        hmap.put(lowerCase, intArray);
                    }
                }

                if(toks[0].equals("stock")) {
                    Stock loadStock = new Stock(toks[1], toks[2], Integer.parseInt(toks[3]), Double.parseDouble(toks[4]), Double.parseDouble(toks[5]));
                    investments.add(loadStock);

                } else if(toks[0].equals("mutualfund")) {
                    MutualFund loadMutualFund = new MutualFund(toks[1], toks[2], Integer.parseInt(toks[3]), Double.parseDouble(toks[4]), Double.parseDouble(toks[5]));
                    investments.add(loadMutualFund);
                }
                //investmentNum++;
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println("File parsing/reading error" + e.getMessage());
        }
        
        //::::::::::::::::::
        //:::GUI ELEMENTS:::
        //::::::::::::::::::
        
        //MENUBAR
        JMenuBar menuBar = new JMenuBar();
        JMenu commands = new JMenu("Commands");
        JMenuItem buy = new JMenuItem("Buy");
        JMenuItem sell = new JMenuItem("Sell");
        JMenuItem update = new JMenuItem("Update");
        JMenuItem getGain = new JMenuItem("Get Gain");
        JMenuItem search = new JMenuItem("Search");
        JMenuItem quit = new JMenuItem("Quit");
        commands.add(buy);
        commands.add(sell);
        commands.add(update);
        commands.add(getGain);
        commands.add(search);
        commands.add(quit);
        menuBar.add(commands);
        super.setJMenuBar(menuBar);
        
        //INITIALINTERFACE LAYOUT
        JPanel initialInterface = new JPanel(new BorderLayout());
        JTextArea initialText = new JTextArea();
        JTextArea textArea = new JTextArea(18,33);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setFont(new Font(null, Font.PLAIN, 20));
        textArea.setText("\n\n\n\n Welcome to Investment Portfolio.\n\n\n\n\n Choose a command from the \"Commands\" menu to buy or sell\n an investment, update prices for all investments, get gain for\n the portfolio, search for relevant investments, or quit the\n program.");
        JScrollPane scrollPane = new JScrollPane(textArea);
        initialInterface.add(scrollPane, BorderLayout.CENTER);
        
        //BUY LAYOUT
        JPanel buyInterface = new JPanel();
        buyInterface.setLayout(new BorderLayout());
        JPanel buyTopLeftPanel = new JPanel(new GridBagLayout());
        buyInterface.add(buyTopLeftPanel, BorderLayout.CENTER);
        buyTopLeftPanel.setBorder(BorderFactory.createTitledBorder("Buying an investment"));
        GridBagConstraints buyLabelConstraints = new GridBagConstraints();
        buyLabelConstraints.anchor = GridBagConstraints.WEST;
        buyLabelConstraints.gridx = 0;
        buyLabelConstraints.gridy = 0;
        buyLabelConstraints.weightx = 0.5;
        buyLabelConstraints.weighty = 1;
        buyLabelConstraints.insets = new Insets(5, 10, 5, 10);
        GridBagConstraints buyFieldConstraints = new GridBagConstraints();
        buyFieldConstraints.anchor = GridBagConstraints.WEST;
        buyFieldConstraints.gridx = 1;
        buyFieldConstraints.gridy = 0;
        buyFieldConstraints.weightx = 0.5;
        buyFieldConstraints.weighty = 1;
        buyFieldConstraints.insets = new Insets(5, 10, 5, 10);
        buyTopLeftPanel.add(new JLabel("Type"), buyLabelConstraints);
        JComboBox<String> buyTypeSelect = new JComboBox();
        buyTypeSelect.addItem("  stock  ");
        buyTypeSelect.addItem("  mutual fund  ");
        buyTopLeftPanel.add(buyTypeSelect, buyFieldConstraints);
        buyLabelConstraints.gridy++;
        buyTopLeftPanel.add(new JLabel("Symbol"), buyLabelConstraints);
        JTextField buySymbolField = new JTextField(10);
        buyFieldConstraints.gridy++;
        buyTopLeftPanel.add(buySymbolField, buyFieldConstraints);
        buyLabelConstraints.gridy++;
        buyTopLeftPanel.add(new JLabel("Name"), buyLabelConstraints);
        JTextField buyNameField = new JTextField(20);
        buyFieldConstraints.gridy++;
        buyTopLeftPanel.add(buyNameField, buyFieldConstraints);
        buyLabelConstraints.gridy++;
        buyTopLeftPanel.add(new JLabel("Quantity"), buyLabelConstraints);
        JTextField buyQuantityField = new JTextField(6);
        buyFieldConstraints.gridy++;
        buyTopLeftPanel.add(buyQuantityField, buyFieldConstraints);
        buyLabelConstraints.gridy++;
        buyTopLeftPanel.add(new JLabel("Price"), buyLabelConstraints);
        JTextField buyPriceField = new JTextField(6);
        buyFieldConstraints.gridy++;
        buyTopLeftPanel.add(buyPriceField, buyFieldConstraints);
        JPanel buyTopRightPanel = new JPanel(new GridBagLayout());
        buyTopRightPanel.setBorder(BorderFactory.createEmptyBorder());
        buyInterface.add(buyTopRightPanel, BorderLayout.EAST);
        GridBagConstraints buyButtonConstraints = new GridBagConstraints();
        buyButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        buyButtonConstraints.insets = new Insets(10, 10, 10, 10);
        buyButtonConstraints.weighty = 1;
        buyButtonConstraints.gridy = 0;
        JButton buyResetButton = new JButton(" Reset ");
        buyTopRightPanel.add(buyResetButton, buyButtonConstraints);
        JButton buyBuyButton = new JButton(" Buy ");
        buyButtonConstraints.gridy++;
        buyTopRightPanel.add(buyBuyButton, buyButtonConstraints);
        JPanel buyBottomPanel = new JPanel(new BorderLayout());
        buyInterface.add(buyBottomPanel, BorderLayout.SOUTH);
        buyBottomPanel.setBorder(BorderFactory.createTitledBorder("Messages"));
        JTextArea buyMessagesArea = new JTextArea(6, 30);
        buyMessagesArea.setEditable(false);
        buyBottomPanel.add(new JScrollPane(buyMessagesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        
        //SELL SETUP
        JPanel sellInterface = new JPanel();
        sellInterface.setLayout(new BorderLayout());
        JPanel sellTopLeftPanel = new JPanel(new GridBagLayout());
        sellInterface.add(sellTopLeftPanel, BorderLayout.CENTER);
        sellTopLeftPanel.setBorder(BorderFactory.createTitledBorder("Selling an investment"));
        GridBagConstraints sellLabelConstraints = new GridBagConstraints();
        sellLabelConstraints.anchor = GridBagConstraints.WEST;
        sellLabelConstraints.gridx = 0;
        sellLabelConstraints.gridy = 0;
        sellLabelConstraints.weightx = 0.5;
        sellLabelConstraints.weighty = 1;
        sellLabelConstraints.insets = new Insets(5, 10, 5, 10);
        GridBagConstraints sellFieldConstraints = new GridBagConstraints();
        sellFieldConstraints.anchor = GridBagConstraints.WEST;
        sellFieldConstraints.gridx = 1;
        sellFieldConstraints.gridy = 0;
        sellFieldConstraints.weightx = 0.5;
        sellFieldConstraints.weighty = 1;
        sellFieldConstraints.insets = new Insets(5, 10, 5, 10);
        sellLabelConstraints.gridy++;
        sellTopLeftPanel.add(new JLabel("Symbol"), sellLabelConstraints);
        JTextField sellSymbolField = new JTextField(10);
        sellFieldConstraints.gridy++;
        sellTopLeftPanel.add(sellSymbolField, sellFieldConstraints);
        sellLabelConstraints.gridy++;
        sellTopLeftPanel.add(new JLabel("Quantity"), sellLabelConstraints);
        JTextField sellQuantityField = new JTextField(6);
        sellFieldConstraints.gridy++;
        sellTopLeftPanel.add(sellQuantityField, sellFieldConstraints);
        sellLabelConstraints.gridy++;
        sellTopLeftPanel.add(new JLabel("Price"), sellLabelConstraints);
        JTextField sellPriceField = new JTextField(6);
        sellFieldConstraints.gridy++;
        sellTopLeftPanel.add(sellPriceField, sellFieldConstraints);
        JPanel sellTopRightPanel = new JPanel(new GridBagLayout());
        sellTopRightPanel.setBorder(BorderFactory.createEmptyBorder());
        sellInterface.add(sellTopRightPanel, BorderLayout.EAST);
        GridBagConstraints sellButtonConstraints = new GridBagConstraints();
        sellButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        sellButtonConstraints.insets = new Insets(10, 10, 10, 10);
        sellButtonConstraints.weighty = 1;
        sellButtonConstraints.gridy = 0;
        JButton sellResetButton = new JButton(" Reset ");
        sellTopRightPanel.add(sellResetButton, sellButtonConstraints);
        JButton sellSellButton = new JButton(" Sell ");
        sellButtonConstraints.gridy++;
        sellTopRightPanel.add(sellSellButton, sellButtonConstraints);
        JPanel sellBottomPanel = new JPanel(new BorderLayout());
        sellInterface.add(sellBottomPanel, BorderLayout.SOUTH);
        sellBottomPanel.setBorder(BorderFactory.createTitledBorder("Messages"));
        JTextArea sellMessagesArea = new JTextArea(6, 30);
        sellMessagesArea.setEditable(false);
        sellBottomPanel.add(new JScrollPane(sellMessagesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        
        //UPDATE SETUP
        JPanel updateInterface = new JPanel();
        updateInterface.setLayout(new BorderLayout());
        JPanel updateTopLeftPanel = new JPanel(new GridBagLayout());
        updateInterface.add(updateTopLeftPanel, BorderLayout.CENTER);
        updateTopLeftPanel.setBorder(BorderFactory.createTitledBorder("Updating investments"));
        GridBagConstraints updateLabelConstraints = new GridBagConstraints();
        updateLabelConstraints.anchor = GridBagConstraints.WEST;
        updateLabelConstraints.gridx = 0;
        updateLabelConstraints.gridy = 0;
        updateLabelConstraints.weightx = 0.5;
        updateLabelConstraints.weighty = 1;
        updateLabelConstraints.insets = new Insets(5, 10, 5, 10);
        GridBagConstraints updateFieldConstraints = new GridBagConstraints();
        updateFieldConstraints.anchor = GridBagConstraints.WEST;
        updateFieldConstraints.gridx = 1;
        updateFieldConstraints.gridy = 0;
        updateFieldConstraints.weightx = 0.5;
        updateFieldConstraints.weighty = 1;
        updateFieldConstraints.insets = new Insets(5, 10, 5, 10);
        updateLabelConstraints.gridy++;
        updateTopLeftPanel.add(new JLabel("Symbol"), updateLabelConstraints);
        JTextField updateSymbolField = new JTextField(10);
        updateSymbolField.setEditable(false);
        updateFieldConstraints.gridy++;
        updateTopLeftPanel.add(updateSymbolField, updateFieldConstraints);
        updateLabelConstraints.gridy++;
        updateTopLeftPanel.add(new JLabel("Name"), updateLabelConstraints);
        JTextField updateNameField = new JTextField(20);
        updateNameField.setEditable(false);
        updateFieldConstraints.gridy++;
        updateTopLeftPanel.add(updateNameField, updateFieldConstraints);
        updateLabelConstraints.gridy++;
        updateTopLeftPanel.add(new JLabel("Price"), updateLabelConstraints);
        JTextField updatePriceField = new JTextField(6);
        updateFieldConstraints.gridy++;
        updateTopLeftPanel.add(updatePriceField, updateFieldConstraints);
        JPanel updateTopRightPanel = new JPanel(new GridBagLayout());
        updateTopRightPanel.setBorder(BorderFactory.createEmptyBorder());
        updateInterface.add(updateTopRightPanel, BorderLayout.EAST);
        GridBagConstraints updateButtonConstraints = new GridBagConstraints();
        updateButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        updateButtonConstraints.insets = new Insets(10, 10, 10, 10);
        updateButtonConstraints.weighty = 1;
        updateButtonConstraints.gridy = 0;
        JButton updatePrevButton = new JButton(" Prev ");
        updatePrevButton.setEnabled(false);
        updateTopRightPanel.add(updatePrevButton, updateButtonConstraints);
        JButton updateNextButton = new JButton(" Next ");
        updateNextButton.setEnabled(false);
        updateButtonConstraints.gridy++;
        updateTopRightPanel.add(updateNextButton, updateButtonConstraints);
        JButton updateSaveButton = new JButton(" Save ");
        updateButtonConstraints.gridy++;
        updateTopRightPanel.add(updateSaveButton, updateButtonConstraints);
        JPanel updateBottomPanel = new JPanel(new BorderLayout());
        updateInterface.add(updateBottomPanel, BorderLayout.SOUTH);
        updateBottomPanel.setBorder(BorderFactory.createTitledBorder("Messages"));
        JTextArea updateMessagesArea = new JTextArea(6, 30);
        updateMessagesArea.setEditable(false);
        updateBottomPanel.add(new JScrollPane(updateMessagesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        
        //GAIN SETUP
        JPanel gainInterface = new JPanel();
        gainInterface.setLayout(new BorderLayout());
        JPanel gainTopLeftPanel = new JPanel(new GridBagLayout());
        gainInterface.add(gainTopLeftPanel, BorderLayout.CENTER);
        gainTopLeftPanel.setBorder(BorderFactory.createTitledBorder("Getting total gain"));
        GridBagConstraints gainLabelConstraints = new GridBagConstraints();
        gainLabelConstraints.anchor = GridBagConstraints.WEST;
        gainLabelConstraints.gridx = 0;
        gainLabelConstraints.gridy = 0;
        gainLabelConstraints.weightx = 0.5;
        gainLabelConstraints.weighty = 1;
        gainLabelConstraints.insets = new Insets(5, 10, 5, 10);
        GridBagConstraints gainFieldConstraints = new GridBagConstraints();
        gainFieldConstraints.anchor = GridBagConstraints.WEST;
        gainFieldConstraints.gridx = 1;
        gainFieldConstraints.gridy = 0;
        gainFieldConstraints.weightx = 0.5;
        gainFieldConstraints.weighty = 1;
        gainFieldConstraints.insets = new Insets(5, 10, 5, 10);
        gainLabelConstraints.gridy++;
        gainTopLeftPanel.add(new JLabel("Total gain"), gainLabelConstraints);
        JTextField gainTotalGainField = new JTextField(10);
        gainTotalGainField.setEditable(false);
        gainFieldConstraints.gridy++;
        gainTopLeftPanel.add(gainTotalGainField, gainFieldConstraints);
        JPanel gainTopRightPanel = new JPanel(new GridBagLayout());
        gainTopRightPanel.setBorder(BorderFactory.createEmptyBorder());
        gainInterface.add(gainTopRightPanel, BorderLayout.EAST);
        JPanel gainBottomPanel = new JPanel(new BorderLayout());
        gainInterface.add(gainBottomPanel, BorderLayout.SOUTH);
        gainBottomPanel.setBorder(BorderFactory.createTitledBorder("Individual gains"));
        JTextArea gainMessagesArea = new JTextArea(20, 30);
        gainMessagesArea.setEditable(false);
        gainBottomPanel.add(new JScrollPane(gainMessagesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        
        //SEARCH SETUP
        JPanel searchInterface = new JPanel();
        searchInterface.setLayout(new BorderLayout());
        JPanel searchTopLeftPanel = new JPanel(new GridBagLayout());
        searchInterface.add(searchTopLeftPanel, BorderLayout.CENTER);
        searchTopLeftPanel.setBorder(BorderFactory.createTitledBorder("Searching investments"));
        GridBagConstraints searchLabelConstraints = new GridBagConstraints();
        searchLabelConstraints.anchor = GridBagConstraints.WEST;
        searchLabelConstraints.gridx = 0;
        searchLabelConstraints.gridy = 0;
        searchLabelConstraints.weightx = 0.5;
        searchLabelConstraints.weighty = 1;
        searchLabelConstraints.insets = new Insets(5, 10, 5, 10);
        GridBagConstraints searchFieldConstraints = new GridBagConstraints();
        searchFieldConstraints.anchor = GridBagConstraints.WEST;
        searchFieldConstraints.gridx = 1;
        searchFieldConstraints.gridy = 0;
        searchFieldConstraints.weightx = 0.5;
        searchFieldConstraints.weighty = 1;
        searchFieldConstraints.insets = new Insets(5, 10, 5, 10);
        searchLabelConstraints.gridy++;
        searchTopLeftPanel.add(new JLabel("Symbol"), searchLabelConstraints);
        JTextField searchSymbolField = new JTextField(10);
        searchFieldConstraints.gridy++;
        searchTopLeftPanel.add(searchSymbolField, searchFieldConstraints);
        searchLabelConstraints.gridy++;
        searchTopLeftPanel.add(new JLabel("Name keywords"), searchLabelConstraints);
        JTextField searchNameKeywordsField = new JTextField(20);
        searchFieldConstraints.gridy++;
        searchTopLeftPanel.add(searchNameKeywordsField, searchFieldConstraints);
        searchLabelConstraints.gridy++;
        searchTopLeftPanel.add(new JLabel("Low price"), searchLabelConstraints);
        JTextField searchLowPriceField = new JTextField(6);
        searchFieldConstraints.gridy++;
        searchTopLeftPanel.add(searchLowPriceField, searchFieldConstraints);
        searchLabelConstraints.gridy++;
        searchTopLeftPanel.add(new JLabel("High price"), searchLabelConstraints);
        JTextField searchHighPriceField = new JTextField(6);
        searchFieldConstraints.gridy++;
        searchTopLeftPanel.add(searchHighPriceField, searchFieldConstraints);
        JPanel searchTopRightPanel = new JPanel(new GridBagLayout());
        searchTopRightPanel.setBorder(BorderFactory.createEmptyBorder());
        searchInterface.add(searchTopRightPanel, BorderLayout.EAST);
        GridBagConstraints searchButtonConstraints = new GridBagConstraints();
        searchButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        searchButtonConstraints.insets = new Insets(10, 10, 10, 10);
        searchButtonConstraints.weighty = 1;
        searchButtonConstraints.gridy = 0;
        JButton searchResetButton = new JButton(" Reset ");
        searchTopRightPanel.add(searchResetButton, searchButtonConstraints);
        JButton searchSearchButton = new JButton(" Search ");
        searchButtonConstraints.gridy++;
        searchTopRightPanel.add(searchSearchButton, searchButtonConstraints);
        JPanel searchBottomPanel = new JPanel(new BorderLayout());
        searchInterface.add(searchBottomPanel, BorderLayout.SOUTH);
        searchBottomPanel.setBorder(BorderFactory.createTitledBorder("Search results"));
        JTextArea searchMessagesArea = new JTextArea(6, 30);
        searchMessagesArea.setEditable(false);
        searchBottomPanel.add(new JScrollPane(searchMessagesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        
        //
        //JMenu listeners
        //
        //swap to buy interface
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyTypeSelect.setSelectedItem("  stock  ");
                buySymbolField.setText("");
                buyNameField.setText("");
                buyQuantityField.setText("");
                buyPriceField.setText("");
                buyMessagesArea.setText("");
                
                scrollPane.setVisible(false);
                
                remove(initialInterface);
                add(buyInterface);
                remove(sellInterface);
                remove(updateInterface);
                remove(gainInterface);
                remove(searchInterface);
                
                initialInterface.setVisible(false);
                buyInterface.setVisible(true);
                sellInterface.setVisible(false);
                updateInterface.setVisible(false);
                gainInterface.setVisible(false);
                searchInterface.setVisible(false);
            }
        });
        //swap to sell interface
        sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellSymbolField.setText("");
                sellQuantityField.setText("");
                sellPriceField.setText("");
                sellMessagesArea.setText("");
                
                scrollPane.setVisible(false);
                
                remove(initialInterface);
                remove(buyInterface);
                add(sellInterface);
                remove(updateInterface);
                remove(gainInterface);
                remove(searchInterface);
                
                initialInterface.setVisible(false);
                buyInterface.setVisible(false);
                sellInterface.setVisible(true);
                updateInterface.setVisible(false);
                gainInterface.setVisible(false);
                searchInterface.setVisible(false);
            }
        });
        //swap to update interface
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(updateSymbolField.getText())) {
                        if(investments.get(i) instanceof Stock) {
                            Stock s = (Stock)investments.get(i);
                            updatePriceField.setText(s.getPrice() + "");
                        } else if(investments.get(i) instanceof MutualFund) {
                            MutualFund m = (MutualFund)investments.get(i);
                            updatePriceField.setText(m.getPrice() + "");
                        }
                    }
                }
                
                scrollPane.setVisible(false);
                
                remove(initialInterface);
                remove(buyInterface);
                remove(sellInterface);
                add(updateInterface);
                remove(gainInterface);
                remove(searchInterface);
                
                initialInterface.setVisible(false);
                buyInterface.setVisible(false);
                sellInterface.setVisible(false);
                updateInterface.setVisible(true);
                gainInterface.setVisible(false);
                searchInterface.setVisible(false);
            }
        });
        //swap to get gain interface
        getGain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(false);
                remove(initialInterface);
                remove(buyInterface);
                remove(sellInterface);
                remove(updateInterface);
                
                add(gainInterface);
                
                remove(searchInterface);
                
                initialInterface.setVisible(false);
                buyInterface.setVisible(false);
                sellInterface.setVisible(false);
                updateInterface.setVisible(false);
                gainInterface.setVisible(true);
                searchInterface.setVisible(false);
            }
        });
        //swap to search interface
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSymbolField.setText("");
                searchNameKeywordsField.setText("");
                searchLowPriceField.setText("");
                searchHighPriceField.setText("");
                searchMessagesArea.setText("");
                
                scrollPane.setVisible(false);
                
                remove(initialInterface);
                remove(buyInterface);
                remove(sellInterface);
                remove(updateInterface);
                remove(gainInterface);
                add(searchInterface);
                
                initialInterface.setVisible(false);
                buyInterface.setVisible(false);
                sellInterface.setVisible(false);
                updateInterface.setVisible(false);
                gainInterface.setVisible(false);
                searchInterface.setVisible(true);
            }
        });
        //upon clicking quit, save the program state and exit
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndQuit(investments);
            }
        });
        
        //Buy interface component listeners:
        buyResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buySymbolField.setText("");
                buyNameField.setText("");
                buyQuantityField.setText("");
                buyPriceField.setText("");
            }
        });
        
        //Listen to update on the buy symbol field and turn the characters into uppercase
        buySymbolField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                int pos = buySymbolField.getCaretPosition();
                buySymbolField.setText(buySymbolField.getText().toUpperCase());
                buySymbolField.setCaretPosition(pos);
            }
        });
        
        //auto filler of information if the investment being bought already exists
        buySymbolField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { fieldUpdated(); }
            @Override public void removeUpdate(DocumentEvent e) { fieldUpdated(); }
            @Override public void changedUpdate(DocumentEvent e) { fieldUpdated(); }
            
            public void fieldUpdated() {
                String currSymbol = buySymbolField.getText();
                
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().toLowerCase().equals(currSymbol.toLowerCase())) { //curr symbol exists in the investments
                        buyNameField.setText(investments.get(i).getInvestmentName());
                        if(investments.get(i) instanceof Stock) {
                            buyTypeSelect.setSelectedItem("  stock  ");
                        } else if(investments.get(i) instanceof MutualFund) {
                            buyTypeSelect.setSelectedItem("  mutual fund  ");
                        }
                        buyTypeSelect.setEditable(false);
                        buyNameField.setEditable(false);
                        break;
                    } else {
                        buyTypeSelect.setEditable(true);
                        buyNameField.setEditable(true);
                    }
                }
            }
        });
        
        //upon clicking the buy button, do needed checks, and continue with the purchase
        buyBuyButton.addActionListener(new ActionListener() {
            int investmentNum = investments.size();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                buyMessagesArea.setText("");
                boolean success = true;
                
                Investment myInvestment = new Investment();
                
                String investmentSymbol = buySymbolField.getText();
                if(investmentSymbol.equals("")) {
                    buyMessagesArea.append("Please input an investment symbol\n");
                    success = false;
                }
                if(buyNameField.getText().equals("")) {
                    buyMessagesArea.append("Please input the name of the investment\n");
                    success = false;
                }
                if(buyQuantityField.getText().equals("")) {
                    buyMessagesArea.append("Please input the quantity you wish to buy\n");
                    success = false;
                }
                if(buyPriceField.getText().equals("")) {
                    buyMessagesArea.append("Please input the price at which to buy the investment\n");
                    success = false;
                }
                
                if(!success) {
                    return;
                }
                
                int quantity;
                try {
                    quantity = Integer.parseInt(buyQuantityField.getText());
                } catch(NumberFormatException f) {
                    buyMessagesArea.append("Invalid quantity\n");
                    return;
                }
                if(quantity<0) {
                    buyMessagesArea.append("Please input a valid quantity\n");
                    success = false;
                }

                double price;
                try {
                    price = Double.parseDouble(buyPriceField.getText());
                } catch(NumberFormatException f) {
                    buyMessagesArea.append("Invalid price\n");
                    return;
                }
                if(price<0) {
                    buyMessagesArea.append("Please input a valid price\n");
                    success = false;
                }

                if(!success) {
                    return;
                }
                
                //IF THE INVESTMENT EXISTS:
                boolean found = false;
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(investmentSymbol)) {
                        found = true;
                        
                        investments.get(i).setQuantity(investments.get(i).getQuantity() + quantity);
                        
                        if(buyTypeSelect.getSelectedItem().toString().equals("  stock  ")) {
                            investments.get(i).setBookValue(investments.get(i).getBookValue() + (quantity*price) + 9.99);
                        } else if(buyTypeSelect.getSelectedItem().toString().equals("  mutual fund  ")) {
                            investments.get(i).setBookValue(investments.get(i).getBookValue() + (quantity*price));
                        }
                        
                        if(investments.get(i) instanceof Stock) { 
                            buyMessagesArea.setText("Bought "+ buyQuantityField.getText() + " more of "+buySymbolField.getText() + " stock\n");
                        } else if(investments.get(i) instanceof MutualFund) {
                            buyMessagesArea.setText("Bought "+ buyQuantityField.getText() + " more of "+buySymbolField.getText() + " mutual fund\n");
                        }
                        
                        buySymbolField.setText("");
                        buyNameField.setText("");
                        buyQuantityField.setText("");
                        buyPriceField.setText("");
                    }
                }
                //IF THE INVESTMENT DOESN'T EXIST
                if(!found) {
                    myInvestment.setSymbol(investmentSymbol);
                    String investmentName = buyNameField.getText();
                    myInvestment.setInvestmentName(investmentName);
                    
                    String tokens[] = investmentName.split(" ");

                    ArrayList<Integer> intArray = new ArrayList<Integer>();
                    for(int i=0; i<tokens.length; i++) {
                        String lowerCase = tokens[i].toLowerCase();
                        if(hmap.containsKey(lowerCase)) {
                            intArray = hmap.get(lowerCase);
                            intArray.add(investmentNum);
                        } else {
                            intArray = new ArrayList<>();
                            intArray.add(investmentNum);
                            hmap.put(lowerCase, intArray);
                        }
                    }
                    
                    myInvestment.setQuantity(quantity);
                    myInvestment.setPrice(price);
                    
                    if(buyTypeSelect.getSelectedItem().toString().equals("  stock  ")) {
                        myInvestment.setBookValue(quantity*price + 9.99);
                        
                        Stock myStock = new Stock(myInvestment.getSymbol(), myInvestment.getInvestmentName(), myInvestment.getQuantity(), myInvestment.getPrice(), myInvestment.getBookValue());
                        investments.add(myStock);
                        buyMessagesArea.setText("Bought : "+myStock.toString());
                    } else if(buyTypeSelect.getSelectedItem().toString().equals("  mutual fund  ")) {
                        myInvestment.setBookValue(quantity*price);
                        
                        MutualFund myMutualFund = new MutualFund(myInvestment.getSymbol(), myInvestment.getInvestmentName(), myInvestment.getQuantity(), myInvestment.getPrice(), myInvestment.getBookValue());
                        investments.add(myMutualFund);
                        buyMessagesArea.setText("Bought : "+myMutualFund.toString());
                    }
                    //clear fields upon finished purchase
                    buySymbolField.setText("");
                    buyNameField.setText("");
                    buyQuantityField.setText("");
                    buyPriceField.setText("");
                }

            }
        });
        
        //Sell interface component listeners:
        sellSymbolField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                int pos = sellSymbolField.getCaretPosition();
                sellSymbolField.setText(sellSymbolField.getText().toUpperCase());
                sellSymbolField.setCaretPosition(pos);
            }
        });
        
        //upon clicking the reset button in the sell interface
        sellResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellSymbolField.setText("");
                sellQuantityField.setText("");
                sellPriceField.setText("");
            }
        });
        
        //upon clicking the sell button in the sell interface, check validity, and continue as needed
        sellSellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellMessagesArea.setText("");
                boolean success = true;
                
                String investmentSymbol = sellSymbolField.getText();
                
                if(investmentSymbol.equals("")) {
                    sellMessagesArea.append("Please input an investment symbol\n");
                    success = false;
                }
                if(sellQuantityField.getText().equals("")) {
                    sellMessagesArea.append("Please input the quantity you wish to sell\n");
                    success = false;
                }
                if(sellPriceField.getText().equals("")) {
                    sellMessagesArea.append("Please input the price at which to buy the investment\n");
                    success = false;
                }

                boolean foundInvestment = false;
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(investmentSymbol)) {
                        foundInvestment = true;
                    }
                }
                
                if(!foundInvestment) {
                    sellMessagesArea.append("Could not find investment\n");
                    success = false;
                }
                
                double actualPrice;
                try {
                    actualPrice = Double.parseDouble(sellPriceField.getText());
                } catch(NumberFormatException f) {
                    sellMessagesArea.append("Price field not formatted correctly\n");
                    return;
                }
                
                int quantity;
                try {
                    quantity = Integer.parseInt(sellQuantityField.getText());
                } catch(NumberFormatException f) {
                    sellMessagesArea.append("Quantity field not formatted correctly\n");
                    return;
                }
                
                if(actualPrice<0) {
                    sellMessagesArea.append("Please input a valid price\n");
                    success = false;
                }
                if(quantity<0) {
                    sellMessagesArea.append("Please input a valid quantity\n");
                    success = false;
                }
                
                if(!success) {
                    return;
                }
                
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(investmentSymbol)) {
                        if(investments.get(i).getQuantity() >= quantity && quantity>=0) {
                            int origQuantity = investments.get(i).getQuantity();
                            investments.get(i).setBookValue(investments.get(i).getBookValue() * ((origQuantity - quantity)/origQuantity));
                            investments.get(i).setQuantity(origQuantity - quantity);

                            if(investments.get(i) instanceof Stock) {
                                sellMessagesArea.append("Sold "+quantity+" stock(s). Payment recieved: $"+(quantity*actualPrice - 9.99)+"\n");
                            } else if(investments.get(i) instanceof MutualFund) {
                                sellMessagesArea.append("Sold "+quantity+" mutual fund(s). Payment recieved: $"+(quantity*actualPrice - 45)+"\n");
                            }
                            if(investments.get(i).getQuantity()==0) {
                                String keysToShift = "";
                                for(int j=i; j<investments.size(); j++) {
                                    String investmentName = investments.get(j).getInvestmentName();
                                    keysToShift += (investmentName+" ");
                                }

                                String keysToShiftTok[] = keysToShift.split(" ");
                                for(int j=0; j<keysToShiftTok.length; j++) {
                                    String tok = keysToShiftTok[j];

                                    ArrayList<Integer> jumpTos = hmap.get(tok.toLowerCase());
                                    for(int k=0; k<jumpTos.size(); k++) {

                                        if(jumpTos.get(k) > i) {
                                            jumpTos.set(k, jumpTos.get(k)-1);
                                            hmap.put(tok, jumpTos);
                                        } else if(jumpTos.get(k) == i) {
                                            jumpTos.remove(k);
                                            hmap.put(tok, jumpTos);
                                        }
                                    }
                                }

                                investments.remove(i);
                            }
                        } else {
                            sellMessagesArea.append("Tried to sell an invalid amount of " + investmentSymbol + " investments\n");
                        }
                    }
                }
            }
        });
        
        //Update interface component listeners:
        if(investments.size() > 0) {
            updateNextButton.setEnabled(true);
            if(investments.get(0) instanceof Stock) {
                Stock s = (Stock)investments.get(0);
                
                updateSymbolField.setText(s.getSymbol());
                updateNameField.setText(s.getInvestmentName());
                updatePriceField.setText(s.getPrice() + "");
            } else if(investments.get(0) instanceof MutualFund) {
                MutualFund m = (MutualFund)investments.get(0);
                
                updateSymbolField.setText(m.getSymbol());
                updateNameField.setText(m.getInvestmentName());
                updatePriceField.setText(m.getPrice() + "");
            }
        }
        
        //move to previous investment upon clicking prev button in update interface
        updatePrevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMessagesArea.setText("");
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(updateSymbolField.getText())) {
                        if(i-1 >= 0) {
                            if(investments.get(i-1) instanceof Stock) {
                                Stock s = (Stock)investments.get(i-1);

                                updateSymbolField.setText(s.getSymbol());
                                updateNameField.setText(s.getInvestmentName());
                                updatePriceField.setText(s.getPrice() + "");
                            } else if(investments.get(i-1) instanceof MutualFund) {
                                MutualFund m = (MutualFund)investments.get(i-1);

                                updateSymbolField.setText(m.getSymbol());
                                updateNameField.setText(m.getInvestmentName());
                                updatePriceField.setText(m.getPrice() + "");
                            }
                            
                            if(i-1 == 0) {
                                updatePrevButton.setEnabled(false);
                            } else {
                                updateNextButton.setEnabled(true);
                            }
                        }
                        break;
                    }
                }
            }
        });
        
        //move to next investment upon clicking prev button in update interface
        updateNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMessagesArea.setText("");
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(updateSymbolField.getText())) {
                        if(i+1 < investments.size()) {
                            if(investments.get(i+1) instanceof Stock) {
                                Stock s = (Stock)investments.get(i+1);

                                updateSymbolField.setText(s.getSymbol());
                                updateNameField.setText(s.getInvestmentName());
                                updatePriceField.setText(s.getPrice() + "");
                            } else if(investments.get(i+1) instanceof MutualFund) {
                                MutualFund m = (MutualFund)investments.get(i+1);

                                updateSymbolField.setText(m.getSymbol());
                                updateNameField.setText(m.getInvestmentName());
                                updatePriceField.setText(m.getPrice() + "");
                            }
                            
                            if(i+2 == investments.size()) {
                                updateNextButton.setEnabled(false);
                            } else {
                                updatePrevButton.setEnabled(true);
                            }
                        }
                        break;
                    }
                }
            }
        });
        
        //save new price to investment upon clicking the save button in the update interface
        updateSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMessagesArea.setText("");
                for(int i=0; i<investments.size(); i++) {
                    if(investments.get(i).getSymbol().equals(updateSymbolField.getText())) {
                        double newPrice;
                        try {
                            newPrice = Double.parseDouble(updatePriceField.getText());
                        } catch(NumberFormatException f) {
                            updateMessagesArea.append("Price field not formatted correctly\n");
                            return;
                        }
                        if(newPrice<0) {
                            updateMessagesArea.append("Price cannot have a negative value\n");
                            return;
                        }
                    
                        investments.get(i).setPrice(newPrice);
                        updateMessagesArea.setText("Investment updated: \n" + investments.get(i).toString());
                    }
                }
            }
        });
        
        //Gain interface component listeners:
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        double totalGain = 0.0;

        //Sum the gains throughout the stocks and the mutual funds
        for(int i=0; i<investments.size(); i++) {
            if(investments.get(i) instanceof Stock) {
                Stock s = (Stock)investments.get(i);
                gainMessagesArea.append("["+s.getSymbol() +  "]" + s.getInvestmentName() +" : " + df.format(s.getGain()) + "\n");
                totalGain+= s.getGain();
            } else if(investments.get(i) instanceof MutualFund) {
                MutualFund m = (MutualFund)investments.get(i);
                gainMessagesArea.append("["+m.getSymbol() +  "]" + m.getInvestmentName() +" : " + df.format(m.getGain()) + "\n");
                totalGain+= m.getGain();
            }
        }

        gainTotalGainField.setText(df.format(totalGain));
        
        //Search interface component listeners:
        searchResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSymbolField.setText("");
                searchNameKeywordsField.setText("");
                searchLowPriceField.setText("");
                searchHighPriceField.setText("");
            }
        });
        //search button in the search interface clicked
        searchSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMessagesArea.setText("");
                ArrayList<Integer> searchRes = new ArrayList<Integer>();
                
                String keywords = searchNameKeywordsField.getText();
                keywords = keywords.toLowerCase();

                //search all keywords
                if(!keywords.equals("")) {
                    String keywordTokens[] = keywords.split(" ");
                    for(int i=0; i<keywordTokens.length; i++) {
                        if(searchRes.isEmpty()) {
                            ArrayList<Integer> test = hmap.get(keywordTokens[i]);
                            if(test!=null) {
                                for(int j=0; j<test.size(); j++) {
                                    searchRes.add(test.get(j));
                                }
                            }
                        } else {
                            searchRes = intersection(searchRes, hmap.get(keywordTokens[i]));
                        }
                    }
                }
                
                ArrayList<Investment> possibleInvestments = new ArrayList<>();
                if(!keywords.equals("")) {
                    for(int i=0; i<searchRes.size(); i++) {
                        Investment found = investments.get(searchRes.get(i));

                        if(found instanceof Stock) {
                            Stock newStock = new Stock(investments.get(searchRes.get(i)).getSymbol(), investments.get(searchRes.get(i)).getInvestmentName(), investments.get(searchRes.get(i)).getQuantity(), investments.get(searchRes.get(i)).getPrice(), investments.get(searchRes.get(i)).getBookValue());
                            possibleInvestments.add(newStock);

                        } else if(found instanceof MutualFund) {
                            MutualFund newMutualFund = new MutualFund(investments.get(searchRes.get(i)).getSymbol(), investments.get(searchRes.get(i)).getInvestmentName(), investments.get(searchRes.get(i)).getQuantity(), investments.get(searchRes.get(i)).getPrice(), investments.get(searchRes.get(i)).getBookValue());
                            possibleInvestments.add(newMutualFund);
                        }
                    }
                } else {
                    for(int i=0; i<investments.size(); i++) {
                        Investment found = investments.get(i);

                        if(found instanceof Stock) {
                            Stock newStock = new Stock(investments.get(i).getSymbol(), investments.get(i).getInvestmentName(), investments.get(i).getQuantity(), investments.get(i).getPrice(), investments.get(i).getBookValue());
                            possibleInvestments.add(newStock);

                        } else if(found instanceof MutualFund) {
                            MutualFund newMutualFund = new MutualFund(investments.get(i).getSymbol(), investments.get(i).getInvestmentName(), investments.get(i).getQuantity(), investments.get(i).getPrice(), investments.get(i).getBookValue());
                            possibleInvestments.add(newMutualFund);
                        }
                    }
                }
                
                String symbol = searchSymbolField.getText();
                symbol = symbol.toLowerCase();

                if(!symbol.equals("")) {
                    for(int i=0; i<possibleInvestments.size(); i++) {
                        if(!possibleInvestments.get(i).getSymbol().toLowerCase().equals(symbol)) {
                            possibleInvestments.get(i).setQuantity(-1); //flag investment as out of the search result
                        }
                    }
                }

                //Low and high fields narrowing down the results
                String lowPriceInput = searchLowPriceField.getText();
                String highPriceInput = searchHighPriceField.getText();
                
                if(!lowPriceInput.equals("")) {
                    double lowPrice;
                    try {
                        lowPrice = Double.parseDouble(lowPriceInput);
                    } catch(NumberFormatException f) {
                        searchMessagesArea.append("Low price field not formatted correctly\n");
                        return;
                    }
                    
                    lowPrice -=0.001; //fix floating point comparison errors

                    for(int i=0; i<possibleInvestments.size(); i++) {
                        if(possibleInvestments.get(i).getPrice()<=lowPrice) {
                            possibleInvestments.get(i).setQuantity(-1);
                        }
                    }
                }
                if(!highPriceInput.equals("")) {
                    double highPrice;
                    try {
                        highPrice = Double.parseDouble(highPriceInput);
                    } catch(NumberFormatException f) {
                        searchMessagesArea.append("High price field not formatted correctly\n");
                        return;
                    }
                    
                    highPrice +=0.001; //fix floating point comparison errors

                    for(int i=0; i<possibleInvestments.size(); i++) {
                        if(possibleInvestments.get(i).getPrice()>=highPrice) {
                            possibleInvestments.get(i).setQuantity(-1);
                        }
                    }
                }

                //Print out the investments that are remaining from the search filters
                searchMessagesArea.setText("");
                for(int i=0; i<possibleInvestments.size(); i++) {
                    if(possibleInvestments.get(i).getQuantity() != -1) {
                        searchMessagesArea.append(possibleInvestments.get(i).toString() + "\n");
                    }
                }
            }
        });
        
        //Initial interface display:
        add(initialInterface);
        remove(buyInterface);
        remove(sellInterface);
        remove(updateInterface);
        remove(gainInterface);
        remove(searchInterface);

        initialInterface.setVisible(true);
        buyInterface.setVisible(false);
        sellInterface.setVisible(false);
        updateInterface.setVisible(false);
        gainInterface.setVisible(false);
        searchInterface.setVisible(false);
    }
    
    /*
     * START HERE
     */
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        portfolio.pack();
        portfolio.setVisible(true);
    }
    
    /**
    * Calculates the intersection of two array lists
    * @param list1 the first list to be intersected with the other
    * @param list2 the second list to be intersected with the first
    * @return An array list that has the interesection of both lists
    */
    public static <Integer> ArrayList<Integer> intersection(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for(Integer t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    //
   /**
   * Run on quit. This function will save all memory loaded investments onto the disk, and exit the program
   * @param investments The investments to be saved onto the disk
   * @return Nothing.
   */
    public void saveAndQuit(ArrayList<Investment> investments) {
        try {
            PrintWriter pw = new PrintWriter("investments.csv");

            for(int i=0; i<investments.size(); i++) {
                if(investments.get(i) instanceof Stock) {
                    pw.write("stock,");
                } else if(investments.get(i) instanceof MutualFund) {
                    pw.write("mutualfund,");
                }
                pw.write(investments.get(i).getSymbol()+","+investments.get(i).getInvestmentName()+","+investments.get(i).getQuantity()+","+investments.get(i).getPrice()+","+investments.get(i).getBookValue()+"\n");
            }

            pw.close();
        } catch(FileNotFoundException e) {
            System.out.println("file save error\n");
        }

        dispose();
        System.exit(0);
    }
}
