package labirynt;

import java.util.List;

import javax.swing.JButton;

public class Node {
	
	public JButton data;
	public List<Node> next;
	public int odleglosc;
	public int i;
	public int j;
	
	public Node(JButton data, int i, int j, List<Node> next, int odleglosc) {
		this.data=data;
		this.next=next;
		this.odleglosc=odleglosc;
		this.i=i;
		this.j=j;
	}
	public Node() {		
	}
	
	public String toString () { 
        return data + ""; 
    }
	
	void addChild(Node child) {
		next.add(child);
	}
	
	List<Node> getChild(){
		return this.next;
	}

}
