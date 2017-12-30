package moneycalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MoneyCalculator {

    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
    
    private double amount;
    private double exchangeRate;
    
    private void execute() throws Exception {
        input();
        process();
        output();
    }
    
    private void input() {
        System.out.print("Enter a dollar amount: ");
        Scanner scanner = new Scanner(System.in);
        amount = Double.parseDouble(scanner.next());
    }
    
    private void process() throws Exception {
        exchangeRate = getExchangeRate("USD", "EUR");
    }

    private void output() {
        System.out.println(amount + " USD are equal to " + 
                amount * exchangeRate + " EUR");
    }
    
    private static double getExchangeRate (String from, String to) throws Exception {
        URL url = new URL("http://api.fixer.io/latest?base=" + from + "&symbols=" + to);
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");
        InputStreamReader input = new InputStreamReader(conection.getInputStream());
        try (BufferedReader reader = new BufferedReader(input)) {
            String line = reader.readLine();
            line = line.substring(line.indexOf(to)+5, line.indexOf("}"));
            return Double.parseDouble(line);
        }
    }

}