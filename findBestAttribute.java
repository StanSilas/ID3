import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.math.*;
import java.lang.Double;
import java.util.LinkedList;


public class findBestAttribute {
	
	public static String test_input [][]= new String[1000][100];
	public static int i = 0, j=0,t=0, num_of_columns=-1, num_of_rows=-1,total_count=0;
	public static String str;
	public static double num_zeros_in_class=0.0 ;
	public static double num_ones_in_class=0.0;
	public static int class_col_num, count=0;
	public static double h;
	public static double get_H_attributes;
	
	public static double ig_sorted[]= new double[100];
	public static ArrayList<String> split_seq = new ArrayList<String>();
	public static double largest, temp; static int lock=0;

	public static void main(String args[]) throws FileNotFoundException
	{
		String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
        System.out.println(currentDir +"//data//example.dat");
        String path=currentDir +"//data//example.dat";
		getValues(path);
		get_H_Class("class");
		//get_H_attributes();
		//get_split_seq();
		
		}
//**RAVEENA SINGH**function to retrieve values from text to array****************
		public static void getValues(String path) throws FileNotFoundException
		{
			
			Scanner scanner = new Scanner(new File(path));
	        
	        
	        while (scanner.hasNext()){
	        	str=scanner.next();
	        	//System.out.println("str "+str);
	        	if(j==0){
	        		if(str.equals("class"))
	        		{
	        		test_input[j][i++] = str;
	        	//	System.out.println("last col "+i);
	        		num_of_columns=i;
	        		class_col_num=i-1;
	        		j++;
	        		i=0;
	        		}
	        		else
	        		{
	        			test_input[j][i++] = str;
	        			//System.out.println("first row input "+ test_input[j][i-1]);
	        		}
	        	}
	        	else if(j>0)
	        	{
	        		if(i<=num_of_columns)
	        		{
	        		//	System.out.println("i"+i+" j "+j);
	        		test_input[j][i++] = str;
	        		
	        		}
	        		if(i==num_of_columns)
	        		{
	            		j++;
	            	    i=0;
	        		}
	        	}
	        	
	        		//System.out.println(scanner.next());
	        	} 
	            scanner.close();
	            System.out.println("num-of_cols "+num_of_columns + "number_of_rows "+j);
	            num_of_rows=j;
	            for(int m=0;m<num_of_rows;m++)
	            {
	            	for(int n=0;n<num_of_columns;n++)
	            		System.out.print(test_input[m][n]+" ");
	            
	            System.out.println();
	            }
	        
		}
		
		//**RAVEENA SINGH**get entropy value for class column
		public static void get_H_Class(String label)
		{
			System.out.println("class now "+label);
			int h_col_num=-1; num_zeros_in_class=0.0; num_ones_in_class=0.0;
			for(int m=0;m<num_of_columns;m++)
			{
			if(test_input[0][m].equals(label))
				h_col_num=m;
			}
			System.out.println("h_col_num "+h_col_num);
			for(int m=1;m<num_of_rows;m++)
			{
				total_count=num_of_rows-1;
				if(test_input[m][h_col_num].equals("0"))
				num_zeros_in_class++;
				else if(test_input[m][h_col_num].equals("1"))
					num_ones_in_class++;
			}
			System.out.println("num_zeros_in_class "+label +" "+ num_zeros_in_class);
			System.out.println("num_ones_in_class "+num_ones_in_class);
			System.out.println("total_count "+total_count);
			
			double p_zeroes=num_zeros_in_class/total_count;
			double p_ones=num_ones_in_class/total_count;
			System.out.println("p_zeroes "+p_zeroes);
			System.out.println("p_ones "+p_ones);
			
	 h= -((p_zeroes*log(p_zeroes,2)) +(p_ones*log(p_ones,2)));	
		System.out.println("Entropy of class is "+h);		
		//	split_seq[count++]=test_input[0][h_col_num];
			
	String next_split=	get_H_attributes(h, h_col_num);
	System.out.println("next split attribute****************** "+ next_split);
	if(next_split.equals(""))
		System.out.println("DONE N DUSTED PART1");
		
	else
		get_H_Class(next_split);
			}
		
		static double log(double x, int base)
		{
		//	System.out.println((double) (Math.log(x) / Math.log(base)));
		    return (double) (Math.log(x) / Math.log(base));
		}
		
