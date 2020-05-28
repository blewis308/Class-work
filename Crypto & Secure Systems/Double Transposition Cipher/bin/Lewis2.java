import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

////////////////////////////////////////////////////////////////////
// Student name: Brandon Lewis
// Course: COSC 4543 - Cryptography and Secure Systems
// Assignment: #2 - Double Transposistion Cipher
// File name: Lewis2.java
// Program Purpose: Encrypts and decrypts a text file 
// 					using a double transposition
// 					cipher and a fixed transposition key
//
// Program Limitations: CANNOT accept input file
//						redirection due to limitations
//						in the windows commandline
//						program. See note below
// 
// Development Computer: Custom Build PC
// Operating System: Win10 Pro
// Integrated Development Environment (IDE): Visual Studio Code
// Compiler: Javac - Command line
// Build Directions: Please make sure the input files are created.
// Program's Operational Status: Completely Functional
////////////////////////////////////////////////////////////////////

/*
NOTE REGARDING INPUT FILE REDIRECTION

Using < during command line operations yields this error statement.
However, output redirection works perfectly fine. After researching 
	this issue, I've concluded that this is a limitation of the Windows 
	OS and not of this program; output redirection works as expected. 

At line:1 char:16
+ java Lewis2 -e < plaintext.txt
+                ~
The '<' operator is reserved for future use.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : RedirectionNotSupported
*/


public class Lewis2
{
    public static void main(String[] args) throws IOException
    {
		String operator = args[0]; // get the operator whether to encrypt or decrypt
		int whichToDo = 0;	// create and default variable to a wrong reference (not -e or -d)

		Scanner input = new Scanner(System.in);

		String message = "";

		if(operator.compareToIgnoreCase("-e") == 0)
		{
			whichToDo = 1;
			// go mark variable for reference to encrypt
		}
		else if(operator.compareToIgnoreCase("-d") == 0)
		{
			whichToDo = 2;
			// go mark variable for reference to decrypt
		}
		else
		{
			// print the usage statement
			System.out.println("Usage (After Compiling): java Lewis2 {-e | -d} inputfile outputfile");
			whichToDo = 0;
		}
		
		
		if(whichToDo == 0)
		{
			System.exit(1);	// if the arguments are wrong or the key is outside the 0-120 (inclusive) bounds
		}
		else if(whichToDo == 1)
		{
			message = input.nextLine();

			encrypt(message);	// go to encrytion method and pass in the commandline arguments
			
			System.out.println(" Encryption is done");	// print that the encryption is done
			input.close();
		}
		else if(whichToDo == 2)
		{
			message = input.nextLine();
			
			decrypt(message);	// go to the decryption method and pass in the commandline arguments
		
			System.out.println(" Decryption is done");	// print that the decryption is done
			input.close();
		}
		else
		{
			System.exit(1);	// this should never be used, but if the todo is not 0, 1, or 2, and the integer key is within the bounds of 0-120, exit the program.
		}

    }

    public static void encrypt(String message) throws IOException
    {   
	   int[] rmod = {3, 5, 0, 2, 1, 4};		// hard-coded row and column modifiers
	   int[] cmod = {3, 2, 0, 1};

	   char[][] plain = makeCharArray(message);	// takes whole file message to 6x4 char array
	   char[][] temp1 = new char[6][4];	// new temp char array part 1
	   char[][] temp2 = new char[6][4];	// new temp char array part 2: electric boogaloo

	   for(int r = 0; r < 6; r++)	// iterate through the plaintext row
	   {
		   for(int c = 0; c < 4; c++)	// iterate through the plaintext col
		   {
			   temp1[r][c] = plain[rmod[r]][c];	// first transposition. Row-wise
		   }
	   }

	   for(int r = 0; r < 6; r++)	// iterate through the semi encrypted text row
	   {	
		   for(int c = 0; c < 4; c++)	// semi encrypted text col
		   {
			   temp2[r][c] = temp1[r][cmod[c]];	// second transposition. Col-wise
		   }
	   }

	   for(int r = 0; r < 6; r++)				
	   {
		   for(int c = 0; c < 4; c++)				// print the chars to encrypted file
		   {
				System.out.print(temp2[r][c]);
		   }
	   }
    }

    public static void decrypt(String message) throws IOException
    {
	   int[] rmod = {3, 5, 0, 2, 1, 4};	// row and column modifiers
	   int[] cmod = {3, 2, 0, 1};

	   char[][] plain = makeCharArray(message);
	   char[][] temp1 = new char[6][4];
	   char[][] temp2 = new char[6][4];								// ... to here the comments are the same as encrypting
	   																// im too lazy to write them all out
	   for(int r = 0; r < 6; r++)
	   {
		   for(int c = 0; c < 4; c++)
		   {
			   temp1[r][cmod[c]] = plain[r][c];	// this decrypts col-wise
		   }
	   }

	   for(int r = 0; r < 6; r++)
	   {
		   for(int c = 0; c < 4; c++)
		   {
			   temp2[rmod[r]][c] = temp1[r][c];	// decrypt part 2 row-wise
		   }
	   }

	   for(int r = 0; r < 6; r++)
	   {
		   for(int c = 0; c < 4; c++)			// print the decrypted file to the output file
		   {
				System.out.print(temp2[r][c]);	
		   }
	   }
	}
	
	public static char[][] makeCharArray(String msg)	// okay i really like this method i think it works great
	{
		char[][] temp = new char[6][4];	// make the output char array

		int r;				// rows, columns, and end of input string holders
		int c;
		int leftoff = 0;

		for(int i = 0; i < msg.length(); i++)	// takes the input string and iterates through placing characters into the char array
		{
			r = i / 4;	// row numbers, changes every 4 chars [0-5]
			c = i % 4;	// column nums, changes every new char [0-3]

			temp[r][c] = msg.charAt(i);	// put the char into its spot in the char array

			leftoff = i;	// record the last index in the string
		}

		leftoff++;

		for(int j = leftoff; j < 24; j++)	// starting from the index, add filler z's to the end of the char array
		{
			r = j / 4;	// same as before
			c = j % 4;

			temp[r][c] = ' ';	// except place a z instead of the string's char
		}

		return temp;
	}


}