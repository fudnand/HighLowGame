package gamepack;
import java.util.*;
import java.time.*;

/**
 * This program hlets the user play HighLow, a simple card game 
 * that is described in the output statements at the beginning of 
 * the main() routine.  After the user plays several rounds, 
 * the user's average score is reported.
 */
public class HighLowGame {
   public static void main(String[] args) {   
	   GameController controller= new GameController();   
	   controller.runGame();   
   } 
} 

class IOHandler implements IGameView{
	Scanner  sc = new Scanner(System.in);
	char input;
	private static char[] matches = new char[]{'l', 'h', 's', 'f', 't'};

	@Override
	public void display(String message) {
		System.out.println(message);	
	}

	@Override
	public Character getInput() {
		boolean isCorrectInput = false;
		do {
			System.out.println("inside get input");
            input = sc.next().charAt(0);
            input = Character.toLowerCase(input);
            for(int i = 0; i < matches.length; i++){
            	if (input == matches[i]) {   
            		return new Character(input);
            	}            	
            }
            System.out.print("Please respond with an expected character:  ");            
        } while (!isCorrectInput);    
		return null;
	}

	@Override
	public void getResult(String prompt) {
		// TODO Auto-generated method stub		
	}	
}

class GameController implements IGameControl{  //game model + game control
	IGameView h = new IOHandler();
	private int correctGuesses;
	private Deck deck;
	
	public GameController(){
		correctGuesses = 0;
		init();
	}
	
	@Override
	public void init(){
		String str = "This program lets you play the simple card game,\n" + 
			"HighLow.  A card is dealt from a deck of cards.\n" + 
	    	"You have to predict whether the next card will be\n" + 
	    	"higher or lower.  Your score in the game is the\n" + 
	    	"number of correct predictions you make before \n" +
	    	"you guessed wrong.";
		h.display(str);
	    deck = new Deck();   
	    deck.shuffle(); 
	}
	
	@Override
	public void runGame(){		
		int gamesPlayed = 0;     // Number of games user has played.
	    int sumOfScores = 0;     // The sum of all the scores from all the games played.
	      double averageScore;     // Average score, computed by dividing sumOfScores by gamesPlayed.
	      char playAgain = 'f';       // Record user's response when user is asked whether he wants to play another game.
	      
	      do {
	         playRound();   // Play the game and get the score.
	         sumOfScores += correctGuesses;
	         gamesPlayed++;
	         h.display("Play again (t/f)h" + "?");
	         //sc.nextLine();	         
	         playAgain = h.getInput();
	      } while (playAgain == 't');
	      
	      averageScore = ((double)sumOfScores) / gamesPlayed;
	      h.display("\nYou played " + gamesPlayed + " games.\n"
	      		+ "Your average score was " + averageScore);
	}
	
	/**
	    * Lets the user play one game of HighLow, and returns the
	    * user's score in that game.  The score is the number of
	    * correct guesses that the user makes.
	    */
	 @Override
	 public void playRound() {	   
	      Card currentCard, nextCard;
	      char guess;  	       
	      currentCard = deck.dealCard();
	      h.display("The first card is the " + currentCard);      
	      while (true) {  // Loop ends when user's prediction is wrong.
	         h.display("Will the next card be higher (h) or lower (l) or same (s)?  ");	         
	         guess = h.getInput();
	         nextCard = deck.dealCard();/* Get the next card and show it to the user. */
	         h.display("The next card is " + nextCard);
	         
	         /* Check the user's prediction. */ 
	         boolean winningCond = ((nextCard.getValue() == currentCard.getValue()) && guess == 's') ||
	        		 (nextCard.getValue() > currentCard.getValue() && guess == 'h') ||
	        		 (nextCard.getValue() < currentCard.getValue() && guess == 'l');
	         if ( winningCond ) {
	        	 h.display("Your prediction was correct.");
	             correctGuesses++;
	         }
	         else {
	             h.display("Your prediction was incorrect.");
	             break;  // End the game.
	         }
	         
	         /* To set up for the next iteration of the loop, the nextCard
	            becomes the currentCard, since the currentCard has to be
	            the card that the user sees, and the nextCard will be
	            set to the next card in the deck after the user makes
	            his/her prediction. */
	         
	         currentCard = nextCard;
	         h.display("\nThe card is " + currentCard);         
	      } // end of while loop

	      h.display("\nThe game is over.\n"
	      		+ "You made " + correctGuesses + " correct predictions.\n");
	 }  //end playRound()

}