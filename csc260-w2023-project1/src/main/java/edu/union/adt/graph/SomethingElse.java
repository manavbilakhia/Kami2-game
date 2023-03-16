package edu.union.adt.graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
/**
 * A graph that establishes connections (edges) between objects of
 * (parameterized) type V (vertices).  The edges are directed.  An
 * undirected edge between u and v can be simulated by two edges: (u,
 * v) and (v, u).
 *
 * The API is based on one from
 *     http://introcs.cs.princeton.edu/java/home/
 *
 * Some method names have been changed, and the Graph type is
 * parameterized with a vertex type V instead of assuming String
 * vertices.
 *
 * @author Aaron G. Cass
 * @version 1
 */
public class SomethingElse<V> implements Graph<V>
{
    private Map<V, List<V>> adjacencyList;
    private int numV;
    private int numE;
    /**
     * Create an empty graph.
     */
    public SomethingElse() 
    {
        adjacencyList = new HashMap<V, List<V>>();
        numV = 0;
        numE = 0;
    }

    /**
     * @return the number of vertices in the graph.
     */
    public int numVertices()
    {
        return numV;
    }

    /**
     * @return the number of edges in the graph.
     */
    public int numEdges()
    {
        return numE;
    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
    public int degree(V vertex)
    {
        if (!adjacencyList.containsKey(vertex)) {
            throw new RuntimeException("Vertex not found in graph");
        }
        return adjacencyList.get(vertex).size();
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
    public void addEdge(V from, V to)
    {
        if (!adjacencyList.containsKey(from)) {
            addVertex(from);
        }
        if (!adjacencyList.containsKey(to)) {
            addVertex(to);
        }
        if (!adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
            numE++;
        }
    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    public void addVertex(V vertex)
    {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new LinkedList<V>());
            numV++;
        }
    }

    /**
     * @return the an iterable collection for the set of vertices of
     * the graph.
     */
    public Iterable<V> getVertices()
    {
        return adjacencyList.keySet();
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    public Iterable<V> adjacentTo(V from)
    {
        if (!adjacencyList.containsKey(from)) {
            return new LinkedList<V>();
        }
        return adjacencyList.get(from);
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    public boolean contains(V vertex)
    {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Tells whether an edge exists in the graph.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
    public boolean hasEdge(V from, V to)
    {
        if (!adjacencyList.containsKey(from)) {
            return false;
        }
        return adjacencyList.get(from).contains(to);
    }
    /**
     * Tells whether or not two graphs are equal.  Two graphs are
     * equal if they have the same number of vertices, the same
     * number of edges, and the same set of edges.
     *
     * @param other the object to compare to this graph
     * 
     * @return true iff the given object is a graph that is equal to 
     */
    public boolean equals(Object other)
    {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SomethingElse)) {
            return false;
        }
        SomethingElse<V> otherGraph = (SomethingElse<V>) other;
        if (otherGraph.numV != numV) {
            return false;
        }
        if (otherGraph.numE != numE) {
            return false;
        }
        for (V vertex : adjacencyList.keySet()) {
            if (!otherGraph.adjacencyList.containsKey(vertex)) {
                return false;
            }
            if (!otherGraph.adjacencyList.get(vertex).equals(adjacencyList.get(vertex))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a list of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A: A, B
     * B:
     * C: A, B
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
     * adjacent vertices.
     *
     * Note: there are no extraneous spaces in the output.  So, if we
     * replace each space with '*', the above representation would be:
     *
     * <PRE>
     * A:*A,*B
     * B:
     * C:*A,*B
     * </PRE>
     *
     * @return the string representation of the graph
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (V vertex: adjacencyList.keySet()) {
            sb.append(vertex + ": ");
            boolean first = true;
            for (V neighbor: adjacencyList.get(vertex)) {
                if (!first) {
                    sb.append(", ");
                }
                first = false;
                sb.append(neighbor);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    public boolean isEmpty()
    {
        return numV == 0;
    }
    /**
     * Removes and vertex from the graph.Also removes any 
     * edges connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    public void removeVertex(V toRemove)
    {
        if (!adjacencyList.containsKey(toRemove)) {
            return;
        }
        for (V vertex: adjacencyList.keySet()) {
            if (adjacencyList.get(vertex).contains(toRemove)) {
                adjacencyList.get(vertex).remove(toRemove);
                numE--;
            }
        }
        adjacencyList.remove(toRemove);
        numV--;
    }
    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to)
    {
        if (!adjacencyList.containsKey(from)) {
            return;
        }
        if (adjacencyList.get(from).contains(to)) 
        {
            adjacencyList.get(from).remove(to);
            numE--;
        }

    }
    /**
     * Returns a list of vertices that are adjacent to the given
     * vertex.  If the given vertex is not in the graph, then the
     * returned list is empty.
     *
     * @param vertex the vertex whose neighbors are to be returned
     * @return a list of vertices that are adjacent to the given
     * vertex
     */
    private void bfs (V from, V to, Map<V, Boolean> visited, Map<V,V> parent)
    {
        Queue<V> q = new LinkedList<V>();
        q.add(from);
        visited.put(from, true);
        while (!q.isEmpty()) {
            V current = q.remove();
            if (current.equals(to)) {
                return;
            }
            for (V neighbor : adjacencyList.get(current)) {
                if (!visited.get(neighbor)) {
                    q.add(neighbor);
                    visited.put(neighbor, true);
                    parent.put(neighbor, current);
                }
            }
        }
    }
    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices should be given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     * 
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable should include the
     * source and destination vertices.
     * 
     */
    public List<V> getPath(V from, V to)
    {
        if (isEmpty()) {
            return new ArrayList<V>();
        }
        Map<V, Boolean> visited = new HashMap<V, Boolean>();
        Map<V, V> parent = new HashMap<V, V>();
        for (V vertex: adjacencyList.keySet()) {
            visited.put(vertex, false);
            parent.put(vertex, null);
        }
        bfs(from, to, visited, parent);
        List<V> path = new ArrayList<V>();
        if (!visited.get(to)) {
            return path;
        }
        V current = to;
        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }
        return path;
    }
    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol> 
     * <li>If from = to, shortest path has length 0
     * <li>Otherwise, shortest path length is length of the shortest
     * possible path connecting from to to.  
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    public int pathLength(V from, V to)
    {
        if (hasPath(from, to))
        {
            return getPath(from, to).size()-1;
        }
        return Integer.MAX_VALUE;
        
    }
        /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff A and B are in the
     * graph and there exists a sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     * It therefore follows that, if vertex A is in the graph, there
     * is a path from A to A.
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    public boolean hasPath(V from, V to)
    {
        if (getPath(from, to).size() > 0)
        {
            return true;
        }
        return false;
    }
}
