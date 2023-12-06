package Runi;

public class M3 {
	
	public double[][] A = new double[3][3];
	
	public M3(	double a11, double a12, double a13,
				double a21, double a22, double a23,
				double a31, double a32, double a33){
		
		A[0][0] = a11;	A[0][1] = a12;	A[0][2] = a13;
		A[1][0] = a21;	A[1][1] = a22;	A[1][2] = a23;
		A[2][0] = a31;	A[2][1] = a32;	A[2][2] = a33;
		
	}
	
	private M3(double[][] A){
		this.A = A;
	}
	
	public M3 add(M3 m){
		double[][] matrix = new double[3][3];
		for(int r = 0; r < 3; r++){
			for(int c = 0; c < 3; c++){
				matrix[r][c] = A[r][c]+m.A[r][c];
			}
		}
		return new M3(matrix);
	}
		
	public M3 mul(double scalar){
		double[][] arr = new double[3][3];
		for(int r = 0; r < 3; r++){
			for(int c = 0; c <3; c++){
				arr[r][c] = this.A[r][c]*scalar;
			}
		}
		return new M3(arr);
	}
	public V3 mul(V3 v){
		double[] vectorB = new double[3];
		double[] vectorA = new double[]{v.x, v.y, v.z};
		
		for(int r = 0; r < 3; r++){
			for(int c = 0; c <3; c++){
				vectorB[r] += (this.A[r][c]*vectorA[c]);
			}
		}
		return new V3(vectorB[0], vectorB[1], vectorB[2]);
	}
	
	public M3 mul(M3 m){
		double[][] arr = new double[3][3];
		for(int r = 0; r < 3; r++){
			for(int c = 0; c <3; c++){
				for(int i = 0; i < 3; i++){	
					arr[r][c] += (this.A[r][i]*m.A[i][c]);
				}
			}
		}
		return new M3(arr);
	}
	
	
	public M3 div(double scalar){
			double[][] arr = new double[3][3];
			for(int r = 0; r < 3; r++){
				for(int c = 0; c <3; c++){
					arr[r][c] = this.A[r][c]/scalar;
				}
			}
			return new M3(arr);
	}

	
	public String toString(){
		String toString = "M3{\n";
		for(int r = 0; r < 3; r++){
			for(int c = 0; c < 3; c++){
				toString += A[r][c] + "\t";
			}
			toString+= "\n";
		}
		return toString+"}";
	}

}

