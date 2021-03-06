package byui.cit260.oregontrail.view;

import java.util.Random;
import java.util.Scanner;

public class GuessView {
    
    private int[] numberList = null;
    int answer = 0;
    int count = 0;
    
    public GuessView() {
       createNumberList();
    }
    
    public void createNumberList() {
        numberList = new int[100];
        Random ran = new Random();
        
        answer = ran.nextInt(100);
        count = 1;
        for (int i = 0; i < numberList.length; i++) {
            numberList[i] = count++;
            System.out.println(i + " " + numberList[i]);
        }
        guess();
    }
    
    public void guess() {
        System.out.println("Guess a number:");
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        
        int response = Integer.parseInt(scan.nextLine());
        
        if (response == answer) {
            TravelView.displayVictoryScreen();        
            }
        else {
            System.out.println("Sorry, wrong guess. Try again.");
            guess();
        }
    }
}