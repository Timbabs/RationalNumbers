#include <iostream>
#include "RationalNumbers.h"
using namespace std;
int main()
{
	RationalNumbers A;
	RationalNumbers B;
	RationalNumbers C;
	
	cout<<"Enter your first fraction: ";
	cin>>A;

	cout<<"Enter your second fraction: ";
	cin>>B;

	char choice;
	do{
		cout<<"What do you want to do?"<<endl;
		cout<<"\t"<<"1 "<<"Add the fractions?"<<endl;
		cout<<"\t"<<"2 "<<"subtact?"<<endl;
		cout<<"\t"<<"3 "<<"compare?"<<endl;

		int answer;
		cout<<"select an option from 1 to 3: ";
		cin>>answer;
		while(answer<1||answer>3)
		{
			cout<<"Please select a valid option: ";
			cin>>answer;
		}

		switch (answer)
		{
		case 1: cout<<"The sum is: "<<A+B<<endl;
			break;
		case 2: cout<<"The difference is: ";
			cout<<A-B<<endl;
			break;
		case 3: if(A>B)
				{
					cout<<"Fraction1 is greater than fraction2"<<endl;
				}
				else if(A<B)
				{
					cout<<"Fraction1 is less than fraction2"<<endl;
				}
				else
				{
					cout<<"The two fractions are equal"<<endl;
				}
		}

		cout<<"Do you want to do something else(y/n)? ";
		cin>>choice;
		if(choice=='N'||choice=='n')
		{
			return 0;
		}

	}while(choice=='Y'||choice=='y'); 

	return 0;
}