package Basis;

public class EX_2016_Opg2 {

	public static void main(String[] args) {
		M3 A=new M3(-1, 4, 2,
				         2, 0,-4,
				         6, 1, 7);
		M3 B=new M3( 5,-2, 4,
				        -6, 0, 0,
				         3, 1, 3);
		V3 v=new V3( 2, 4,-3);
		M3 M=new M3(-1, 0, 0,
				         0, 1, 0,
				         0, 0, 1);
		
		System.out.println("A="+A);
		System.out.println("B="+B);
		System.out.println("v="+v);
		System.out.println("M="+M);
		System.out.println();
		System.out.println("A*v="+A.mul(v));
		System.out.println("A*B="+A.mul(B));
		System.out.println("M*v="+M.mul(v));
		
	}

}
