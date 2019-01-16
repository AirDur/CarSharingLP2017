package v3_window;

import java.awt.event.*;
import v3_algo.Execut_Algo_Genetique;

/**
 * Fonctions correspond aux actions d'un bouton
 *  @author AirDur
 * @version Build III -  v0.5
 * @since Build III -  v0.5
 */
public class ActionHandler implements ActionListener {
    
	protected Window win;
	protected String name;
    
    protected ActionHandler(Window win, String name) {
    	this.win = win;
    	this.name = name;
    }
    
    @Override
    /**
     * Classe qui g�re les actions
     * @author AirDur
     * @version Build III -  v0.6
   	 * @since Build III -  v0.5
     */
    public void actionPerformed(ActionEvent evt) {
        switch(this.name) {
	        case "New Grid" : 
	        	//Affiche une nouvelle grille selon les param�tres
	        	win.realTime = false;
	        	win.animationButton.setEnabled(true);
	        	win.slider.setEnabled(true);
	        	win.initializeGrid();
	        	break;
	        	
	        case "Clear" : 
	        	//Efface le parcours (Animation)
	        	win.deleteCarAndPassagerGrid();
	        	break;
	        case "Clear2" :
	        	//Enl�ve tout les murs de la grille.
	        	win.deleteWallGrid();
	        	break;
	        case "Animation" :
	        	//Affiche pour chaque �tape de l'algo g�n�tique le parcours des voitures.
	        	if(!win.endOfSearch) { //Ne peut pas se d�clancher si le truc marche pas.
	        		win.realTime = false;
	                win.searching = true;
	                win.message.setText((String) win.JSON_Window.get("msgSelectStepByStepEtc"));
	                win.promptSelected = true;
	                win.algo = new Execut_Algo_Genetique(win);
	                win.timer.setDelay(win.delay);
	                win.timer.start();
	        	}
	        	break;
	        case "ChangeState" : 
	        	win.editButton("ChangeState_2", win.changeStateButton);
	        	win.typeColoriage = true;
	        	break;
	        case "ChangeState_2" : 
	        	win.editButton("ChangeState", win.changeStateButton);
	        	win.typeColoriage = false;
	        	break;
	        case "About" :
	            About about = new About(Main.mazeFrame,true);
	            about.setVisible(true);
	            break;
	            
        }
        
    }
} // end nested class ActionHandler
