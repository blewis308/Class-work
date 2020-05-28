///////////////////////////////////////////////////////////////////
// Student name: Brandon Lewis
// Course: COSC 4553 - Information Security
// Assignment: Assignment #5 - RSA Key Generation
// File name: Lewis5.cpp
// Purpose: Defines the functions that generate the RSA public and
//          private keys
// Limitations:  
// Development Computer: Custom Build PC
// Operating System: Windows 10 Pro
// Integrated Development Environment (IDE): Visual Studio Code
// Compiler: MinGW G++
// Build Directions: See the driver module
// Operational Status: 
///////////////////////////////////////////////////////////////////

#include <iostream>
#include "RSA_Keys.h"

using namespace std;

static int relativelyPrime(int num);
static int gcd(int numV, int numW);
static int inverse(int numX, int modN);

// ##################################################################
void generateKeys(int primeA, int primeB, int &encryptionExponent, 
                  int &decryptionExponent)
{
    int p = primeA;
    int q = primeB;

    int n = p * q;
    int phi = (p - 1) * (q - 1);
    
    encryptionExponent = relativelyPrime(phi);

    decryptionExponent = (1 + phi) / encryptionExponent;
} // End generateKeys

static int relativelyPrime(int num)
{   // Purpose: Compute a value Y that is relatively prime to X
    int e = 1;
    int temp;

    do
    {
        e++;
        temp = gcd(num, e);
    }
    while(temp != 1);
    
    return e;
}   // End relativelyPrime

static int gcd(int numV, int numW)
{   // Computes the greatest common divisor of V and W

    int a = numV;
    int b = numW;

    int temp;
    while(b != 0)
    {
        if (a < b)
        {
            temp = a;
            a = b;
            b = temp;
        }
        temp = b;
        b = a % b;
        a = temp;
    }

    return a;
}   // End gcd