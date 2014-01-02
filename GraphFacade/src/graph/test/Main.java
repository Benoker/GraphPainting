package graph.test;

import graph.uml.CommentBox;
import graph.uml.Connection;
import graph.uml.ConnectionLabelConfiguration;
import graph.uml.Item;
import graph.uml.TypeBox;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramRepository;
import graph.uml.UmlDiagramTools;
import graph.uml.UmlDiagramView;
import graph.uml.event.ItemContextEvent;
import graph.uml.event.ItemContextListener;
import graph.uml.event.ItemSelectionEvent;
import graph.uml.event.ItemSelectionListener;
import graph.uml.io.Format;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Main {

	public static void main( String[] args ) throws IOException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setBounds( 20, 20, 800, 600 );

		final UmlDiagramRepository repository = UmlDiagramRepository.createDefaultRepository();

		UmlDiagramView view = repository.createView();

		frame.setLayout( new GridBagLayout() );
		Insets insets = new Insets( 0, 0, 0, 0 );
		frame.add( view.getComponent(), new GridBagConstraints( 0, 1, 100, 100, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0 ) );
		frame.add( createTools( view.getTools() ), new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0 ) );

		UmlDiagram diagram = tryRead( repository );
		if( diagram == null ) {
			diagram = repository.createEmptyDiagram();

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

			Connection con = b.addAggregation( d );
			c.addComposition( d );

			CommentBox co = a.addComment( "some comment text" );
			co.setLocation( 250, 170 );
			
			con.addLabel( ConnectionLabelConfiguration.SOURCE ).setText( "source" );
			con.addLabel( ConnectionLabelConfiguration.MIDDLE ).setText( "middle" );
			con.addLabel( ConnectionLabelConfiguration.TARGET ).setText( "target" );
		}

		diagram.addItemContextListener( disposingListener( diagram ) );
		diagram.addItemSelectionListener( selectionListener() );

		view.setDiagram( diagram );
		frame.addWindowListener( writeOnClose( diagram, repository ) );
		frame.setVisible( true );
	}

	private static WindowListener writeOnClose( final UmlDiagram diagram, final UmlDiagramRepository repository ) {
		return new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				write( diagram, repository );
			}
		};
	}

	private static UmlDiagram tryRead( UmlDiagramRepository repository ) throws IOException {
		Path path = Paths.get( "diagram.xml" );
		if( Files.isReadable( path ) ) {
			return repository.read( Format.XML, path );
		} else {
			return null;
		}
	}

	private static void write( UmlDiagram diagram, UmlDiagramRepository repository ) {
		try {
			Path path = Paths.get( "diagram.xml" );
			repository.write( diagram, Format.XML, path );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

	private static ItemContextListener disposingListener( final UmlDiagram diagram ) {
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

	private static ItemSelectionListener selectionListener() {
		return new ItemSelectionListener() {
			@Override
			public void itemSelectionChanged( ItemSelectionEvent event ) {
				System.out.println( "Selection of " + event.getItem() + " is " + event.getSelection() );
			}
		};
	}

	private static class DisposeAction implements ActionListener {
		private JMenuItem menuItem;
		private UmlDiagram diagram;

		public DisposeAction( UmlDiagram diagram ) {
			this.diagram = diagram;
			menuItem = new JMenuItem( "Remove" );
			menuItem.addActionListener( this );
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			for( Item item : diagram.getSelection() ) {
				item.dispose();
			}
		}
	}

	private static JPanel createTools( final UmlDiagramTools tools ) {
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
		} );

		applyAddInheritsFromTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyAddInheritsFromTool();
			}
		} );

		applyAddImplementsFromTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyAddImplementsFromTool();
			}
		} );

		applyAggregationTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyAggregationTool();
			}
		} );

		applyCompositionTool.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tools.applyCompositionTool();
			}
		} );

		return panel;
	}
}
