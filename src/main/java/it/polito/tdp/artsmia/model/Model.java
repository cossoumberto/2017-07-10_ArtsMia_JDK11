package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private List<ArtObject> bestPercorso;
	private Integer bestPeso;
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<>();
		dao.listObjects(idMap);
		grafo = null;
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, idMap.values());
		for(CoppiaObjects c : dao.listCoppieObjects(idMap))
			Graphs.addEdge(grafo, c.getO1(), c.getO2(), c.getPeso());
	}
	
	public Integer getNumVertex() {
		return grafo.vertexSet().size();
	}
	
	public Integer getNumEdge() {
		return grafo.edgeSet().size();
	}

	public Integer componenteConnessa(Integer id) {
		ConnectivityInspector<ArtObject, DefaultWeightedEdge> ci = new ConnectivityInspector<>(grafo);
		if(grafo.vertexSet().contains(idMap.get(id)))
			return ci.connectedSetOf(idMap.get(id)).size();
		else return null;
	}
	
	public List<ArtObject> percorso(Integer id, Integer LUN) {
		bestPercorso = null;
		bestPeso = 0;
		List<ArtObject> parziale = new ArrayList<>();
		parziale.add(idMap.get(id));
		cerca(parziale, idMap.get(id).getClassification(), 0, LUN);
		if(bestPercorso!=null)
			Collections.sort(bestPercorso);
		return bestPercorso;
	}

	private void cerca(List<ArtObject> parziale, String classification, int peso, Integer LUN) {
		if(parziale.size()-1 == LUN) {
			if(peso>bestPeso) {
				bestPercorso = new ArrayList<>(parziale);
				bestPeso = peso;
			}
			return;
		} else {
			if(grafo.degreeOf(parziale.get(parziale.size()-1))>0) 
				for(ArtObject a : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) 
					if(!parziale.contains(a) && a.getClassification().equals(classification)) {
						parziale.add(a);
						cerca(parziale, classification, peso + (int) grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-2), a)), LUN);
						parziale.remove(parziale.size()-1);
					}
		}
		
	}
	
	
}
