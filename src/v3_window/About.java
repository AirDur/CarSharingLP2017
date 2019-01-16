package v3_window;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Classe qui permet d'afficher la fen�tre "A propos"
 * 
 * @author AirDur
 * @version Build III -  v0.6
 * @since Build III -  v0.6
 */
public class About extends JDialog{


	private static final long serialVersionUID = 1L;

	private final static String file = "lib/Textes.json";
	// Objets qui permet de lire le document :   
	private JSONParser parser = new JSONParser();
	protected JSONObject JSON_Window, JSON_Boutons;

	private static final int width = 350;
	private static final int height = 170;
	/**
	 * Constructeur de la fenetre
	 * @version Build III -  v0.6
	 * @since Build III -  v0.6
	 */
	public About(Frame parent, boolean modal){
		super(parent, modal);

		try								{ this.ouvertureFichier(); 	} 
		catch (FileNotFoundException e) { e.printStackTrace();		}
		catch (IOException e)			{ e.printStackTrace();		}
		catch (ParseException e)		{ e.printStackTrace();		}

		buildInCenter();

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JLabel title =		buildLabel("title",		Font.PLAIN,	24);
		JLabel version =	buildLabel("version",	Font.BOLD,	14);
		JLabel programmer = buildLabel("programmer",Font.PLAIN,	16);
		JLabel license =	buildLabel("license",	Font.PLAIN,	14);
		JLabel link =		buildLabel("link",		Font.PLAIN,	16);
		JLabel dummy = new JLabel("");
		
		title.setForeground(new java.awt.Color(255, 153, 102));
		link.setCursor(new Cursor(Cursor.HAND_CURSOR));
		link.setToolTipText((String) this.JSON_Window.get("tooltip"));

		add(title		); 
		add(version		);
		add(programmer	);
		add(license		);
		add(link		);
		add(dummy		);

		goDropBox(link);

		title.		setBounds(5,  0, 330, 30);
		version.	setBounds(5, 30, 330, 20);
		programmer.	setBounds(5, 55, 330, 20);
		license.	setBounds(5, 80, 330, 20);
		link. 		setBounds(5,105, 330, 20);
		dummy.		setBounds(5,130, 330, 20);
	}

 	private void buildInCenter() {
		// the aboutBox is located in the center of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double ScreenHeight = screenSize.getHeight();
		int x = ((int)screenWidth-About.width)/2;
		int y = ((int)ScreenHeight-About.height)/2;
		setSize(About.width,About.height);
		setLocation(x, y);
	}

	private JLabel buildLabel(String string, int Style ,int i) {
		JLabel jl = new JLabel((String) this.JSON_Window.get(string), JLabel.CENTER);
		jl.setFont(new Font("Helvetica",Style,i));
		return jl;
	}

	private void goDropBox(JLabel website) {
		website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/AirDur/CarSharingLP2017"));
				} catch (URISyntaxException | IOException ex) {
					//It looks like there's a problem
				}
		    }
		});
    }
    
    /**
     * Ouvre le fichier contenant les donn�es.
     * Permet � terme de g�n�raliser afin d'avoir plusieurs langages.
     * @version Build III -  v0.5
	 * @since Build III -  v0.5
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    private void ouvertureFichier() throws FileNotFoundException, IOException, ParseException {
    	Object obj = this.parser.parse(new FileReader(About.file));
    	JSONObject tampon = (JSONObject) obj;
    	this.JSON_Window = (JSONObject) tampon.get("About");
		this.JSON_Boutons = (JSONObject) this.JSON_Window.get("Button");
    }
    
}