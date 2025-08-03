import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import estructuras.conjuntistas.dinamicas.ArbolAVL;

public class ArbolAvlTest {

    private ArbolAVL arbolAVL;

    @BeforeEach
    void init() {
        arbolAVL = new ArbolAVL();
    }

    @Test
    @DisplayName("Insertar elemento dentro de un árbol AVL")
    void testInsertar() {
        assertTrue(arbolAVL.insertar(10));
        assertFalse(arbolAVL.insertar(10));
        assertTrue(arbolAVL.insertar(20));
        assertTrue(arbolAVL.insertar(5));
        assertTrue(arbolAVL.insertar(15));
    }

    @Test
    @DisplayName("Buscar elemento en un árbol AVL")
    void testBuscar() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);
        arbolAVL.insertar(5);
        arbolAVL.insertar(15);
        assertTrue(arbolAVL.pertenece(10));
        assertTrue(arbolAVL.pertenece(20));
        assertTrue(arbolAVL.pertenece(5));
        assertTrue(arbolAVL.pertenece(15));
        assertFalse(arbolAVL.pertenece(25));
    }

    @Test
    @DisplayName("Eliminar elemento de un árbol AVL")
    void testEliminar() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);

        assertTrue(arbolAVL.eliminar(10));
        assertFalse(arbolAVL.pertenece(10));
        assertTrue(arbolAVL.pertenece(20));
        assertFalse(arbolAVL.eliminar(30));
        assertTrue(arbolAVL.eliminar(20));
        assertFalse(arbolAVL.pertenece(20));
    }

    @Test
    @DisplayName("Verificar si el árbol AVL está vacío")
    void testEsVacio() {
        assertTrue(arbolAVL.esVacio());
        arbolAVL.insertar(10);
        assertFalse(arbolAVL.esVacio());
        arbolAVL.eliminar(10);
        assertTrue(arbolAVL.esVacio());
    }

    @Test
    @DisplayName("Verificar si un elemento pertenece al árbol")
    void testPertenece() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);
        arbolAVL.insertar(5);
        assertTrue(arbolAVL.pertenece(10));
        assertTrue(arbolAVL.pertenece(20));
        assertTrue(arbolAVL.pertenece(5));
        assertFalse(arbolAVL.pertenece(15));
    }

    @Test
    @DisplayName("Verificar obtener elemento de un árbol")
    void testObtener() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);
        arbolAVL.insertar(5);
        assertTrue(arbolAVL.obtener(10).equals(10));
        assertTrue(arbolAVL.obtener(20).equals(20));
        assertTrue(arbolAVL.obtener(5).equals(5));
        assertTrue(arbolAVL.obtener(15) == null);
    }

    @Test
    @DisplayName("Verificar elemento minimo del árbol")
    void testMinimo() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);
        arbolAVL.insertar(5);
        assertTrue(arbolAVL.minimoElem().equals(5));
        arbolAVL.eliminar(5);
        assertTrue(arbolAVL.minimoElem().equals(10));
        arbolAVL.eliminar(10);
        assertTrue(arbolAVL.minimoElem().equals(20));
    }

    @Test
    @DisplayName("Verificar elemento maximo del árbol")
    void testMaximo() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);
        arbolAVL.insertar(5);
        assertTrue(arbolAVL.maximoElem().equals(20));
        arbolAVL.eliminar(20);
        assertTrue(arbolAVL.maximoElem().equals(10));
        arbolAVL.eliminar(10);
        assertTrue(arbolAVL.maximoElem().equals(5));
    }

    @Test
    @DisplayName("Verificar vaciar árbol")
    void testVaciar() {
        arbolAVL.insertar(10);
        assertFalse(arbolAVL.esVacio());
        arbolAVL.vaciar();
        assertTrue(arbolAVL.esVacio());

    }

    @Test
    @DisplayName("Verificar estructura del árbol con toString")
    void testEstructuraBasica() {
        arbolAVL.insertar(10);
        String esperado = "10(alt: 0) HI: - HD: -\n";
        assertEquals(esperado, arbolAVL.toString());

        arbolAVL.insertar(5);
        arbolAVL.insertar(15);
        String esperadoConHijos = "10(alt: 1) HI: 5 HD: 15\n" +
                "5(alt: 0) HI: - HD: -\n" +
                "15(alt: 0) HI: - HD: -\n";
        assertEquals(esperadoConHijos, arbolAVL.toString());
    }

    @Test
    @DisplayName("Verificar rotación derecha en inserción secuencial descendente")
    void testRotacionDerecha() {

        arbolAVL.insertar(3);
        arbolAVL.insertar(2);
        arbolAVL.insertar(1);

        String estructuraEsperada = "2(alt: 1) HI: 1 HD: 3\n" +
                "1(alt: 0) HI: - HD: -\n" +
                "3(alt: 0) HI: - HD: -\n";

        assertEquals(estructuraEsperada, arbolAVL.toString());
    }

    @Test
    @DisplayName("Verificar rotación izquierda en inserción secuencial ascendente")
    void testRotacionIzquierda() {

        arbolAVL.insertar(1);
        arbolAVL.insertar(2);
        arbolAVL.insertar(3);

        String estructuraEsperada = "2(alt: 1) HI: 1 HD: 3\n" +
                "1(alt: 0) HI: - HD: -\n" +
                "3(alt: 0) HI: - HD: -\n";

        assertEquals(estructuraEsperada, arbolAVL.toString());
    }

    @Test
    @DisplayName("Verificar rotación doble izquierda-derecha")
    void testRotacionDobleIzquierdaDerecha() {

        arbolAVL.insertar(3);
        arbolAVL.insertar(1);
        arbolAVL.insertar(2);

        String estructuraEsperada = "2(alt: 1) HI: 1 HD: 3\n" +
                "1(alt: 0) HI: - HD: -\n" +
                "3(alt: 0) HI: - HD: -\n";

        assertEquals(estructuraEsperada, arbolAVL.toString());
    }

    @Test
    @DisplayName("Verificar que las alturas son correctas")
    void testAlturasCorrectas() {
        arbolAVL.insertar(50);
        arbolAVL.insertar(30);
        arbolAVL.insertar(70);
        arbolAVL.insertar(20);
        arbolAVL.insertar(40);
        arbolAVL.insertar(60);
        arbolAVL.insertar(80);

        String estructura = arbolAVL.toString();

        assertTrue(estructura.contains("50(alt: 2)"));

        assertTrue(estructura.contains("20(alt: 0)"));
        assertTrue(estructura.contains("40(alt: 0)"));
    }

    @Test
    @DisplayName("Verificar clone de árbol avl")
    void testClone() {
        arbolAVL.insertar(10);
        arbolAVL.insertar(20);
        arbolAVL.insertar(5);

        ArbolAVL clone = arbolAVL.clone();

        assertEquals(arbolAVL.toString(), clone.toString());
    }
}
