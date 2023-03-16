package edu.union.adt.graph.tests.bilakhim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.union.adt.graph.Graph;
import edu.union.adt.graph.GraphFactory;

@RunWith(JUnit4.class)
public class ExtendedAPIGraphTests
{
    private Graph<String> g;
    private Graph<String> g2;
    private Graph<Object> objectGraph;

    @Before
    public void setUp()
    {
        g = GraphFactory.<String>createGraph();
        g2 = GraphFactory.<String>createGraph();
        objectGraph = GraphFactory.<Object>createGraph();
    }
    
    @Test
    public void checkEmpty()
    {
        assertTrue("Empty graph is empty", g.isEmpty());
        assertTrue("Empty graph is empty", g2.isEmpty());
        assertTrue("Empty graph is empty", objectGraph.isEmpty());

        g.addVertex("Foo");
        assertFalse("Non-empty graph is not empty", g.isEmpty());
        g.removeVertex("Foo");
        assertTrue("Empty graph is empty", g.isEmpty());

        g2.addVertex("Foo");
        assertFalse("Non-empty graph is not empty", g2.isEmpty());
        g2.removeVertex("Foo");
        assertTrue("Empty graph is empty", g2.isEmpty());

        objectGraph.addVertex("Foo");
        assertFalse("Non-empty graph is not empty", objectGraph.isEmpty());
        objectGraph.removeVertex("Foo");
        assertTrue("Empty graph is empty", objectGraph.isEmpty());

    }

    @Test
    public void checkEdges()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addEdge("Foo", "Bar");
        assertTrue("Graph contains edge", g.hasPath("Foo", "Bar"));
        g.removeEdge("Foo", "Bar");
        assertFalse("Graph does not contain edge", g.hasPath("Foo", "Bar"));

        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addEdge("Foo", "Bar");
        assertTrue("Graph contains edge", g2.hasPath("Foo", "Bar"));
        g2.removeEdge("Foo", "Bar");
        assertFalse("Graph does not contain edge", g2.hasPath("Foo", "Bar"));

