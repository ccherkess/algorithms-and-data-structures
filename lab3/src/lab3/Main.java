package lab3;

import java.io.*;

import tree.AVLTree;
import tree.BinarySearchTree;
import tree.BinaryTree;
import tree.RedBlackTree;


public class Main {

	public static void main(String[] args) {
		
		BinaryTree<Integer> binaryTree = BinaryTree.parsingFromString(readFromFile());
		
		System.out.println(binaryTree);
		
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>(binaryTree);
		
		System.out.println(binarySearchTree);
		
		AVLTree<Integer> AVLTree = new AVLTree<Integer>(binaryTree);
		
		System.out.println(AVLTree);
		
		RedBlackTree<Integer> RBTree = new RedBlackTree<Integer>(binaryTree);
		
		System.out.println(RBTree);
	}
	
	private static String readFromFile() {
		
		File file = new File("in.txt");
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(file));){
			
			return br.readLine();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}

}
