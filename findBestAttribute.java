import java.io.File;
import java.util.Stack;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.math.*;
import java.lang.Double;


public class findBestAttribute {
	
	public static String test_input [][]= new String[1000][100];
	public static int i = 0, j=0, num_of_columns=-1, num_of_rows=-1,total_count=0;
	public static String str;
	public static double num_zeros_in_class=0.0 ;
	public static double num_ones_in_class=0.0;
	public static int class_col_num, count=0;
	public static double h;
	public static double get_H_attributes;
	public static double ig_attributes[]= new double[100];
	public static double ig_sorted[]= new double[100];
	public static String split_seq[]= new String[100];
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
		{int h_col_num=-1; num_zeros_in_class=0.0; num_ones_in_class=0.0;
			for(int m=0;m<num_of_columns;m++)
			{
			if(test_input[0][m].equals(label))
				h_col_num=m;
			}
			for(int m=1;m<num_of_rows;m++)
			{
				total_count=num_of_rows-1;
				if(test_input[m][h_col_num].equals("0"))
				num_zeros_in_class++;
				else if(test_input[m][h_col_num].equals("1"))
					num_ones_in_class++;
			}
			System.out.println("num_zeros_in_class "+num_zeros_in_class);
			System.out.println("num_ones_in_class "+num_ones_in_class);
			System.out.println("total_count "+total_count);
			
			double p_zeroes=num_zeros_in_class/total_count;
			double p_ones=num_ones_in_class/total_count;
			System.out.println("p_zeroes "+p_zeroes);
			System.out.println("p_ones "+p_ones);
			
	 h= -((p_zeroes*log(p_zeroes,2)) +(p_ones*log(p_ones,2)));	
		System.out.println("Entropy of class is "+h);		
		//	split_seq[count++]=test_input[0][h_col_num];
			
	String next_split=	get_H_attributes(h,split_seq, h_col_num);
	System.out.println("next split attribute****************** "+ next_split);
		get_H_Class(next_split);
			}
		static double log(double x, int base)
		{
		//	System.out.println((double) (Math.log(x) / Math.log(base)));
		    return (double) (Math.log(x) / Math.log(base));
		}
		
		static String get_H_attributes(double h_split_ele, String []split_seq, int split_ele_col_num)
		{
			System.out.println("function is here!" +test_input[1][0]);
			//double num_of_zeroes_c1=0.0,num_of_ones_c1=0.0 , matching_zero_nodes=0.0, unmatching_zero_nodes=0.0 , unmatching_ones_nodes=0.0, matching_ones_nodes=0.0;
			int c=0; 
			while(c<class_col_num)
			{
				double num_of_zeroes_c1=0.0,num_of_ones_c1=0.0 , matching_zero_nodes=0.0, unmatching_zero_nodes=0.0 , unmatching_ones_nodes=0.0, matching_ones_nodes=0.0;
				
			//**RAVEENA SINGH**for one attribute
			for( i=1;i<num_of_rows;i++)
			{
				//**RAVEENA SINGH**check if attribute already used for split**************
				boolean already_used=false;
				if(split_seq.length==(num_of_columns-1))
					return "0";
				for(int k=0;k<split_seq.length;k++)
				{
				if(test_input[0][c].equals(split_seq[k]))
				{
					already_used=true;
				}
				}
				if(already_used==true)
					continue;
				//**RAVEENA SINGH**check if attribute already used for split**************
				System.out.println("check1******** "+"i"+ i+"c "+c +" test_input "+test_input[0][c]);
				if(test_input[i][c].equals("0"))
				num_of_zeroes_c1++;
				else if(test_input[i][c].equals("1"))
					num_of_ones_c1++;
				
				if(test_input[i][c].equals("0"))
				{
					if(test_input[i][split_ele_col_num].equals("0"))
						matching_zero_nodes++;
					else if(test_input[i][split_ele_col_num].equals("1"))
						unmatching_zero_nodes++;
					
				}
				if(test_input[i][c].equals("1"))
				{
					//System.out.println("test_input[i][class_col_num] "+test_input[i][class_col_num]);
					if(test_input[i][split_ele_col_num].equals("1"))
						matching_ones_nodes++;
					else if(test_input[i][split_ele_col_num].equals("0"))
						unmatching_ones_nodes++;
					
				}
				
				}
		
			
			System.out.println("matching_zero_nodes "+matching_zero_nodes);
			System.out.println("unmatching_zero_nodes "+unmatching_zero_nodes);
			System.out.println("matching_ones_nodes "+matching_ones_nodes);
			System.out.println("unmatching_ones_nodes "+unmatching_ones_nodes);
		
			System.out.println("splitting column number "+split_ele_col_num);
			System.out.println("For "+test_input[0][split_ele_col_num]+"****************");
			double p_zeroes= (double)(num_of_zeroes_c1/(num_of_zeroes_c1+num_of_ones_c1));
			System.out.println("p_zeroes "+p_zeroes);
			double p_ones= (double)(num_of_ones_c1/(num_of_ones_c1+num_of_zeroes_c1));
			System.out.println("p_ones "+p_ones);
			double p_class0_c0= (double)(matching_zero_nodes/num_of_zeroes_c1)*(log((matching_zero_nodes/num_of_zeroes_c1),2));
			System.out.println("p_class0_c0 "+p_class0_c0);
			double p_class0_c1= (double)(unmatching_zero_nodes/num_of_zeroes_c1)*(log((unmatching_zero_nodes/num_of_zeroes_c1),2));
			System.out.println("p_class0_c1 "+p_class0_c1);
			double p_class1_c1= (double)(matching_ones_nodes/num_of_ones_c1)*(log((matching_ones_nodes/num_of_ones_c1),2));
			System.out.println("p_class1_c1 "+p_class1_c1);
			double p_class1_c0= (double)(unmatching_ones_nodes/num_of_ones_c1)*(log((unmatching_ones_nodes/num_of_ones_c1),2));
			System.out.println("p_class1_c0 "+p_class1_c0);
			double h_total=-(p_zeroes*(p_class0_c0+p_class0_c1)) -(p_ones*(p_class1_c1+p_class1_c0));
			System.out.println("h_total "+h_total);
			double ig= h_split_ele- h_total;
			System.out.println("IG for "+test_input[0][split_ele_col_num]+" is "+ig);
			
			//if(isNaN())
			ig_attributes[c]=ig;
			c++;
		}//while loop
		
			for(i=0;i<c;i++)
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
			split_seq[i]=test_input[0][lock];
			System.out.println(" splitting attribute is "+split_seq[i]);
			String split_att=test_input[0][lock];
			return split_att;
			
		}
		
		public static void get_split_seq()
		{
				largest=ig_attributes[i];
				//split_seq[i]=i;
				ig_sorted[0]=largest;
				for(j=0;j<class_col_num;j++)
				{
					if(ig_attributes[j]>largest)
					{
						largest=ig_attributes[j];
						lock=j;
						
					}
				}
				System.out.println("first splitting attribute is C"+(lock+1));
				
			
			
			System.out.println("sorted IG attributes array");
			
		}
		
		
	}
	

