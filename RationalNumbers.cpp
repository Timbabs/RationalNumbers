#include <iostream>
#include <cmath>
#include "RationalNumbers.h"
using namespace std;

RationalNumbers::RationalNumbers()
{
	numerator = 1;
	denominator = 1;
}
	

void RationalNumbers::simplify()
{
	int i = numerator>denominator? numerator: denominator;
	while(i>1)
	{
		if (numerator%i==0 && denominator%i==0)
		{
			numerator /=i;
			denominator /=i;
		}
		--i;
	}
}

RationalNumbers RationalNumbers::operator+(RationalNumbers a)
{
	RationalNumbers result;
	result.numerator = (a.numerator*denominator + a.denominator*numerator);
	result.denominator = a.denominator*denominator;
	result.simplify();

	return result;
}

RationalNumbers RationalNumbers::operator-(RationalNumbers a)
{
	RationalNumbers result;
	result.numerator = abs(a.denominator*numerator-a.numerator*denominator);
	result.denominator = a.denominator*denominator;
	result.simplify();

	return result;
}

RationalNumbers RationalNumbers::operator*(RationalNumbers a)
{
	RationalNumbers result;
	result.numerator = (a.numerator*numerator);
	result.denominator = (a.denominator*a.denominator);
	result.simplify();

	return result;
}

RationalNumbers RationalNumbers::operator/(RationalNumbers a)
{
	RationalNumbers result;
	result.numerator = (a.numerator*denominator);
	result.denominator = (a.denominator*numerator);
	result.simplify();

	return result;
}

bool RationalNumbers::operator<(RationalNumbers a)
{
	bool status;
	if(double(numerator/denominator)<double(a.numerator/a.denominator))
	{
		status =true;
	}
	else
	{
		status = false;
	}
	return status;
}

bool RationalNumbers::operator>(RationalNumbers a)
{
	bool status;
	if(double(numerator/denominator)>double(a.numerator/a.denominator))
	{
		status =true;
	}
	else
	{
		status = false;
	}
	return status;
}

bool RationalNumbers::operator<=(RationalNumbers a)
{
	bool status;
	if(double(numerator/denominator)<=double(a.numerator/a.denominator))
	{
		status =true;
	}
	else
	{
		status = false;
	}
	return status;
}

bool RationalNumbers::operator>=(RationalNumbers a)
{
	bool status;
	if(double(numerator/denominator)>=double(a.numerator/a.denominator))
	{
		status =true;
	}
	else
	{
		status = false;
	}
	return status;
}

bool RationalNumbers::operator==(RationalNumbers a)
{
	bool status;
	if(double(numerator/denominator)==double(a.numerator/a.denominator))
	{
		status = true;
	}
	else
	{
		status = false;
	}
	return status;
}

ostream &operator <<(ostream &strm, const RationalNumbers &obj)
{
	if(obj.numerator==0 || obj.denominator==1)
	{
		strm<<obj.numerator;
		return strm;
	}
	else
	{
		strm<<obj.numerator<<"/"<<obj.denominator;
		return strm;
	}
}

istream &operator >>(istream &strm, RationalNumbers &obj)
{
	/*char slash;
	strm >>obj.numerator>>slash>>obj.denominator;*/
	strm >>obj.numerator;
	strm.ignore(1);
	strm>>obj.denominator;

	if(obj.denominator==0)
	{
		cout<<"Invalid denominator"<<endl;
		obj.denominator=1;
	}
	obj.simplify();
	return strm;
}


