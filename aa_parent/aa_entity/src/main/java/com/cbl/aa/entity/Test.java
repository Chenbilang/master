package com.cbl.aa.entity;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		Tree tree=new Tree();
		tree.setText("t1");
		Tree tree2=new Tree();
		List<Tree> list=new ArrayList<Tree>();
		list.add(tree);
		tree2.setChildren(list);
		
		for (Tree tree3 : tree2.getChildren()) {
			System.out.println(tree3.getText());
			
		}
		list.remove(0);
		for (Tree tree3 : tree2.getChildren()) {			
			System.out.println(tree3.getText());
			
		}
	}
}
