///////////////////////////////////////////////////////////////////
// Student name:   
// Course: COSC 4553 - Information Security
// Assignment: Assignment #5 - Repeated Squaring
// File name:  Lewis6.cpp
// Purpose:  Implements the powerModN utility function that
//           incorporates the repeated squaring algorithm 
// Limitations: Must have 64 bit compiler
// Development Computer:  Custom built PC
// Operating System:  Windows 10 Pro
// Integrated Development Environment (IDE):  Visual Studio Code
// Compiler:  g++
// Build Directions: See the driver module
// Operational Status: Fully Functional
///////////////////////////////////////////////////////////////////

using namespace std;

#include "powerUtility.h"
#include <iostream>
#include <iomanip>
#include <typeinfo>
#include <cmath>

#define MAX_BITS 1000

// Function Prototypes
void binary(long decimalValue, int bits[], int &bitCount);


// ##########################################################
// Implements the repeated squaring algorithm
long powerModN(long base, long exponent, long modulus)
{
    int bsize = (int) (log (exponent)/log (2)) + 1;
    
    int bits[MAX_BITS];
    long long modresult[bsize];
    int bitCount;
    long long result = 1;
    long long temp;

    modresult[0] = base % modulus;
    cout << modresult[0] << endl;

    binary(exponent, bits, bitCount);
    
    int n = 2;
    int count = 1;
    do
    {
        modresult[count] = (long long) (modresult[count - 1] * modresult[count - 1]) % modulus;
        //cout << modresult[count] << endl;
        count++;
        n = n * 2;
    } while(n < exponent);

    //cout << endl << "For loop" << endl;
    for(int i = MAX_BITS - 1; i > MAX_BITS - bsize - 1; i--)
    {
        //cout << i << endl;
        if(bits[i] == 1)
        {
            //cout << result << " " << modresult[MAX_BITS - i - 1] << endl;
            temp = modresult[MAX_BITS - i - 1];
            result *= temp;
            //cout << result << endl;
        }
    }

    result = result % modulus;
    
    return result;
} // End powerModN


// ##########################################################
// Converts the exponent into its binary number representation
void binary(long decimalValue, int bits[], int &bitCount)
{
    int i = 1; 
    int n = decimalValue;

    while(n > 0)
    {
        bits[MAX_BITS - i] = n % 2;
        n = n / 2;
        i++;
    }


} // End binary

