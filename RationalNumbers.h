#ifndef RATIONALNUMBERS_H
#define RATIONALNUMBERS_H

#include <iostream>
using namespace std;

class RationalNumbers;

ostream &operator <<(ostream &, const RationalNumbers &);
istream &operator >>(istream &, RationalNumbers &);

class RationalNumbers
{
private:
	int numerator;
	int denominator;
	void simplify();
public:
	RationalNumbers();
	void setnum(int);
	void setdeno(int);
	int getnum();
	int getdeno();
	RationalNumbers operator+(RationalNumbers);
	RationalNumbers operator-(RationalNumbers);
	RationalNumbers operator*(RationalNumbers);
	RationalNumbers operator/(RationalNumbers);
	bool operator>(RationalNumbers);
	bool operator<(RationalNumbers);
	bool operator>=(RationalNumbers);
	bool operator<=(RationalNumbers);
	bool operator==(RationalNumbers);
	
	friend ostream &operator <<(ostream &, const RationalNumbers &);
	friend istream &operator >>(istream &, RationalNumbers &);
};
#endif