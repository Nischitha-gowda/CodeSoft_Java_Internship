import java.util.Scanner;
public class grade{
    public static void main(String args[]){
        int n,i,total=0;
        double average;
        int sub[]=new int[100];
        Scanner sc=new Scanner (System.in);
        System.out.println("enter the number of subjects:");
        n=sc.nextInt();
        System.out.println("enter the each subject marks");
        for (i=0;i<n;i++){
            System.out.println("enter the marks of subject"+(i+1));
            sub[i]=sc.nextInt();
            total+=sub[i];
        }
        average=total/n;
        System.out.println("Total marks:"+total);
        System.out.println("Average percentage:"+average+"%");
        if(average>=90){
            System.out.println("Grade=A");
        }
        else if( average>=80){
            System.out.println("Grade=B");
        }
        else if(average>=70){
            System.out.println("Grade=C");
        }
        else if(average>=60){
            System.out.println("Grade=D");
        }else{
            System.out.println("Grade=Fail");
        }
    }
}
