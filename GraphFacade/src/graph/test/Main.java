package graph.test;

import graph.ui.Graph;
import graph.uml.CommentBox;
import graph.uml.Item;
import graph.uml.ItemContextEvent;
import graph.uml.ItemContextListener;
import graph.uml.TypeBox;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramTools;
import graph.uml.intern.DefaultUmlDiagram;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Main {
	
	public static void main( String[] args ) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setBounds( 20, 20, 800, 600 );
		Graph graph = new Graph();
		
		UmlDiagram diagram = new DefaultUmlDiagram( graph );

		frame.setLayout( new GridBagLayout() );
		Insets insets = new Insets( 0, 0, 0, 0 );
		frame.add( graph.getView(), new GridBagConstraints( 0, 1, 100, 100, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0 ) );
		frame.add( createTools( diagram.getTools() ), new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0 ) );
		
		diagram.addItemContextListener( disposingListener( diagram ) );
		
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
	
	private static ItemContextListener disposingListener( final UmlDiagram diagram ){
		return new ItemContextListener() {
			private DisposeAction dispose = new DisposeAction( diagram );
			
			@Override
			public void contextAction( ItemContextEvent event ) {
				List<JComponent> menu = new ArrayList<>();
				menu.add( dispose.menuItem );
				event.show( menu );	
			}
		};
	}
	
	private static class DisposeAction implements ActionListener{
		private JMenuItem menuItem;
		private UmlDiagram diagram;
		
		public DisposeAction( UmlDiagram diagram ){
			this.diagram = diagram;
			menuItem = new JMenuItem( "Remove" );
			menuItem.addActionListener( this );
		}
		
		@Override
		public void actionPerformed( ActionEvent e ) {
			for( Item item : diagram.getSelection() ){
				item.dispose();
			}
		}
	}

	private static JPanel createTools( final UmlDiagramTools tools ){
		JPanel panel = new JPanel( new GridLayout( 1, 5 ) );
		JToggleButton applyDefaultTool = new JToggleButton( "Default" );
		JToggleButton applyAddInheritsFromTool = new JToggleButton( "Inherits" );
		JToggleButton applyAddImplementsFromTool = new JToggleButton( "Implements" );
		JToggleButton applyAggregationTool = new JToggleButton( "Aggregation" );
		JToggleButton applyCompositionTool = new JToggleButton( "Composition" );
		
		panel.add( applyDefaultTool );
		panel.add( applyAddInheritsFromTool );
		panel.add( applyAddImplementsFromTool );
		panel.add( applyAggregationTool );
		panel.add( applyCompositionTool );
		
		applyDefaultTool.setSelected( true );
		tools.applyDefaultTool();
		
		ButtonGroup group = new ButtonGroup();
		group.add( applyDefaultTool );
		group.add( applyAddInheritsFromTool );
		group.add( applyAddImplementsFromTool );
		group.add( applyAggregationTool );
		group.add( applyCompositionTool );
		
		applyDefaultTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyDefaultTool();
			}
		});
		
		applyAddInheritsFromTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyAddInheritsFromTool();
			}
		});
		
		applyAddImplementsFromTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyAddImplementsFromTool();
			}
		});
		
		applyAggregationTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyAggregationTool();
			}
		});
		
		applyCompositionTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyCompositionTool();
			}
		});
		
		return panel;
	}
}
