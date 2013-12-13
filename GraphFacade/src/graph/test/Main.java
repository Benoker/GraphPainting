package graph.test;

import graph.ui.Graph;
import graph.uml.CommentBox;
import graph.uml.TypeBox;
import graph.uml.intern.DefaultUmlDiagram;

import javax.swing.JFrame;

public class Main {
	
	public static void main( String[] args ) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setBounds( 20, 20, 800, 600 );
		Graph graph = new Graph();
		frame.add( graph.getView() );
		
		DefaultUmlDiagram diagram = new DefaultUmlDiagram( graph );
		diagram.getTools().applyAddInheritsFromTool();
		
		TypeBox a = diagram.createType();
		a.setText( "alpha" );
		a.setLocation( 50, 50 );
		
		TypeBox b = diagram.createType();
		b.setText( "beta" );
		b.setLocation( 350, 350 );
		
		TypeBox c = diagram.createType();
		c.setText( "gamma" );
		c.setLocation( 50, 450 );
		
		TypeBox d = diagram.createType();
		d.setText( "delta" );
		d.setLocation( 350, 70 );
		
		a.addInheritsFrom( b );
		a.addImplementsFrom( c );
		
		b.addAggregation( d );
		c.addComposition( d );
		
		CommentBox co = a.addComment( "some comment text" );
		co.setLocation( 250, 170 );
		
		frame.setVisible( true );
	}
}
