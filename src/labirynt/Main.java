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
			}
		}					
		panel.setVisible(true);
		frame.add(panel);
		frame.setVisible(true);

		tablicaprzyciskow[2][4].setEnabled(false);
		tablicaprzyciskow[6][4].setEnabled(false);
		tablicaprzyciskow[5][4].setEnabled(false);
		tablicaprzyciskow[4][4].setEnabled(false);
		tablicaprzyciskow[3][4].setEnabled(false);
		tablicaprzyciskow[1][4].setEnabled(false);
		tablicaprzyciskow[0][4].setEnabled(false);
		tablicaprzyciskow[7][2].setEnabled(false);
		tablicaprzyciskow[4][2].setEnabled(false);
		
		int najmniej_odleg = parents[0][0].odleglosc;

					List<Node> children =  parents[0][0].getChild();
					Frontier.add(parents[0][0]);
					
					Node parent = parents[0][0];
					Node old_parent = parents[0][0];
					
					int ruch = 0;
					while(!ClosedList.contains(parents[6][7])) {
						old_parent = parent;
						int new_odleg = 100;
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if(Frontier.contains(parent)) {
							for(int a=0;a<children.size();a++) {
								if(!ClosedList.contains(children.get(a))) {
									children.get(a).Set_from(parent);
								Frontier.add(children.get(a));
								}
							}
							ClosedList.add(parent);
							
						}
						for(int a=0;a<children.size();a++) {
							if(Frontier.contains(children.get(a))) {
								System.out.println(children.get(a).i+" "+children.get(a).j);
								if(tablicaprzyciskow[children.get(a).i][children.get(a).j].isEnabled()==true){	
									new_odleg = children.get(a).odleglosc + ruch +1;
								}
								System.out.println("odleglosc: "+children.get(a).odleglosc);
								System.out.println("odleglosc + ruch: "+new_odleg);
								System.out.println("najmniej_odleg "+najmniej_odleg);
							
								if((new_odleg<=najmniej_odleg) && (!ClosedList.contains(children.get(a))) && (tablicaprzyciskow[children.get(a).i][children.get(a).j].isEnabled()==true)) {
									System.out.println("Dodano");
									ruch++;
									System.out.println("ruch: "+ruch);
									parent = children.get(a);
									tablicaprzyciskow[children.get(a).i][children.get(a).j].setBackground(Color.GREEN);
									children = children.get(a).getChild();
									break;
								}else {
									System.out.println("nie dodano");
								}
								
							}
							
						}
						if((parent==old_parent) && (parent!=parents[0][0])) {
							Frontier.remove(parent);
							ClosedList.add(parent);
							System.out.println("cofam");
							ruch--;
							System.out.println("ruch: "+ruch);
							tablicaprzyciskow[parent.i][parent.j].setBackground(Color.BLACK);
							children = parent.from.getChild();
							parent = parent.from;
						}else if((parent==old_parent) && (parent==parents[0][0])) {
							najmniej_odleg++;
							ClosedList.clear();
							if(Frontier.contains(parent)) {
								for(int a=0;a<children.size();a++) {
									if(!ClosedList.contains(children.get(a))) {
										children.get(a).Set_from(parent);
									Frontier.add(children.get(a));
									}
								}
								ClosedList.add(parent);
								
							}
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
