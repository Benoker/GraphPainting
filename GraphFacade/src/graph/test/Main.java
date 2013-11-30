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
		
		TypeBox a = diagram.createType();
		a.setText( "alpha" );
		a.setLocation( 50, 50 );
		a.setVisible( true );
		
		TypeBox b = diagram.createType();
		b.setText( "beta" );
		b.setLocation( 350, 350 );
		b.setVisible( true );
		
		TypeBox c = diagram.createType();
		c.setText( "gamma" );
		c.setLocation( 50, 450 );
		c.setVisible( true );
		
		TypeBox d = diagram.createType();
		d.setText( "delta" );
		d.setLocation( 350, 70 );
		d.setVisible( true );
		
		a.addInheritsFrom( b ).setVisible( true );
		a.addImplementsFrom( c ).setVisible( true );
		
		b.addAggregation( d ).setVisible( true );
		c.addComposition( d ).setVisible( true );
		
		CommentBox co = a.addComment( "some comment text" );
		co.setLocation( 250, 170 );
		co.setVisible( true );
		
		frame.setVisible( true );
	}
}
