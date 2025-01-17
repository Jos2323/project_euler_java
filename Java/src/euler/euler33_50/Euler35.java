//Euler Problem 35 (http://projecteuler.net/problem=35)

/* Problem:	
	Circular primes

The number, 197, is called a circular prime because all rotations of the digits: 197, 971, and 719, are themselves prime.
There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.
How many circular primes are there below one million?



*/

/* L�sung (Nr. 55995, 2016/02/03):

	55

*/


// Erkl�rung siehe unterhalb des Programms

// 

package euler.euler33_50;

import utils.ArrayNumber;
import utils.Prime;

import java.util.Arrays;

public class Euler35 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 4;
		
		ArrayNumber num = new ArrayNumber(11,6);
		
		jump:
		while (num.getNumberAsInt() < 1_000_000)
		{	
//			System.out.println("start: " + num.getNumberAsInt() + " " + num.getLead());
			for (int i = 0; i <= num.getLead(); i++)
			{
//				System.out.println("values: " + num.get(i) + " " + (num.get(i) % 2) );
				if ( (num.get(i) % 2 == 0) || (num.get(i) == 5) )
				{
//					num.add(2);
					num.increase();
					num.increase();
//					System.out.println("jump");
					continue jump;
				}
			}
//			System.out.println(num.getNumberAsInt());
			if (num.digitSumFinal() != 3 )
			{
//				System.out.println("rein fuer" + num.getNumberAsInt());
				int[] sol;
				if ((sol = checkCircle(num))[0] > 0)
				{
					solution += sol[0];
					for (int i = 1; i < sol.length; i++)
					{
						if (sol[i] > -1)
							System.out.println("found: " + sol[i]);
					}
					System.out.println("Found until now: " + solution);
				}		
			}
			
//			num.add(2);
			num.increase();
			num.increase();		
		
		}
		
		
		System.out.println("Solution: " + solution);
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");

		
	}
	
//	static boolean primTest (ArrayNumber x)
//	{
//		int p = x.getNumberAsInt();
	static boolean primTest (int p)
	{	
		for (int i = 7; i < p/2; i +=2 )
		{
			if ( (p % i) == 0 )
				return false;
		}
		return true;
	}
	
	static int getNum(int[] x)
	{
		int res = 0;
		for (int i = 0; i < 6; i++)
			res += x[i]*Math.pow(10,i);
		return res;
	}
	
	// give back 0, if the Number has been already checked (-> if a smaller circle permutation exists)
	// if not give number of circle permutations
	static int[] checkCircle(ArrayNumber y)
	{
		int lead = y.getLead();

		int[] res = new int[lead+2];
		
//		ArrayNumber x = new ArrayNumber(y);
		int[] x = y.getNumberAsArray();
		

		// give back false, if there is a digit smaller than largest digit
//		int c = x.get(lead);
		int c = x[lead];
		for (int i = 0; i < lead; i++)
		{
//			if (x.get(i) < c)
			if (x[i] < c)
			{
				res[0] = 0;
				return res;
			}
		}

		int[] circle = createCircle(x,lead);
		
		
		for (int i = 0; i <= lead; i++)
		{
//			System.out.println(circle[i] + " " + Prime.test(circle[i]));
			if ((res[i+1] = circle[i]) == -1)
			{
				res[0] = i;
				return res;
			}
//			if (!primTest(circle[i]))
			if (!Prime.isPrime(circle[i]))
			{
				res[0] = 0;
				return res;
			}
		}
//		res = x.getLead()+1;
		res[0] = lead+1;
		return res;
		
//		int checkVal = x.getNumberAsInt();
//		
//		for (int i = 1; i <= x.getLead(); i++)
//		{
//			// circle permutation
//			for (int j = x.getLead(); j > 0; j--)
//			{
//				x.set(j,x.get(j-1));
//			}
//			x.set(0,c);
//			c = x.get(x.getLead());
//			
//			// if smaller value than permutation exists, break
//			if (x.getNumberAsInt() < checkVal)
//				return 0;
//
//			// count numbers of permutations -> if there is an equal permutation, break
//			if (checkVal == x.getNumberAsInt())
//			{
//				return i;
//			}
//
//			
//		}
//		
//		return x.getLead()+1;

	}
	
	// input: ArrayNumber. ouput: all permutations. 
	// if permutation circle exists -> first output element -1
	// if repetition occurs: set -1 at this element
//	static int[] createCircle(ArrayNumber y)
	static int[] createCircle(int[] y,int lead)
	{		
		int[] res = new int[lead+1];
//		ArrayNumber x = new ArrayNumber(y);
		int[] x = Arrays.copyOf(y,y.length);
		
//		res[0] = x.getNumberAsInt();
		res[0] = getNum(x);
		
//		for (int i = 1; i <= x.getLead(); i++)
		for (int i = 1; i <= lead; i++)
		{
//			int c = x.get(x.getLead());
			int c = x[lead];
			// circle permutation
//			for (int j = x.getLead(); j > 0; j--)
			for (int j = lead; j > 0; j--)
			{
//				x.set(j,x.get(j-1));
				x[j] = x[j-1];
			}
//			x.set(0,c);
			x[0] = c;
			
//			res[i] = x.getNumberAsInt();
			res[i] = getNum(x);
			
			// if smaller value than permutation exists, break
			if (res[i] < res[0])
			{
				res[0] = -1;
				return res;
			}

			// count numbers of permutations -> if there is an equal permutation, break
			if (res[0] == res[i])
			{
				res[i] = -1;
				return res;
			}

		}
		return res;
		
	}

}






/* Erkl�rung:
	1. Grund�berlegungen:
	   Es geht um Zahlen, bei denen alle Permutationen im Kreis (dh. Reihenfolge bleibt gleich) Primzahlen sind.
	   Damit k�nnen die Ziffern 0,2,4,5,6,8 nicht vorkommen, da diese dann einmal hinten stehen und damit nicht prim sein k�nnen.
	   -> Die Zahlen bauen sich aus den Ziffern 1,3,7,9 auf
	2. Brute Force Prinzip:
	   a) Z�hle alle Zahlen hoch, die sich aus obigen Ziffern aufbauen lassen. 
	   b) �berspringe, wenn die Zahl bereits als Permutation im Kreis gepr�ft wurden
	   c) Pr�fe, ob Primzahl
	3. Bzgl. Permutation im Kreis:
	   Wenn man die Zahlen von unten aus pr�ft, m�sste es klappen. daf�r gilt
	    a) Jede Kreispermutation hat ein kleinstes Elemente
	    b) Wenn man von unten nach oben durchl�uft, so gilt: gibt es eine Kreispermutation, die kleiner ist, so wurde dies schon getestet.
	    c) Man muss also die Permutationen durchlaufen und pr�fen, ob es eine kleinere gibt.
	    d) Verk�rzung: wenn es eine Ziffer gibt, die kleiner ist als die erste, so wurde schon getestet
	4. Bzgl. Primzahlpr�fung:
	   Das wurde bereits in einem fr�heren Euler-Problem gefordert...

*/
