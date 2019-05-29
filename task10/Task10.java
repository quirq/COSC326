package task10;

import java.util.ArrayList;
import java.util.Scanner;

public class Task10 {

	int finalGoal;
	char operation;
	boolean LEFT = false, RIGHT = true;
	Node finalNode = null;
	NormalNode finalNormalNode = null;

	public Task10(){

		//Takes user input
		Scanner scanner = new Scanner(System.in);

		//Loop through all inputs
		while(scanner.hasNextLine()){

			ArrayList<Integer> nums = new ArrayList<>();

			String numString = scanner.nextLine();
			String goal = scanner.nextLine();

			String[] splitNumString = numString.split(" ");
			for(String s : splitNumString){
				nums.add(Integer.parseInt(s));
			}

			String[] splitGoal = goal.split(" ");

			finalGoal = Integer.parseInt(splitGoal[0]);
			operation = splitGoal[1].charAt(0);

			String result = calculateCombination(nums, finalGoal, operation);
			System.out.println(operation + " " + finalGoal + " " + result);
		}

	}

	//Chooses which operation to use
	public String calculateCombination(ArrayList<Integer> numbers, int finalNumber, char operation){

		int minPossibleNumber = 0;

		for(int i = 0; i < numbers.size(); i++){
			if(numbers.get(i) != 1)
				minPossibleNumber += numbers.get(i);
		}

		if(minPossibleNumber > finalNumber){
			return ("Impossible");
		} else {

			if(operation == 'L'){

				return(leftToRight(numbers));

			} else if (operation == 'N'){

				return(normalSearch(numbers));

			}

		}
		return null;
	}

	//Searching left to right without using order of operations
	public String leftToRight(ArrayList<Integer> numbers){
		finalNode = null;

		//Create root node
		Node root = new Node(numbers.get(0), null, false);
		//Populates tree, and assigns node that is equivalent to the answer
		populateTree(root, 0, numbers);

		//If no node
		if(finalNode == null){
			return ("Impossible");
		}

		StringBuilder result = new StringBuilder();
		String[] symbols = new String[numbers.size() - 1];

		//Loop through node from leaf to root, and create String of answer
		Node curr = finalNode;
		for(int i = numbers.size() - 2; i >= 0; i--){
			if(curr.childOf == LEFT){
				symbols[i] = "+";
			} else {
				symbols[i] = "*";
			}
			curr = curr.parent;
		}

		//Output answer to StringBuilder
		for(int i = 0; i < numbers.size() - 1; i++){
			result.append(numbers.get(i) + " " + symbols[i] + " ");
		}
		result.append(numbers.get(numbers.size() - 1));

		return result.toString();
	}

	//Searching for answer with order of operations in mind
	private String normalSearch(ArrayList<Integer> numbers){

		//Root node
		NormalNode root = new NormalNode(numbers.get(0));
		finalNormalNode = null;
		String[] symbols = new String[numbers.size() - 1];

		StringBuilder sb = new StringBuilder();

		//Find correct node that is leaf and the answer
		populateNormalTree(numbers, 1, root);
		if(finalNormalNode != null){
			for(int i = symbols.length - 1; i >= 0; i--){
				if(finalNormalNode.leftChild){
					symbols[i] = "+";
				} else {
					symbols[i] = "*";
				}
				finalNormalNode = finalNormalNode.parent;
			}
			if(finalNormalNode.equals(root)){
				for(int i = 0; i < symbols.length; i++){
					sb.append(numbers.get(i) + " " + symbols[i] + " ");
				}
				sb.append(numbers.get(numbers.size() - 1));
				return sb.toString();
			}
		} else {
			return "Impossible";
		}
		return "Impossible";
	}

	//Populates tree for order of operations search
	public void populateNormalTree(ArrayList<Integer> numbers, int i, NormalNode normalNode){
		//Current value of node
		int currSum = normalNode.leftVal + normalNode.rightVal;
		//If answer has been found
		if(finalNormalNode != null){
			return;
		}

		//Check if level of node is greater than maximum inputs
		if(i >= numbers.size()){
			if(currSum == finalGoal && normalNode.level == numbers.size() - 1){
				finalNormalNode = normalNode;
			}
			return;
		}

		//Check current values are not equal
		if(currSum - normalNode.rightVal != normalNode.leftVal ||
				currSum - normalNode.leftVal != normalNode.rightVal ||
				normalNode.rightVal * numbers.get(i) / numbers.get(i) != normalNode.rightVal ||
				normalNode.rightVal < 0 || normalNode.leftVal < 0){
			return;
		}

		//If current value greater than end goal, stop creating branch for efficiency
		if(currSum > finalGoal){
			return;
		}

		//If next left node value is less than the answer we're after (for efficiency)
		//Only needs addition
		if(currSum + numbers.get(i) <= finalGoal){
			normalNode.left = new NormalNode(numbers.get(i), i, true, normalNode);
			populateNormalTree(numbers, i+1, normalNode.left);
		}

		//If next right node value is less than the answer we're after (for efficiency)
		//Needs multiplication to check
		if(normalNode.rightVal * numbers.get(i) + normalNode.leftVal <= finalGoal){
			normalNode.right = new NormalNode(numbers.get(i), i, false, normalNode);
			populateNormalTree(numbers, i+1, normalNode.right);
		}

	}

	//Populate tree for left to right search
	public void populateTree(Node currNode, int currDepth, ArrayList<Integer> nums){

		if(currDepth >= nums.size() - 1){
			if(currNode.value == finalGoal){
				//Node is also a leaf
				if(currDepth == nums.size() - 1){
					finalNode = currNode;
				}
			}
			return;
		}

		int nextNum = nums.get(currDepth + 1);

		//Efficiency: If next node is greater than our answer we're looking for, stop creating this branch
		if(currNode.value > finalGoal){
			return;
		}

		//Create next nodes
		currNode.left = new Node(currNode.value + nextNum, currNode, LEFT);
		currNode.right = new Node(currNode.value * nextNum, currNode,  RIGHT);

		//Recursively create rest of tree
		populateTree(currNode.left, currDepth+1, nums);
		populateTree(currNode.right, currDepth+1, nums);
	}

	//Node for left to right search
	private class Node{
		public int value;
		public Node left, right, parent;
		public boolean childOf;

		public Node(int value, Node parent, boolean child){
			this.value = value;
			this.childOf = child;
			this.parent = parent;
		}
	}

	//NormalNode for order of operations, normal search
	private class NormalNode{
		public int leftVal, rightVal;
		public int level;
		public boolean leftChild;
		NormalNode left, right, parent;

		NormalNode(int val, int level, boolean lChild, NormalNode parent){
			this.parent = parent;
			this.leftChild = lChild;

			//Left child, so we do addition
			if(lChild){
				this.level = level;
				this.leftVal = parent.leftVal + parent.rightVal;
				this.rightVal = val;
			}
			//Else right child, so we do multiplication
			else {
				this.leftVal = parent.leftVal;
				this.rightVal = parent.rightVal * val;
				this.level = level;
			}
		}

		NormalNode(int val){
			this.leftVal = 0;
			this.rightVal = val;
			this.parent = null;
		}
	}

	public static void main(String[] args) {
		new Task10();
	}

}
