package edu.union.adt.graph.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import edu.union.adt.graph.tests.bilakhim.ExtendedAPIGraphTests;

@RunWith(Suite.class)
@Suite.SuiteClasses
({
    SimpleGraphTests.class,
    GraphTestsUsingEquals.class,
    ExtendedAPIGraphTests.class
})
public class GraphTestSuite
{ // no implementation needed; above annotations do the work.
}
