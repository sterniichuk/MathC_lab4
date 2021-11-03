import java.util.Random;
import java.util.Vector;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.analysis.solvers.PolynomialSolver;
import org.apache.commons.math3.complex.Complex;

public class Main {
	
	public static double[][] matrixData = {{5,3,1},{3,5,3},{1,3,5}};;
	public static RealMatrix A = MatrixUtils.createRealMatrix(matrixData);
	public static Vector<Double> b;
	public static double[][] nullData;
	public static RealMatrix K;
	public static RealMatrix E;
	
	public static void main(String[] args) {		
		int n = Math.max(A.getRowDimension(), A.getColumnDimension());
		nullData =  init(n, n);
		b = new Vector(n);
		E = MatrixUtils.createRealIdentityMatrix(n);
		K = MatrixUtils.createRealIdentityMatrix(n);
		b.add(-((A.multiply(K)).getTrace()));
		b.add(1.0);
		print();
		for(int i = 2; i <= n; ++i) {
			K = (A.multiply(K)).add(E.scalarMultiply(b.firstElement()));
			//b.add();
			b.add(0, A.multiply(K).getTrace() * (-1.0/i));
			print();
		}
		print();
		
	    PolynomialFunction f = new PolynomialFunction(tomasiv(b));
	    LaguerreSolver solver = new LaguerreSolver();
	   // double a = solver.solve(10, f, -10, 10);
	    Complex[] a = solver.solveAllComplex(tomasiv(b), 0);
	    for(Complex i: a) {
	    	System.out.println(i.getReal());
	    }
	    
	}
	private static double[] tomasiv(Vector<Double> vect){
		double[] masiv = new double[vect.size()];
		for(int i = 0; i < vect.size(); ++i) {
			masiv[i] = vect.elementAt(i);
		}
		return masiv;
		
	}
	private static void initMatrix(double[][] matrix, int size) {
		matrix = new double[size][];
		for(int i = 0; i < size; ++i) {
			matrix[i] = new double[size];
		}
	}
	private static void print(String str) {
		System.out.println(str);
	}
	private static void print() {
		System.out.println("////////");
		for(Double i: b) {
			System.out.print(i + " ");
		}
		System.out.println("\n.........");
		print(K);
		System.out.println("////////");
	}
	private static void print(RealMatrix matrix) {
		print(matrix.getData(), matrix.getRowDimension(), matrix.getColumnDimension());
	}
	private static void print(double[][] matrix, int row, int col) {
		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	private static double[][] init(int row, int col) {
		double[][] matrix = new double[row][];
		for(int i = 0; i < row; ++i) {
			matrix[i] = new double[col];
			for(int j = 0; j < col; ++j) {
				matrix[i][j] = 0;
			}
		}
		return matrix;
	}
}
