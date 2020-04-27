/**
 * @author Julio Herrera
 * @author Daniela Batz
 * 
 * Prueba unitaria de la clase SplayTree
 */
import static org.junit.Assert.*;
import org.junit.Test;

public class SplayTreeTest {

    /**
     * Con esta prueba verificaremos que jUnit este funcionando
     */
	@Test
    public void pruebaJUnit() {
        assertTrue(true);
    }

    /**
     * Prueba del insercion
     */
    @Test
    public void putTest() {
        SplayTree<Integer, String> st = new SplayTree<>();
        st.put(Integer.valueOf(8), "ocho");
        st.put(Integer.valueOf(3), "tres");
        st.put(Integer.valueOf(1), "uno");
        assertEquals(st.size(), 3);
    }
    
    /**
     * Prueba para saber si existe un elemento por llave
     */
    @Test
    public void containsKey() {
        SplayTree<Integer, String> st = new SplayTree<>();
        st.put(Integer.valueOf(8), "ocho");
        st.put(Integer.valueOf(3), "tres");
        st.put(Integer.valueOf(1), "uno");
        assertTrue(st.containsKey(3));
    }
    
    /**
     * Prueba para obtener un elemento
     */
    @Test
    public void get() {
        SplayTree<Integer, String> st = new SplayTree<>();
        st.put(Integer.valueOf(8), "ocho");
        st.put(Integer.valueOf(3), "tres");
        st.put(Integer.valueOf(1), "uno");
        assertEquals(st.get(3), "tres");
    }

}