        objectGraph.addVertex("Foo");
        objectGraph.addVertex("Bar");
        objectGraph.addEdge("Foo", "Bar");
        assertTrue("Graph contains edge", objectGraph.hasPath("Foo", "Bar"));
        objectGraph.removeEdge("Foo", "Bar");
        assertFalse("Graph does not contain edge", objectGraph.hasPath("Foo", "Bar"));
    }

    @Test

    public void checkHasPath()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addVertex("Baz");
        g.addVertex("Qux");
        g.addEdge("Foo", "Bar");
        g.addEdge("Bar", "Baz");
        g.addEdge("Baz", "Qux");
        assertTrue("Graph contains path", g.hasPath("Foo", "Qux"));
        assertFalse("Graph does not contain path", g.hasPath("Qux", "Foo"));
        g.removeEdge("Baz", "Qux");
        assertFalse("Graph does not contain path", g.hasPath("Foo", "Qux"));

        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addVertex("Baz");
        g2.addVertex("Qux");
        g2.addEdge("Foo", "Bar");
        g2.addEdge("Bar", "Baz");
        g2.addEdge("Baz", "Qux");
        assertTrue("Graph contains path", g2.hasPath("Foo", "Qux"));
        assertFalse("Graph does not contain path", g2.hasPath("Qux", "Foo"));
        g2.removeEdge("Baz", "Qux");
        assertFalse("Graph does not contain path", g2.hasPath("Foo", "Qux"));

        objectGraph.addVertex("Foo");
        objectGraph.addVertex("Bar");
        objectGraph.addVertex("Baz");
        objectGraph.addVertex("Qux");
        objectGraph.addEdge("Foo", "Bar");
        objectGraph.addEdge("Bar", "Baz");
        objectGraph.addEdge("Baz", "Qux");
        assertTrue("Graph contains path", objectGraph.hasPath("Foo", "Qux"));
        assertFalse("Graph does not contain path", objectGraph.hasPath("Qux", "Foo"));
        objectGraph.removeEdge("Baz", "Qux");
        assertFalse("Graph does not contain path", objectGraph.hasPath("Foo", "Qux"));
    }

    @Test
    public void checkPathLength()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addVertex("Baz");
        g.addVertex("Qux");
        g.addVertex("Quux");
        g.addEdge("Foo", "Bar");
        g.addEdge("Bar", "Baz");
        g.addEdge("Baz", "Qux");
        g.addEdge("Qux", "Quux");
        g.addEdge("Bar", "Quux");

        assertEquals("Path length is correct", 3, g.pathLength("Foo", "Qux"));
        assertEquals("Path length is correct", 0, g.pathLength("Foo", "Foo"));
        assertEquals("Path length is correct", 2, g.pathLength("Foo", "Quux"));
        g.removeEdge("Foo", "Bar");

        assertEquals("Path length is correct", Integer.MAX_VALUE, g.pathLength("Foo", "Qux"));

        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addVertex("Baz");
        g2.addVertex("Qux");
        g2.addVertex("Quux");
        g2.addEdge("Foo", "Bar");
        g2.addEdge("Bar", "Baz");
        g2.addEdge("Baz", "Qux");
        g2.addEdge("Qux", "Quux");
        g2.addEdge("Bar", "Quux");

        assertEquals("Path length is correct", 3, g2.pathLength("Foo", "Qux"));
        assertEquals("Path length is correct", 0, g2.pathLength("Foo", "Foo"));
        assertEquals("Path length is correct", 2, g2.pathLength("Foo", "Quux"));

        g2.removeEdge("Foo", "Bar");

        assertEquals("Path length is correct", Integer.MAX_VALUE, g2.pathLength("Foo", "Qux"));


        objectGraph.addVertex("Foo");
        objectGraph.addVertex("Bar");
        objectGraph.addVertex("Baz");
        objectGraph.addVertex("Qux");
        objectGraph.addVertex("Quux");
        objectGraph.addEdge("Foo", "Bar");
        objectGraph.addEdge("Bar", "Baz");
        objectGraph.addEdge("Baz", "Qux");
        objectGraph.addEdge("Qux", "Quux");
        objectGraph.addEdge("Bar", "Quux");

        assertEquals("Path length is correct", 3, objectGraph.pathLength("Foo", "Qux"));
        assertEquals("Path length is correct", 0, objectGraph.pathLength("Foo", "Foo"));
        assertEquals("Path length is correct", 2, objectGraph.pathLength("Foo", "Quux"));

        objectGraph.removeEdge("Foo", "Bar");
        
        assertEquals("Path length is correct", Integer.MAX_VALUE, objectGraph.pathLength("Foo", "Qux"));
    }
    @Test
    public void GetPathEdgeCase()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addVertex("Baz");
        g.addVertex("Qux");
        g.addVertex("Quux");
        g.addEdge("Foo", "Bar");
        g.addEdge("Bar", "Baz");
        g.addEdge("Baz", "Qux");
        g.addEdge("Qux", "Quux");
        g.addEdge("Bar", "Quux");

        g.removeEdge("Bar", "Baz");
        List<String>path = (List<String>) g.getPath("Foo", "Qux");
        assertTrue("path doesnt exist",path.isEmpty());
        g.removeEdge("Baz", "Qux");
        path = (List<String>) g.getPath("Foo", "Qux");
        assertTrue("path doesnt exist",path.isEmpty());

        g.addEdge("Quux", "Foo");
        g.addEdge("Bar", "Foo");
        path = (List<String>) g.getPath("Foo", "Quux");
        assertEquals("Path is correct", "Foo", path.get(0));
        assertEquals("Path is correct", "Bar", path.get(1));
        assertEquals("Path is correct", "Quux", path.get(2));

        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addVertex("Baz");
        g2.addVertex("Qux");
        g2.addVertex("Quux");
        g2.addEdge("Foo", "Bar");
        g2.addEdge("Bar", "Baz");
        g2.addEdge("Baz", "Qux");
        g2.addEdge("Qux", "Quux");
        g2.addEdge("Bar", "Quux");
        
        g2.removeEdge("Bar", "Baz");
        List<String>path2 = (List<String>) g.getPath("Foo", "Qux");
        assertTrue("path doesnt exist",path2.isEmpty());
        g2.removeEdge("Baz", "Qux");
        path2 = (List<String>) g.getPath("Foo", "Qux");
        assertTrue("path doesnt exist",path2.isEmpty());

        g2.addEdge("Quux", "Foo");
        g2.addEdge("Bar", "Foo");
        path2 = (List<String>) g.getPath("Foo", "Quux");
        assertEquals("Path is correct", "Foo", path.get(0));
        assertEquals("Path is correct", "Bar", path.get(1));
        assertEquals("Path is correct", "Quux", path.get(2));

        assertFalse("path doesnt exist",objectGraph.hasPath("Foo", "Qux"));
        assertEquals("Path doesnt exist", Integer.MAX_VALUE, objectGraph.pathLength("Foo", "Qux"));

        objectGraph.addVertex("Foo");
        objectGraph.addVertex("Bar");
        objectGraph.addVertex("Baz");
        objectGraph.addVertex("Qux");
        objectGraph.addVertex("Quux");
        objectGraph.addEdge("Foo", "Bar");
        objectGraph.addEdge("Bar", "Baz");
        objectGraph.addEdge("Baz", "Qux");
        objectGraph.addEdge("Qux", "Quux");
        objectGraph.addEdge("Bar", "Quux");
        
        objectGraph.removeEdge("Bar", "Baz");
        List<Object>path3 = (List<Object>) objectGraph.getPath("Foo", "Qux");
        assertTrue("path doesnt exist",path3.isEmpty());
        assertFalse("path doesnt exist",objectGraph.hasPath("Foo", "Qux"));
        assertEquals("Path doesnt exist", Integer.MAX_VALUE, objectGraph.pathLength("Foo", "Qux"));

        objectGraph.removeEdge("Baz", "Qux");
        path3 = (List<Object>) objectGraph.getPath("Foo", "Qux");
        assertTrue("path doesnt exist",path3.isEmpty());

        objectGraph.addEdge("Quux", "Foo");
        objectGraph.addEdge("Bar", "Foo");
        path3 = (List<Object>) objectGraph.getPath("Foo", "Quux");
        assertEquals("Path is correct", "Foo", path.get(0));
        assertEquals("Path is correct", "Bar", path.get(1));
        assertEquals("Path is correct", "Quux", path.get(2));

    }

    @Test
    public void getOnlyPath()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addVertex("Baz");
        g.addVertex("Qux");
        g.addVertex("Quux");
        g.addEdge("Foo", "Bar");
        g.addEdge("Bar", "Baz");
        g.addEdge("Baz", "Qux");
        g.addEdge("Qux", "Quux");
        g.addEdge("Bar", "Quux");

        List<String> path = (List<String>) g.getPath("Foo", "Qux");
        assertEquals("Path is correct", "Foo", path.get(0));
        assertEquals("Path is correct", "Bar", path.get(1));
        assertEquals("Path is correct", "Baz", path.get(2));
        assertEquals("Path is correct", "Qux", path.get(3));
        path = (List<String>) g.getPath("Foo", "Foo");
        assertEquals("Path is correct", "Foo", path.get(0));
        path = (List<String>) g.getPath("Foo", "Quux");
        assertEquals("Path is correct", "Foo", path.get(0));
        assertEquals("Path is correct", "Bar", path.get(1));
        assertEquals("Path is correct", "Quux", path.get(2));
        

        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addVertex("Baz");
        g2.addVertex("Qux");
        g2.addVertex("Quux");
        g2.addEdge("Foo", "Bar");
        g2.addEdge("Bar", "Baz");
        g2.addEdge("Baz", "Qux");
        g2.addEdge("Qux", "Quux");
        g2.addEdge("Bar", "Quux");

        List<String> path2 = (List<String>) g2.getPath("Foo", "Qux");
        assertEquals("Path is correct", "Foo", path2.get(0));
        assertEquals("Path is correct", "Bar", path2.get(1));
        assertEquals("Path is correct", "Baz", path2.get(2));
        assertEquals("Path is correct", "Qux", path2.get(3));
        path2 = (List<String>) g2.getPath("Foo", "Foo");
        assertEquals("Path is correct", "Foo", path2.get(0));
        path2 = (List<String>) g2.getPath("Foo", "Quux");
        assertEquals("Path is correct", "Foo", path2.get(0));
        assertEquals("Path is correct", "Bar", path2.get(1));
        assertEquals("Path is correct", "Quux", path2.get(2));


        objectGraph.addVertex("Foo");
        objectGraph.addVertex("Bar");
        objectGraph.addVertex("Baz");
        objectGraph.addVertex("Qux");
        objectGraph.addVertex("Quux");
        objectGraph.addEdge("Foo", "Bar");
        objectGraph.addEdge("Bar", "Baz");
        objectGraph.addEdge("Baz", "Qux");
        objectGraph.addEdge("Qux", "Quux");
        objectGraph.addEdge("Bar", "Quux");

        List<Object> path3 = (List<Object>) objectGraph.getPath("Foo", "Qux");
        assertEquals("Path is correct", "Foo", path3.get(0));
        assertEquals("Path is correct", "Bar", path3.get(1));
        assertEquals("Path is correct", "Baz", path3.get(2));
        assertEquals("Path is correct", "Qux", path3.get(3));
        path3 = (List<Object>) objectGraph.getPath("Foo", "Foo");
        assertEquals("Path is correct", "Foo", path3.get(0));
        path3 = (List<Object>) objectGraph.getPath("Foo", "Quux");
        assertEquals("Path is correct", "Foo", path3.get(0));
        assertEquals("Path is correct", "Bar", path3.get(1));
        assertEquals("Path is correct", "Quux", path3.get(2));
    }


    @Test
    public void TestRemoveVertex()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.removeVertex("Foo");
        assertFalse("Removing a vertex from the graph graph",
                   g.contains("Foo"));
        assertEquals("Removing a vertex decreases vertex count",
                     1, g.numVertices());
        g.removeVertex("Foo");
        assertEquals("Removing a non-existent vertex doesn't change vertex count",
                     1, g.numVertices());

    }

    @Test
    public void TestRemoveEdge()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addEdge("Foo", "Bar");
        assertTrue("Adding an edge causes it to exist in the graph",
                   g.hasEdge("Foo", "Bar"));
        g.removeEdge("Foo", "Bar");
        assertFalse("After we remove an edge, it's not there",
                    g.hasEdge("Foo", "Bar"));
        assertEquals("Removing an edge decreases edge count",
                     0, g.numEdges());
        g.removeEdge("Foo", "Bar");
        assertEquals("Removing a non-existent edge doesn't change edge count",
                     0, g.numEdges());
    }
    @Test
    public void TestRemoveVertexUsingEquals()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addEdge("Foo", "Bar");
        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addEdge("Foo", "Bar");
        assertEquals("Adding the same vertexes and edges produces two same graphs",g2, g);
        g.removeVertex("Foo");
        g2.removeVertex("Foo");
        assertEquals("Removing the same vertex produces two same graphs",g2, g);
    }

    @Test
    public void TestRemoveEdgeUsingEquals()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addEdge("Foo", "Bar");


        g2.addVertex("Foo");
        g2.addVertex("Bar");
        g2.addEdge("Foo", "Bar");
        assertEquals("Adding the same vertexes and edges produces two same graphs",g2, g);
        g.removeEdge("Foo", "Bar");
        g2.removeEdge("Foo", "Bar");
        assertEquals("Removing the same edge produces two same graphs",g2, g);
    }
}

