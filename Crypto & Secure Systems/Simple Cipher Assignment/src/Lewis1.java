import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

///////////////////////////////////////////////////////////////////
// Student name: Brandon Lewis
// Course: COSC 4543 - Cryptography and Secure Systems
// Assignment: #1 - Simple Arithmetic Cipher
// File name: Lewis1.java
// Program Purpose: Encrypts and decrypts a text file 
// 					using a simple arithmetic
// 					cipher and a nonnegative integer key
//
// Program Limitations: None known
// 
// Development Computer: Custom Build PC
// Operating System: Win10 Pro
// Integrated Development Environment (IDE): Visual Studio Code
// Compiler: Javac - Command line
// Build Directions: Please make sure the input files are created.
// Program's Operational Status: Completely Functional
/////////////////////////////////////////////////////////////////// 


public class Lewis1 
{
	public static void main(String[] args) throws IOException 
	{
		// If no args then output usage statement	
			// Usage: java Lewis1 {-e | -d} integer-key
		
		// If integer key is outside of the given range, output usage statement
			// Usage: Integer key must range between 0 and 120 inclusive
		
		String operator = args[0]; // get the operator whether to encrypt or decrypt
		int whichToDo = 0;	// create and default variable to a wrong reference (not -e or -d)

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
			System.out.println("Usage (After Compiling): java Lewis1 {-e | -d} integer-key inputfile outputfile");
			whichToDo = 0;
		}
		
		int integerKey = Integer.parseInt(args[1]);	// get the integer key from command line arguments
		
		//System.out.println(integerKey); // Debug to print the integer key
		
		if(whichToDo == 0 || integerKey < 0 || integerKey > 120)
		{
			System.exit(1);	// if the arguments are wrong or the key is outside the 0-120 (inclusive) bounds
		}
		else if(whichToDo == 1)
		{
			encrypt(args);	// go to encrytion method and pass in the commandline arguments
			
			System.out.println("Encryption is done");	// print that the encryption is done
		}
		else if(whichToDo == 2)
		{
			decrypt(args);	// go to the decryption method and pass in the commandline arguments
		
			System.out.println("Decryption is done");	// print that the decryption is done
		}
		else
		{
			System.exit(1);	// this should never be used, but if the todo is not 0, 1, or 2, and the integer key is within the bounds of 0-120, exit the program.
		}	
	}
																		// ###################################################### ENCRYPTION METHOD
	public static void encrypt(String[] args) throws IOException
	{
		System.out.println("in encryption function"); // debugging statement

		String plaintextfilename = args[2];		// these get the input and output filenames from the commandline arguments
		String encryptedfilename = args[3];
		
		System.out.println("done creating filenames");	// debugging statement
		
		File inputfile = new File(plaintextfilename);	// takes the input and output filenames and creates file objects from them
		File outputfile = new File(encryptedfilename);
		
		System.out.println("done creating file objects");	// debugging statement
		
		FileReader filereader = new FileReader(inputfile);		// creates a FileReader object with the input file to read character by character from the input file
		PrintWriter filewriter = new PrintWriter(outputfile);	// creates a PrintWriter object with the output file to enable writing to the output file
		
		System.out.println("done creating file reading and writing objects");	// debugging statement
		
		char readchar;	// creates the character to be read from the file
		int integerKey = Integer.parseInt(args[1]);	// gets the integer key again from the commandline arguments to shift the char value by
		
		System.out.println("reading file now");	// debugging statement
		
		readchar = (char)filereader.read();	// reads the first char from the file, gets -1 if the file is empty

		while(readchar != (char)-1)	// while the char being read isn't the end of the file, do the stuff in the loop 
		{
			//System.out.print("reading char " + readchar + " int value of " + (int)readchar); // debugging statement
	
			readchar += integerKey;	// shift the char that's read by the integet key
			readchar %= 128;		// mod the char value by 128 to ensure its value stays within the ascii range

			//System.out.println(" sending value of " + (int)readchar);	// debugging statement
			
			filewriter.print(readchar);	// prints the encrypted char to the output file

			readchar = (char)filereader.read();	// gets the next char in the file
		}
		filereader.close();	// closes the filereader and writer objects to ensure the files are populated
		filewriter.close();
	}
																		// ###################################################### DECRYPTION METHOD
	public static void decrypt(String[] args) throws IOException	
	{
		System.out.println("in decryption function");	// debugging statement
		
		String encryptedfilename = args[2];		// these get the input and output filenames from the commandline arguments
		String plaintextfilename = args[3];
		
		System.out.println("done creating filenames");	// debugging statement
		
		File inputfile = new File(encryptedfilename);	// takes the input and output file names and creates objects from them
		File outputfile = new File(plaintextfilename);	
		
		System.out.println("done creating file objects");	// debugging statement
		
		FileReader filereader = new FileReader(inputfile);		// crates the FileReader object with the input file to read character by character form the input file
		PrintWriter filewriter = new PrintWriter(outputfile);	// creates a PrintWriter object with the output file to enable writing to the output file
		
		System.out.println("done creating file reading and writing objects");	// debugging statement

		char readchar;	// creates the character to be read from the file
		int integerKey = Integer.parseInt(args[1]);	// gets the integer key again from the commandline arguments to shift the char value by
		
		System.out.println("reading the file now");	// debugging statement

		readchar = (char)filereader.read();	// reads the first char from the file, gets -1 if the file is empty
		
		while(readchar != (char)-1)		// while the char is being read isn't the end of the file, deo the stuff in the loop
		{
			//System.out.print("reading char " + readchar + " int value of " + (int)readchar); // debugging statement
			
			readchar -= integerKey;	// shift the char that's read by the integer key opposite to encryption to decrypt
			readchar %= 128;		// mod the char vlue by 128 to ensure its value stays within the ascii range

			//System.out.println(" sending value of " + readchar);	// degubbing statement

			filewriter.print(readchar);	// prints the decrypted char to the output file

			readchar = (char)filereader.read();	// gets the next char in the file
		}

		filereader.close();	// closes the filereader and writer to ensure the files are populated
		filewriter.close();
	}

}
		// end of file