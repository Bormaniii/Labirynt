package labirynt;

import java.awt.Color;
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

				List<Node> children = new ArrayList<Node>();
				parents[i][j] = new Node(tablicaprzyciskow[i][j], i, j, children);
			}
		}

		tablicaprzyciskow[0][0].setText("S"); //start
		tablicaprzyciskow[6][7].setText("M"); //meta

		//dodawanie Node's child
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				int odleglosc;
				odleglosc = odleglosc(i,j, 6, 7);
				parents[i][j].addOdleglosc(odleglosc);
				
				System.out.println("i: "+i+" j: "+j+" od: "+parents[i][j].odleglosc);
				
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
		
		//ustawiam przyciski, ktore maja zostac ominiete
		Frontier.remove(parents[6][4]);
		tablicaprzyciskow[6][4].setEnabled(false);

		int najmniej_odleg = parents[0][0].odleglosc;

					List<Node> children =  parents[0][0].getChild();
					System.out.println(children.get(0).j);
					
					Node parent = parents[0][0];
					
					int ruch = 0;
					while(!ClosedList.contains(parents[6][7])) {

						int nr_dziecka = 0; //zapamietuje wybranego child
						int new_odleg = 0;
						for(int a=0;a<children.size();a++) {
							children.get(a).Set_from(parent);
							if(Frontier.contains(parents[children.get(a).i][children.get(a).j])) {
							System.out.println(children.get(a).i+" "+children.get(a).j);			
								ruch++;					
							new_odleg = children.get(a).odleglosc + ruch;
							System.out.println("ruch: "+ruch);
							System.out.println("odleglosc: "+children.get(a).odleglosc);
							System.out.println("nowa odl "+new_odleg);
							if((a+1==children.size()) && (new_odleg!=najmniej_odleg)) {
								Frontier.remove(parents[children.get(a).i][children.get(a).j]);
								ClosedList.add(parents[children.get(a).i][children.get(a).j]);								
								System.out.println("po dzieciach");
							}
								if(new_odleg==najmniej_odleg) {
									nr_dziecka=a;
									Frontier.remove(parents[children.get(a).i][children.get(a).j]);
									ClosedList.add(parents[children.get(a).i][children.get(a).j]);	
									System.out.println("Dodano");
									parent = children.get(a);
									tablicaprzyciskow[children.get(a).i][children.get(a).j].setBackground(Color.GREEN);
									break;
								}else {
									System.out.println("nie dodano");
									ruch--;
									Frontier.remove(parents[children.get(a).i][children.get(a).j]);
									ClosedList.add(parents[children.get(a).i][children.get(a).j]);									
								}
							}
							
						}
						if(new_odleg!=najmniej_odleg) {
							children = parents[0][0].getChild();
							System.out.println("cofam");
							ruch=0;
						}else {
							
							children = parents[children.get(nr_dziecka).i][children.get(nr_dziecka).j].getChild();	
													
						}
							
					}
					System.out.println("Dotarles");
	}
	
	//sprawdza odleglosc
	int odleglosc(int i, int j, int ii, int jj) {
		//i oraz j to wspolrzedne punktu, od ktorego odleglosc jest sprawdzana
		//ii oraz jj to wspolrzedne punktu, do ktorego odleglosc jest sprawdzana
		int odleglosc = 0;
		
		if((ii>=i) && (jj>=j)){
			odleglosc = (ii-i)+(jj-j);
		}else if((ii<i) && (jj>=j)) {
			odleglosc = -(ii-i)+(jj-j);
		}else if((ii>=i) && (jj<j)) {
			odleglosc = -(jj-j)+(ii-i);
		}
		return odleglosc;
	}
	
	void zmiana_koloru(JButton[][] tablicaprzyciskow) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(tablicaprzyciskow[i][j].getBackground()==Color.GREEN) {
					tablicaprzyciskow[i][j].setBackground(Color.BLACK);
				}
			}
		}	
	}
}
