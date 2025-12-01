public class Wordle {

    // Reads all words from dictionary filename into a String array.
    public static String[] readDictionary(String filename) {
		// ...
        In in = new In(filename); 
        String[] words = in.readAllStrings();
        return words;
    }

    // Choose a random secret word from the dictionary. 
    // Hint: Pick a random index between 0 and dict.length (not including) using Math.random()
    public static String chooseSecretWord(String[] dict) {
		// ...
        return dict[(int)(Math.random()*(dict.length))];
    }

    // Simple helper: check if letter c appears anywhere in secret (true), otherwise
    // return false.
    public static boolean containsChar(String secret, char c) {
		// ...
        boolean b =false;
        for(int i = 0; i<secret.length();i++){
            if(c==secret.charAt(i)){
                b=true;
            }
        }
        return b;
    }

    // Compute feedback for a single guess into resultRow.
    // G for exact match, Y if letter appears anywhere else, _ otherwise.
    public static void computeFeedback(String secret, String guess, char[] resultRow) {
		// ...
		// you may want to use containsChar in your implementation 
        for(int i=0;i<guess.length();i++){
            if(guess.charAt(i)==secret.charAt(i)){
                resultRow[i]='G';
            }
            else if(containsChar(secret,guess.charAt(i))){
                resultRow[i]='Y';
            }
            else{
                resultRow[i]='_';
            }
        }

    }

    // Store guess string (chars) into the given row of guesses 2D array.
    // For example, of guess is HELLO, and row is 2, then after this function 
    // guesses should look like:
    // guesses[2][0] // 'H'
	// guesses[2][1] // 'E'
	// guesses[2][2] // 'L'
	// guesses[2][3] // 'L'
	// guesses[2][4] // 'O'
    public static void storeGuess(String guess, char[][] guesses, int row) {
		// ...
        for(int i = 0; i < guess.length(); i++){
            guesses[row][i] = guess.charAt(i);
        }

    }

    // Prints the game board up to currentRow (inclusive).
    public static void printBoard(char[][] guesses, char[][] results, int currentRow) {
        System.out.println("Current board:");
        for (int row = 0; row <= currentRow; row++) {
            System.out.print("Guess " + (row + 1) + ": ");
            for (int i = 0; i < guesses[row].length; i++) {
                System.out.print(guesses[row][i]);
            }
            System.out.print("   Result: ");
            for (int j = 0; j < results[row].length; j++) {
                System.out.print(results[row][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns true if all entries in resultRow are 'G'.
    public static boolean isAllGreen(char[] resultRow) {
		// ...
        boolean b = true;
        for(int i=0;i<resultRow.length;i++){
            if(resultRow[i]!='G'){
                b=false;

            }
        }
        return b;
    }

    public static void main(String[] args) {

        int WORD_LENGTH = 5;
        int MAX_ATTEMPTS = 6;
        
        // Read dictionary
        String[] dict = readDictionary("dictionary.txt");

        // Choose secret word
        String secret = chooseSecretWord(dict);

        // Prepare 2D arrays for guesses and results
        char[][] guesses = new char[MAX_ATTEMPTS][WORD_LENGTH];
        char[][] results = new char[MAX_ATTEMPTS][WORD_LENGTH];

        // Prepare to read from the standart input 
        In inp = new In();

        int attempt = 0;
        boolean won = false;

        while (attempt < MAX_ATTEMPTS && !won) {

            String guess = "";
            boolean valid = false;

            // Loop until you read a valid guess
            while (!valid) {
                System.out.print("Enter your guess (5-letter word): ");
                guess = inp.readLine(); 
                if (guess == null || guess.length() != WORD_LENGTH) {
                    System.out.println("Invalid word. Please try again.");
                } else {
                    String tmp="";
                    for(int i=0;i<guess.length();i++){
                        if(guess.charAt(i)<='z'&&guess.charAt(i)>='a'){
                            tmp+=(char)(guess.charAt(i)-32);
                        }else if(guess.charAt(i)<='Z'&&guess.charAt(i)>='A'){
                            tmp+=guess.charAt(i);
                        }
                    }
                    guess=tmp;
                    valid = true;
                }
                
            }

            // Store guess and compute feedback
            // ... use storeGuess and computeFeedback
            storeGuess(guess, guesses, attempt);
            computeFeedback(secret, guess, results[attempt]);
            // Print board
            printBoard(guesses, results, attempt);

            // Check win
            if (isAllGreen(results[attempt])) {
                System.out.println("Congratulations! You guessed the word in " + (attempt + 1) + " attempts.");
                won = true;
            }

            attempt++;
        }

        if (!won) {
            // ... follow the assignment examples for how the printing should look like
            System.out.println("Sorry, you did not guess the word.\nThe secret word was: "+ secret);
            
        }

        inp.close();
    }
}
