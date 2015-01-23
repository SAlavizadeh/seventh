/*
 *	leola-live 
 *  see license.txt
 */
package seventh.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import seventh.ai.basic.Zone;
import seventh.graph.AStarGraphSearch;
import seventh.graph.GraphNode;
import seventh.graph.GraphSearchPath;
import seventh.math.Vector2f;

/**
 * A {@link GraphNode} of the {@link Map}
 * 
 * @author Tony
 *
 */
@SuppressWarnings("all")
public class MapGraph<T> {

	private GraphNode[][] graph;
	private seventh.map.Map map;
	private Random random;
	private int width, height;
	/**
	 * 
	 */
	public MapGraph(Map map, GraphNode[][] graph) {
		this.map = map;
		this.graph = graph;
		
		this.height = graph.length;
		this.width = graph[0].length;
		
		this.random = new Random();
	}
	
	/**
	 * @param x
	 * @param y
	 * @return get the {@link GraphNode} by the x and y index (not world coordinates)
	 */
	public GraphNode<Tile, T> getNodeByIndex(int x, int y) {
		return (GraphNode<Tile, T>)graph[y][x];
	}
	
	/**
	 * @param wx
	 * @param wy
	 * @return the graph node at a world coordinate
	 */
	public GraphNode<Tile, T> getNodeByWorld(int wx, int wy) {
		int tileOffset_x = 0;//(wx % map.getTileWidth());
		int x = (tileOffset_x + wx) / map.getTileWidth();

		int tileOffset_y = 0;//(wy % map.getTileHeight());
		int y = (tileOffset_y + wy) / map.getTileHeight();
		return x<width && y<height ? (GraphNode<Tile, T>)graph[y][x] : null;
	}
	
	/**
	 * Finds a path, avoiding the supplied {@link Zone}s
	 * 
	 * 
	 * @param start
	 * @param destination
	 * @param zonesToAvoid
	 * @return the list of node to travel to reach the destination
	 */
	public List<GraphNode<Tile, T>> findPathAvoidZones(GraphSearchPath<Tile, T> searchPath, Vector2f start, Vector2f destination, final List<Zone> zonesToAvoid) {						
		GraphNode<Tile, T> startNode = getNodeByWorld((int)start.x, (int)start.y);
		GraphNode<Tile, T> destNode = getNodeByWorld((int)destination.x, (int)destination.y);
		List<GraphNode<Tile, T>> resultPath = searchPath.search(startNode, destNode);
		return resultPath; 
	}
	
	/**
	 * Finds a fuzzy (meaning not necessarily the most optimal but different) path between the start and end point
	 * @param start
	 * @param destination
	 * @param the amount of fuzzy the add to the path (the greater the number the less efficient the 
	 * path is to the destination)
	 * @return the list of node to travel to reach the destination
	 */
	public List<GraphNode<Tile, T>> findFuzzyPath(GraphSearchPath<Tile, T> searchPath, Vector2f start, Vector2f destination, final int fuzzyNess) {							
		GraphNode<Tile, T> startNode = getNodeByWorld((int)start.x, (int)start.y);
		GraphNode<Tile, T> destNode = getNodeByWorld((int)destination.x, (int)destination.y);
		List<GraphNode<Tile, T>> resultPath = searchPath.search(startNode, destNode);
		return resultPath; 
	}
	

	/**
	 * Finds the optimal path between the start and end point
	 * @param start
	 * @param destination
	 * @return the list of node to travel to reach the destination
	 */
	public List<GraphNode<Tile, T>> findPath(GraphSearchPath<Tile, T> searchPath, Vector2f start, Vector2f destination) {				
		return findFuzzyPath(searchPath, start, destination, 0);
	}	
}
