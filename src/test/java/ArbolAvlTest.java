import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("Verificar si un elemento pertenece al arbol")
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
    @DisplayName("Verificar obtener elemento de un arbol")
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
    @DisplayName("Verificar elemento minimo del arbol")
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
    @DisplayName("Verificar elemento maximo del arbol")
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
    @DisplayName("Verificar vaciar arbol")
    void testVaciar() {
        arbolAVL.insertar(10);
        assertFalse(arbolAVL.esVacio());
        arbolAVL.vaciar();
        assertTrue(arbolAVL.esVacio());

    }
}
