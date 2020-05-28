import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;

////////////////////////////////////////////////////////////////////
// Student name: Brandon Lewis
// Course: COSC 4543 - Cryptography and Secure Systems
// Assignment: #3 - RC4 Cipher
// File name: Lewis3.java
//
// Rev. 2
//
// Program Purpose: Encrypts and decrypts a text file 
// 					using an alphanumeric key and the
//                  RC4 algorithm to encypt and decrypt
//                  a given file
//
// Program Limitations: 256 character key length limit,
//                      no other known limitations
// 
// Development Computer: Custom Build PC
// Operating System: Win10 Pro
// Integrated Development Environment (IDE): Visual Studio Code
// Compiler: Javac - Command line
// Build Directions: Please make sure the input files are created.
// Program's Operational Status: Completely Functional
////////////////////////////////////////////////////////////////////


public class Lewis3
{
    static byte[] S;    // creates byte arrays of the iterators that go through the 
    static byte[] T;

    public static void main(String[] args) throws Exception
    {
        String sKey;        // string version of the key
        String outString;   // string version of the output byte array

        byte[] input;   // input file in byte array form
        byte[] output;  // output in byte array form
        byte[] key;     // key in byte array

        File outputfile;    // outputfile object
        PrintWriter writer; // printwriter object

        if(args.length == 3)    // if all three arguments are present
        {
            if(args[0].length() > 256)  // if the key length is greater than 256 characters
            {
                System.out.println("Key length over 256 characters, ignoring excess characters");   // tell the user
                sKey = args[0].substring(0, 256);   // sKey is the 256 char long concatonated key
            }
            else    // if its less than 256 chars...
            {
                sKey = args[0];     // ...just set the key
            }

            key = sKey.getBytes();  // translate string key to byte array key
            String inputfilename = args[1]; // get inputfile name
            String outputfilename = args[2];    // get outputfile name
            
            outputfile = new File(outputfilename);  // make output File object, create and open the output file
            writer = new PrintWriter(outputfile);   // make printwriter onject
        
            input = Files.readAllBytes(Paths.get(inputfilename));   // takes input file and reads the contents to a byte array
        }
        else
        {
            throw new Exception("Example program usage (after compiling): java Lewis3 <alphanumeric-key> <input file> <output file>");  // if the arguments aren't correct
        }


        cipher(key);    // call the cipher function to fill and modify the S[] and T[] arrays 
        output = encrypt(input);    // sets the output byte array as the cipher text or new plaintext, whichever it needs

        outString = new String(output); // translates the byte array to a string

        writer.write(outString);    // writes the string to the output file

        System.out.println("RC4 stream cipher operation is done");

        writer.close(); // close the printwriter
    }

    public static void cipher(byte[] key)   // sets the two S and T arrays 
    {
        int keylen = key.length;    // gets key length
        S = new byte[256];  // finish creating S array
        T = new byte[256];  // finish creating T array

        for(int i = 0; i < 256; i++)
        {
            S[i] = (byte) i;        // set 0-255 in S
            T[i] = key[i % keylen]; // copy the key values into T and repeat if necessary
        }

        int j = 0;

        for(int i = 0; i < 256; i++)
        {
            j = (j + S[i] + T[i]) & 0xFF;   // the initial permutation of S 

            byte temp = S[i];  // swap bytes
            S[i] = S[j];
            S[j] = temp;
        }
    }

    public static byte[] encrypt(byte[] plaintext)  // this actually encrypts the plaintext
    {
        byte[] ciphertext = new byte[plaintext.length]; // create new ciphertext byte array

        int i = 0, j = 0, k, t;

        for(int counter = 0; counter < plaintext.length; counter++) // does the steps to encrypt/decrypt the text
        {
            i = (i + 1) & 0xFF;     // instead of mod, just AND via 0xFF which is 256 in hex
            j = (j + S[i]) & 0xFF;

            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;

            t = (S[i] + S[j]) & 0xFF;   // same here
            k = S[t];
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);  // this is the actual XORing
        }
        return ciphertext;  // returns the ciphertext
    }
}