package Basis;

public class EX_2016_Opg3 {

	public static void main(String[] args) {
		V3 v=new V3(2,-1, 3);
		V3 u=new V3(1, 4, 2);

		System.out.println("v="+v);
		System.out.println("u="+u);
		System.out.println("|v|="+v.length());
		System.out.println("v*u="+v.dot(u));
		
	}

}
