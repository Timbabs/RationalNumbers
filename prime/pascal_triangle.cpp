#include <iostream>
using namespace std;

int factorial(int);
int **arryPtr = nullptr;
int rows;
void space(int);
int main()
{
	char choice;
	do
	{
		do{
			cout<<"Enter the number of rows to generate: ";
			cin>>rows;
			if(rows==0)
			{
				cout<<"Invalid input"<<endl;
				cout<<"Enter a valid number: ";
				cin>>rows;
			}
		}while(rows==0);

		cout<<"\nPascal's Triangle with "<<rows<<" rows"<<endl;
		cout<<endl;
	
		arryPtr = new int*[rows];
		for(int i=0, size=1; size<=rows+1 && i<=rows; ++size, ++i)
		{
		arryPtr[i] = new int[size];
		}

		for(int i = 0; i<=rows; i++)
		{
			for(int col=0, r=0; col<=rows && r<=rows; col++, r++)
			{
				arryPtr[i][col] = factorial(i)/(factorial(i-r)*factorial(r));
				if(arryPtr[i][col]==0)
				{
					break;
				}
				else
				{
					cout<<arryPtr[i][col];
					space(1);
				}
			}
			cout<<endl;
		}


		cout<<"\nThe Binomial Equation based on the triangle with "<<rows<<" rows is: "<<endl;
		cout<<"\n(x+y)^"<<rows<<" = ";
		for(int j=0, i=rows; j<=rows && i>=0; i--)
		{
			cout<<arryPtr[rows][j]<<"x^"<<i<<"y^"<<j;
			j++;
			if(j!=rows+1)
			{
				cout<<" + ";
			}
		}
		cout<<endl;

		cout<<"\nPrinted in Triangular Format"<<endl;
		cout<<endl;

		for(int i = 0, j=rows; i<=rows && j>=0; j--, i++)
		{
			space(j);
			for(int col=0; col<=rows; col++)
			{
				if(arryPtr[i][col]==0)
				{
					break;
				}
				else
				{
					
					cout<<arryPtr[i][col];
					space(1);
				}
			}
			cout<<endl;
		}

		cout<<"\nDo you want to generate Pascal's Triangle for another number of rows? ";
		cin>>choice;
		if(choice=='N'||choice=='n')
		{
			return 0;
		}


	}while(choice=='Y'||choice=='y');

	
	
	delete [] arryPtr;
	return 0;
}

int factorial(int a)
{
	if(a>=1)
	{
		int product = 1;
			for(int n = a; n>=1; n--)
		{
			product *=n;
		}
		return product;
	}

	else if(a==0)
	{
		return 1;
	}
}

void space(int j)
{
	for(int i=0; i<j; i++)
	{
		cout<<" ";
	}
}