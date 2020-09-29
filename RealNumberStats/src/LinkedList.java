import java.util.ArrayList;

public class LinkedList {
	private class Node {
		private Double value;
		private Node previous;
		private Node next;
		
		public Node(Double value) {
			this.value = value;
			previous = null;
			next = null;
		}
		
		public Node(Node prev, Double value) {
			this.value = value;
			prev.next = this;
			this.previous = prev;
			this.next = null;
		}
	}
	
	Node head;
	Node tail;
	int size;
	Double sum;
	
	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
		sum = 0.0;
	}
	
	public LinkedList(ArrayList<Double> values) {
		this();
		
		for(Double value : values) {
			add_number(value);
		}
	}
	
	public void add_number(Double value) {
		if(head == null) {
			head = new Node(value);
			tail = head;
		}
		
		else {
			tail = new Node(tail, value);
		}
		size++;
		sum += value;
	}
	
	public Double getMean() {		
		return sum/size;
		
	}
	
	public Double getStdDev() {
		final Double u = getMean();
		Double dev = 0.0;
		Node temp = head;
		
		while(temp != null) {
			dev += Math.pow(temp.value - u, 2);
			temp = temp.next;
		}
		
		return Math.pow(dev/size,.5);
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
}
