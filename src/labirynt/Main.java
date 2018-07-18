package labirynt;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main Main = new Main();
	}

	
	
	public Main() {
		JFrame frame = new JFrame("Labirynt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 450);
		JPanel panel = new JPanel(new GridLayout(8,8));
		JButton[][] tablicaprzyciskow = new JButton[8][8];
		Node[][] parents = new Node[8][8];

		List<Node> Frontier = new ArrayList<Node>();
		List<Node> ClosedList = new ArrayList<Node>();

		
		//Tworzenie siatki
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				tablicaprzyciskow[i][j] = new JButton();
				panel.add(tablicaprzyciskow[i][j]);
			}
		}

		tablicaprzyciskow[0][0].setText("S"); //start
		tablicaprzyciskow[6][7].setText("M"); //meta

		//dodawanie Node's child
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				int odleglosc;
				odleglosc = odleglosc(i,j, 6, 7);
				
				System.out.println("i: "+i+" j: "+j+" od: "+odleglosc);
				
				List<Node> children = new ArrayList<Node>();
				parents[i][j] = new Node(tablicaprzyciskow[i][j], i, j, children, odleglosc);
				System.out.println(parents[i][j].odleglosc);
				if(i-1>=0) {
					parents[i][j].addChild(parents[i-1][j]);
				}
				if(j-1>=0) {
					parents[i][j].addChild(parents[i][j-1]);
				}
				if(i+1<=7) {
					parents[i][j].addChild(parents[i+1][j]);
				}
				if(j+1<=7) {
					parents[i][j].addChild(parents[i][j+1]);
				}
				Frontier.add(parents[i][j]);
			}
		}	
				
		panel.setVisible(true);
		frame.add(panel);
		frame.setVisible(true);
		
		
					//tu mam problem
					List<Node> child =  parents[0][0].getChild();
					
					
					for(int a=0;a<child.size();a++) {
						System.out.println(child.get(a).odleglosc+" "+child.get(a).i);
						int odleg = child.get(a).odleglosc + odleglosc(child.get(a).i, child.get(a).j, 0, 0);
						System.out.println(odleg);
					}				
		
		
	}
	
	//sprawdza odleglosc
	int odleglosc(int i, int j, int ii, int jj) {
		
		int odleglosc = (ii-i)+(jj-j);
		if((i==jj)&&(j==ii)) {
			odleglosc = 2;
		}if(odleglosc<0) {
			odleglosc = odleglosc*-1;
		}
		return odleglosc;
	}
}