		static String get_H_attributes(double h_split_ele, int split_ele_col_num)
		{
			DecimalFormat df = new DecimalFormat("#.####");
			//System.out.println("function is here!" +test_input[0][split_ele_col_num]);
			//double num_of_zeroes_c1=0.0,num_of_ones_c1=0.0 , matching_zero_nodes=0.0, unmatching_zero_nodes=0.0 , unmatching_ones_nodes=0.0, matching_ones_nodes=0.0;
			int c=1; 
			//System.out.println("class_col_num "+class_col_num);
			//System.out.println("split_seq.length is "+split_seq.size());
			
			/*if(split_seq.size()==(num_of_columns-1))
				return "0";
			*/
			String split_att="";
			  double ig_attributes[]= new double[100];
			 if (split_seq.size()<(num_of_columns-1))
			{
			while(c<=class_col_num)
			{
				//c++;
				//double num_of_zeroes_c1=0.0,num_of_ones_c1=0.0 , matching_zero_nodes=0.0, unmatching_zero_nodes=0.0 , unmatching_ones_nodes=0.0, matching_ones_nodes=0.0;
				double	num_of_zeroes_c1=0.0,num_of_ones_c1=0.0 , matching_zero_nodes=0.0, unmatching_zero_nodes=0.0 , unmatching_ones_nodes=0.0, matching_ones_nodes=0.0;
			//	System.out.println("num_of_columns "+num_of_columns);
				boolean already_used=false;
				
			//**RAVEENA SINGH**for one attribute
			for( i=1;i<num_of_rows;i++)
			{
				
				//**RAVEENA SINGH**check if attribute already used for split**************
				
				if(i==1)
				{
				for(int k=0;k<split_seq.size();k++)
				{
				//	System.out.println("test_input[0][c] "+test_input[0][c-1]);
				//	System.out.println("split_seq.get(k) "+split_seq.get(k));
					
				if(test_input[0][c-1].equals(split_seq.get(k)))
				{
					already_used=true;
					break;
				}
				}
				
			}
			//	System.out.println("already_used "+already_used);
				
				//**RAVEENA SINGH**check if attribute already used for split**************
				if(already_used==false)
				{
				//	System.out.println("already_used ******!!!!!!"+already_used+ " attribute "+test_input[0][c-1]);
				//System.out.println("check1******** "+"i"+ i+"c "+c +" test_input "+test_input[0][c]);
				if(test_input[i][c-1].equals("0"))
				num_of_zeroes_c1++;
				else if(test_input[i][c-1].equals("1"))
					num_of_ones_c1++;
				
				if(test_input[i][c-1].equals("0"))
				{
					if(test_input[i][split_ele_col_num].equals("0"))
						matching_zero_nodes++;
					else if(test_input[i][split_ele_col_num].equals("1"))
						unmatching_zero_nodes++;
					
				}
				if(test_input[i][c-1].equals("1"))
				{
					//System.out.println("test_input[i][class_col_num] "+test_input[i][class_col_num]);
					if(test_input[i][split_ele_col_num].equals("1"))
						matching_ones_nodes++;
					else if(test_input[i][split_ele_col_num].equals("0"))
						unmatching_ones_nodes++;
					
				}
				
				}	
				else if(already_used==true)
				{
					System.out.println("no use of iterating over this column "+i);
					break;
				}
			}
			if(already_used==false)
			{
			System.out.println("matching_zero_nodes "+matching_zero_nodes);
			System.out.println("unmatching_zero_nodes "+unmatching_zero_nodes);
			System.out.println("matching_ones_nodes "+matching_ones_nodes);
			System.out.println("unmatching_ones_nodes "+unmatching_ones_nodes);
		
			System.out.println("splitting column number "+split_ele_col_num);
			System.out.println("For "+test_input[0][split_ele_col_num]+" and "+test_input[0][c-1]);
			double p_zeroes= getNormalProb(num_of_zeroes_c1,(num_of_zeroes_c1+num_of_ones_c1));
			System.out.println("p_zeroes "+p_zeroes);
			double p_ones= getNormalProb(num_of_ones_c1,(num_of_ones_c1+num_of_zeroes_c1));
			System.out.println("p_ones "+p_ones);
			double p_class0_c0=getProb(matching_zero_nodes,num_of_zeroes_c1);
			System.out.println("p_class0_c0 "+p_class0_c0);
			double p_class0_c1= getProb(unmatching_zero_nodes,num_of_zeroes_c1);
			System.out.println("p_class0_c1 "+p_class0_c1);
		//	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			double p_class1_c1= getProb(matching_ones_nodes, num_of_ones_c1);
			System.out.println("p_class1_c1 "+p_class1_c1);
			
			double p_class1_c0=getProb(unmatching_ones_nodes,num_of_ones_c1);
			System.out.println("p_class1_c0 "+p_class1_c0);
			double h_total=-(p_zeroes*(p_class0_c0+p_class0_c1)) -(p_ones*(p_class1_c1+p_class1_c0));
			System.out.println("h_total "+h_total+ "for label "+ test_input[0][split_ele_col_num]);
			double ig= h_split_ele- h_total;
			System.out.println("IG for "+test_input[0][c-1]+" is "+ig);
			//System.out.println("c"+c);
			//if(isNaN())
		//	ig.setPrecision(4);
			ig_attributes[c-1]=(double)ig;
			//c++;
			}//if scope
				
		//	System.out.println("split_seq.length keep track "+split_seq.size());
			
			
			c++;
		}//while loop
		
			for(i=0;i<(c-1);i++)
			{
				System.out.println("IG["+i+"] = "+ig_attributes[i]);
			}
			
			largest=ig_attributes[0];
			
			ig_sorted[0]=largest;
			for(j=0;j<class_col_num;j++)
			{
				if(ig_attributes[j]>largest)
				{
					largest=ig_attributes[j];
					lock=j;
					
				}
			}
			
			for(i=0;i<c;i++)
			{
				ig_attributes[i]=0.0;
			}
		//	System.out.println("split_seq.length "+split_seq.size());
			//System.out.println("ok1 largest IG column "+lock);
			split_seq.add(test_input[0][lock]);
		//	System.out.println("ok2");
			//add(test_input[0][lock]);
		//	System.out.println("split_seq.length "+split_seq.size());
		//	System.out.println(" splitting attribute is "+split_seq.get(t));
			t++;
			//System.out.println("ok3");
		    split_att=test_input[0][lock];
		//	System.out.println("ok4");
		//	System.out.println("split_seq.length "+split_seq.size());
			
			}
			 return split_att;
		}
		
		private static double getNormalProb(double n, double d) {
			// TODO Auto-generated method stub
			
			double ans=(double)(n/d);
			if(n==0.0)
				ans=0.0;
			// TODO Auto-generated method stub
			return ans;
			
		}
		private static double getProb(double numerator,
				double den) {
			double ans=(double)(numerator/den)*(log((numerator/den),2));
			if(numerator==0.0)
				ans=0.0;
			// TODO Auto-generated method stub
			return ans;
		}
		
		
		
	}
	

