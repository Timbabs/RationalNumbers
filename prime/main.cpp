#include <iostream> //concept 1 - output to terminal
#include <iomanip> //concept 2 - formatting output
#include <fstream> //concept 3 - output to text file

#define LIST_SIZE  100000 //concept 4 - preprocessor directive and // c-style constants

using namespace std;

int currPositionInList = 0;
bool eratosthenesList[LIST_SIZE];

void initializeList();
void crossOffList(int);
int findNextPrime();

int main()
{
    initializeList();
    for (int i = 2; i < LIST_SIZE; i++) {
        crossOffList(i);
    }
    eratosthenesList[LIST_SIZE - 1] = false;

    int count = 0;
    ofstream myFile;
    myFile.open ("../listOfPrimes.txt");
    while (currPositionInList < LIST_SIZE) {
        int result = findNextPrime();
        if (result != 0) {
            if (count % 5 != 0) {
                myFile <<setw(10)<<result;
            } else {
                myFile << "\n"<<setw(10)<<result;
            }

            count++;
        }
    }
    myFile.close();

    cout<<"\nThere are "<<count<< " primes that have a value less than "<<LIST_SIZE<<endl;
    cout<<"The primes are listed in the text file listOfPrimes.txt"<<endl;

    return 0;

}

void initializeList() {
    for (int i = 0; i < LIST_SIZE; i++) {
        eratosthenesList[i] = true;
    }

}

void crossOffList(int primeValue) {
    if (eratosthenesList[primeValue - 2]) {
        for (int i = primeValue - 1; i < LIST_SIZE; i++) {
            int val = i + 2;
            if (val % primeValue == 0) {
                eratosthenesList[i] = false;
            }
        }
    }

}

int findNextPrime() {
    while (currPositionInList < LIST_SIZE) {
        if (eratosthenesList[currPositionInList]) {
            return currPositionInList++ + 2;
        }
        currPositionInList++;
    }
    return 0;
}