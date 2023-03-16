package edu.union.adt.game.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses
({
    GameBoardTest.class,
    CoordinatePointTest.class, 
    BoardPainterShapeTest.class, 
    GameBoardSquareTest.class
})
public class GameTestSuite
{ // no implementation needed; above annotations do the work.
}
