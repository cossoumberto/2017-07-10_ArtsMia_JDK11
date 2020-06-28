package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private TextField txtLUN;

    @FXML
    private Button btnCalcolaComponenteConnessa;

    @FXML
    private Button btnCercaOggetti;

    @FXML
    private Button btnAnalizzaOggetti;

    @FXML
    private TextField txtObjectId;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	model.creaGrafo();
    	txtResult.setText("Grafo creato con " + model.getNumVertex() + " vertici e " + model.getNumEdge() + " archi");
    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	try {
    		Integer id = Integer.parseInt(txtObjectId.getText());
    		Integer numConnessi = model.componenteConnessa(id);
    		if(numConnessi!=null)
    			txtResult.setText("La componente connessa del vertice contiene " + numConnessi + " vertici");
    		else
    			txtResult.setText("Vertice non esistente");
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("Inserimento non valido");
    	}
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {
    	try {
    		Integer id = Integer.parseInt(txtObjectId.getText());
    		Integer LUN =  Integer.parseInt(txtLUN.getText());
    		if(LUN<2||LUN>model.componenteConnessa(id))
    			throw new NumberFormatException();
    		if(model.componenteConnessa(id)==1)
    			txtResult.setText("Componente connessa composta da solo un vertice");
    		else {
    			List<ArtObject> list = model.percorso(id, LUN);
    			if(list==null)
    				txtResult.setText("Impossibile trovare un cammino dal vertice " + id);
    			else {
	    			txtResult.setText("ArtObjects presenti nel cammino di peso max con lunghezza " + LUN + ": ");
	    			for(ArtObject a : model.percorso(id, LUN))
	    				txtResult.appendText("\n" + a);
    			}
    		}
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("Inserimento non valido");
    	}
    }

    @FXML
    void initialize() {
    	assert txtLUN != null : "fx:id=\"txtLUN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
