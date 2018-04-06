import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.BestMatcher;
import util.Requestable;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by steve on 08/11/2016.
 */
public class BestMatcherTest {

    private BestMatcher collection;

    @Before
    public void init(){
        collection = new BestMatcher("test");
    }

    @After
    public void end(){
        collection = null;
    }

    @Test
    public void testEmptySize(){
        assertTrue(collection.isEmpty());
        assertTrue(collection.getBests().isEmpty());
        assertTrue(collection.getBest() == null);
        assertFalse(collection.isUnique());
    }

    @Test
    public void testOneElement(){
        Requestable requestable = new Requestable("test", "http://test.tst");
        collection.evaluate(requestable);
        assertTrue(collection.getBests().size() == 1);
        assertTrue(collection.getBest() == requestable);
        assertTrue(collection.isUnique());
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testDifferentElements(){
        Requestable foo = new Requestable("foo", "http://foo.tst");
        Requestable requestable = new Requestable("test", "http://test.tst");
        Requestable bar = new Requestable("bar", "http://bar.tst");

        collection.evaluate(foo);
        collection.evaluate(requestable);
        collection.evaluate(bar);

        assertTrue(collection.getBests().size() == 1);
        assertTrue(collection.getBest() == requestable);
        assertTrue(collection.isUnique());
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testSimilarElements(){
        Requestable test1 = new Requestable("test1", "http://test1.tst");
        Requestable test2 = new Requestable("test2", "http://test2.tst");
        Requestable test3 = new Requestable("test3", "http://test3.tst");

        collection.evaluate(test1);
        collection.evaluate(test2);
        collection.evaluate(test3);

        assertTrue(collection.getBests().size() == 3);
        assertTrue(collection.getBest() == null);
        assertFalse(collection.isUnique());
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testSimilarElementsWithExactOne(){
        Requestable test1 = new Requestable("test1", "http://test1.tst");
        Requestable test = new Requestable("test", "http://test.tst");
        Requestable test3 = new Requestable("test3", "http://test3.tst");

        collection.evaluate(test1);
        collection.evaluate(test);
        collection.evaluate(test3);

        assertTrue(collection.getBests().size() == 1);
        assertTrue(collection.getBest() == test);
        assertTrue(collection.isUnique());
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testSimilarElementsWithBadOne(){
        Requestable test1 = new Requestable("test1", "http://test1.tst");
        Requestable bad = new Requestable("bad", "http://bad.tst");
        Requestable test3 = new Requestable("test3", "http://test3.tst");

        collection.evaluate(test1);
        collection.evaluate(bad);
        collection.evaluate(test3);

        assertTrue(collection.getBests().size() == 2);
        assertTrue(collection.getBest() == null);
        assertFalse(collection.isUnique());
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testEvaluateAllEmpty(){
        collection.evaluateAll(new ArrayList<>());

        assertTrue(collection.getBests().size() == 0);
        assertTrue(collection.getBest() == null);
        assertFalse(collection.isUnique());
        assertTrue(collection.isEmpty());
    }

    @Test
    public void testEvaluateAll(){
        collection.evaluateAll(
                new Requestable("test1", "http://test1.tst"),
                new Requestable("tést2", "http://test2.tst"),
                new Requestable("têst tÉst3", "http://test3.tst"),
                new Requestable("TEST4", "http://test4.tst")
        );

        assertTrue(collection.getBests().size() == 4);
        assertTrue(collection.getBest() == null);
        assertFalse(collection.isUnique());
        assertFalse(collection.isEmpty());
    }
}
