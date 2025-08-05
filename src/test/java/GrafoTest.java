import estructuras.grafos.Grafo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GrafoTest {

    @Test
    public void testVacio() {
        Grafo grafo = new Grafo();
        assertFalse(grafo.eliminarVertice(1));
        assertFalse(grafo.insertarArco(1,2,100));
        assertFalse(grafo.eliminarArco(1,2));
        assertFalse(grafo.existeVertice(1));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeCamino(1,2));
        assertTrue(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[]");
        assertEquals(grafo.listarEnAnchura().toString(), "[]");
        assertEquals(grafo.toString(), "El grafo esta vacio");
    }

    @Test
    public void testConUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertFalse(grafo.insertarArco(1,2,100));
        assertFalse(grafo.insertarArco(1,1,100));
        assertFalse(grafo.eliminarArco(1,2));
        assertFalse(grafo.eliminarArco(1,1));
        assertTrue(grafo.existeVertice(1));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(1,1));
        assertFalse(grafo.existeCamino(1,2));
        assertFalse(grafo.existeCamino(1,1));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasCorto(1,1).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,1).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1]");
        assertEquals(grafo.toString(), "1 -> \n");
    }

    @Test
    public void testConDosVertices() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertFalse(grafo.insertarArco(1,3,100));
        assertFalse(grafo.insertarArco(2,3,100));
        assertFalse(grafo.insertarArco(1,1,100));
        assertFalse(grafo.eliminarArco(1,2));
        assertFalse(grafo.eliminarArco(2,1));
        assertFalse(grafo.eliminarArco(1,1));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(1,1));
        assertFalse(grafo.existeCamino(1,2));
        assertFalse(grafo.existeCamino(2,1));
        assertFalse(grafo.existeCamino(1,1));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasCorto(2,1).toString(), "[]");
        assertEquals(grafo.caminoMasCorto(1,1).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(2,1).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,1).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2]");
        assertEquals(grafo.toString(), "1 -> \n2 -> \n");
    }

    @Test
    public void testConDosVerticesYUnArco() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarArco(1,2,100));
        assertFalse(grafo.insertarArco(1,2,100));
        assertFalse(grafo.insertarArco(1,1,100));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(1,1));
        assertTrue(grafo.existeCamino(1,2));
        assertFalse(grafo.existeCamino(2,1));
        assertFalse(grafo.existeCamino(1,1));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasCorto(2,1).toString(), "[]");
        assertEquals(grafo.caminoMasCorto(1,1).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasLargo(2,1).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,1).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2]");
        assertEquals(grafo.toString(), "1 -> 2 (100)\n2 -> \n");
    }

    @Test
    public void testConDosVerticesYDosArcos() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertFalse(grafo.insertarArco(2,1,100));
        assertFalse(grafo.insertarArco(1,1,100));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(1,1));
        assertTrue(grafo.existeCamino(1,2));
        assertTrue(grafo.existeCamino(2,1));
        assertFalse(grafo.existeCamino(1,1));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasCorto(2,1).toString(), "[2,1]");
        assertEquals(grafo.caminoMasCorto(1,1).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasLargo(2,1).toString(), "[2,1]");
        assertEquals(grafo.caminoMasLargo(1,1).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2]");
        assertEquals(grafo.toString(), "1 -> 2 (100)\n2 -> 1 (100)\n");
    }

    @Test
    public void testConCuatroVerticesYDosArcos() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeVertice(3));
        assertTrue(grafo.existeVertice(4));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(3,4));
        assertTrue(grafo.existeCamino(1,2));
        assertTrue(grafo.existeCamino(3,4));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasCorto(3,4).toString(), "[3,4]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasLargo(3,4).toString(), "[3,4]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2,3,4]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2,3,4]");
        assertEquals(grafo.toString(), "1 -> 2 (100)\n2 -> \n3 -> 4 (100)\n4 -> \n");
    }

   @Test
    public void testConCuatroVerticesYCuatroArcos() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeVertice(3));
        assertTrue(grafo.existeVertice(4));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertTrue(grafo.existeArco(2,4));
        assertTrue(grafo.existeArco(3,4));
        assertTrue(grafo.existeCamino(1,2));
        assertTrue(grafo.existeCamino(1,3));
        assertTrue(grafo.existeCamino(2,4));
        assertTrue(grafo.existeCamino(3,4));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasCorto(1,3).toString(), "[1,3]");
        assertEquals(grafo.caminoMasLargo(2,4).toString(), "[2,4]");
        assertEquals(grafo.caminoMasLargo(3,4).toString(), "[3,4]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2,4,3]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2,3,4]");
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (100)\n2 -> 4 (100)\n3 -> 4 (100)\n4 -> \n");
    }

    @Test
    public void testEliminarElUnicoVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.eliminarVertice(1));
        assertFalse(grafo.insertarArco(1,2,100));
        assertFalse(grafo.insertarArco(1,1,100));
        assertFalse(grafo.eliminarArco(1,2));
        assertFalse(grafo.eliminarArco(1,1));
        assertFalse(grafo.existeVertice(1));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(1,1));
        assertFalse(grafo.existeCamino(1,2));
        assertFalse(grafo.existeCamino(1,1));
        assertTrue(grafo.esVacio());
        assertEquals(grafo.toString(), "El grafo esta vacio");
    }

    @Test
    public void testEliminarElPrimerVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarVertice(1));
        assertFalse(grafo.existeVertice(1));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(2,1));
        assertTrue(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "2 -> \n3 -> 2 (100)\n");
    }

    @Test
    public void testEliminarUnVerticeDelMedioDeLaLista() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarVertice(2));
        assertFalse(grafo.existeVertice(2));
        assertFalse(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "1 -> 3 (100)\n3 -> \n");
    }

    @Test
    public void testEliminarElUltimoVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarVertice(3));
        assertFalse(grafo.existeVertice(3));
        assertTrue(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(1,3));
        assertTrue(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "1 -> 2 (100)\n2 -> 1 (100)\n");
    }

    @Test
    public void testEliminarElUnicoArcoDeUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarArco(2,1));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(2,1));
        assertTrue(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (100)\n2 -> \n3 -> 2 (100)\n");
    }

@Test
    public void testEliminarElPrimerArcoDeUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(2,3,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarArco(2,1));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(2,1));
        assertTrue(grafo.existeArco(2,4));
        assertTrue(grafo.existeArco(2,3));
        assertTrue(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (100)\n2 -> 4 (100), 3 (100)\n3 -> 2 (100)\n4 -> \n");
    }

    @Test
    public void testEliminarUnArcoDelMedioDeLaListaDeUnVerticec() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(2,3,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarArco(2,4));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertTrue(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(2,4));
        assertTrue(grafo.existeArco(2,3));
        assertTrue(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (100)\n2 -> 1 (100), 3 (100)\n3 -> 2 (100)\n4 -> \n");
    }

    @Test
    public void testEliminarElUltimoArcoDeUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(2,3,100));
        assertTrue(grafo.insertarArco(3,2,100));
        assertTrue(grafo.eliminarArco(2,3));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertTrue(grafo.existeArco(2,1));
        assertTrue(grafo.existeArco(2,4));
        assertFalse(grafo.existeArco(2,3));
        assertTrue(grafo.existeArco(3,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (100)\n2 -> 1 (100), 4 (100)\n3 -> 2 (100)\n4 -> \n");
    }

    @Test
    public void testModificarEtiquetaPrimerArcoDeUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(1,4,100));
        assertTrue(grafo.modificarEtiqueta(1,2, 200));
        assertEquals(grafo.toString(), "1 -> 2 (200), 3 (100), 4 (100)\n2 -> \n3 -> \n4 -> \n");
    }

    @Test
    public void testModificarEtiquetaDeUnArcoDelMedioDeLaListaDeUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(1,4,100));
        assertTrue(grafo.modificarEtiqueta(1,3, 200));
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (200), 4 (100)\n2 -> \n3 -> \n4 -> \n");
    }

    @Test
    public void testModificarEtiquetaDelUltimoArcoDeUnVertice() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(1,4,100));
        assertTrue(grafo.modificarEtiqueta(1,4, 200));
        assertEquals(grafo.toString(), "1 -> 2 (100), 3 (100), 4 (200)\n2 -> \n3 -> \n4 -> \n");
    }

    @Test
    public void testExisteArco() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertFalse(grafo.existeArco(1,1));
        assertTrue(grafo.existeArco(1,2));
        assertTrue(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(1,4));
        assertFalse(grafo.existeArco(2,1));
        assertFalse(grafo.existeArco(2,2));
        assertFalse(grafo.existeArco(2,3));
        assertTrue(grafo.existeArco(2,4));
        assertFalse(grafo.existeArco(3,1));
        assertFalse(grafo.existeArco(3,2));
        assertFalse(grafo.existeArco(3,3));
        assertTrue(grafo.existeArco(3,4));
        assertFalse(grafo.existeArco(4,1));
        assertFalse(grafo.existeArco(4,2));
        assertFalse(grafo.existeArco(4,3));
        assertFalse(grafo.existeArco(4,4));
    }

    @Test
    public void testExisteCamino() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertFalse(grafo.existeCamino(1,1));
        assertTrue(grafo.existeCamino(1,2));
        assertTrue(grafo.existeCamino(1,3));
        assertTrue(grafo.existeCamino(1,4));
        assertFalse(grafo.existeCamino(2,1));
        assertFalse(grafo.existeCamino(2,2));
        assertFalse(grafo.existeCamino(2,3));
        assertTrue(grafo.existeCamino(2,4));
        assertFalse(grafo.existeCamino(3,1));
        assertFalse(grafo.existeCamino(3,2));
        assertFalse(grafo.existeCamino(3,3));
        assertTrue(grafo.existeCamino(3,4));
        assertFalse(grafo.existeCamino(4,1));
        assertFalse(grafo.existeCamino(4,2));
        assertFalse(grafo.existeCamino(4,3));
        assertFalse(grafo.existeCamino(4,4));
    }

    @Test
    public void testCaminoMasCorto() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarVertice(5));
        assertTrue(grafo.insertarVertice(6));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,5,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertTrue(grafo.insertarArco(5,4,100));
        assertTrue(grafo.insertarArco(5,6,100));
        assertTrue(grafo.insertarArco(6,1,100));
        assertEquals(grafo.caminoMasCorto(1, 2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasCorto(1, 4).toString(), "[1,3,4]");
        assertEquals(grafo.caminoMasCorto(3, 6).toString(), "[]");
    }

    @Test
    public void testCaminoMasLargo() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarVertice(5));
        assertTrue(grafo.insertarVertice(6));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,5,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertTrue(grafo.insertarArco(5,4,100));
        assertTrue(grafo.insertarArco(5,6,100));
        assertTrue(grafo.insertarArco(6,1,100));
        assertEquals(grafo.caminoMasLargo(1, 2).toString(), "[1,2]");
        assertEquals(grafo.caminoMasLargo(1, 4).toString(), "[1,2,5,4]");
        assertEquals(grafo.caminoMasLargo(3, 6).toString(), "[]");
    }

    @Test
    public void testListarEnProfundidad() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarVertice(5));
        assertTrue(grafo.insertarVertice(6));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,5,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertTrue(grafo.insertarArco(5,4,100));
        assertTrue(grafo.insertarArco(5,6,100));
        assertTrue(grafo.insertarArco(6,1,100));
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2,5,4,6,3]");
    }

    @Test
    public void testListarEnAnchura() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarVertice(5));
        assertTrue(grafo.insertarVertice(6));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,5,100));
        assertTrue(grafo.insertarArco(3,4,100));
        assertTrue(grafo.insertarArco(5,4,100));
        assertTrue(grafo.insertarArco(5,6,100));
        assertTrue(grafo.insertarArco(6,1,100));
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2,3,5,4,6]");
    }

    @Test
    public void testCaminoCaudalPlenoMinimo() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarVertice(5));
        assertTrue(grafo.insertarVertice(6));
        assertTrue(grafo.insertarArco(1,2,140));
        assertTrue(grafo.insertarArco(1,3,300));
        assertTrue(grafo.insertarArco(2,5,80));
        assertTrue(grafo.insertarArco(3,4,60));
        assertTrue(grafo.insertarArco(5,4,100));
        assertTrue(grafo.insertarArco(5,6,160));
        assertTrue(grafo.insertarArco(6,1,140));
        assertEquals(grafo.caminoCaudalPlenoMinimo(1,4).toString(), "[1,3,4]");
        assertEquals(grafo.caminoCaudalPlenoMinimo(3,6).toString(), "[]");
    }

    @Test
    public void testVaciar() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarVertice(4));
        assertTrue(grafo.insertarArco(1,2,100));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,4,100));
        assertTrue(grafo.insertarArco(3,4,100));
        grafo.vaciar();
        assertFalse(grafo.existeVertice(1));
        assertFalse(grafo.existeVertice(2));
        assertFalse(grafo.existeVertice(3));
        assertFalse(grafo.existeVertice(4));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(2,4));
        assertFalse(grafo.existeArco(3,4));
        assertFalse(grafo.existeCamino(1,2));
        assertFalse(grafo.existeCamino(1,3));
        assertFalse(grafo.existeCamino(2,4));
        assertFalse(grafo.existeCamino(3,4));
        assertTrue(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasCorto(1,3).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(2,4).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(3,4).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[]");
        assertEquals(grafo.listarEnAnchura().toString(), "[]");
        assertEquals(grafo.toString(), "El grafo esta vacio");
    }

    @Test
    public void testClonVacio() {
        Grafo grafo = new Grafo();
        Grafo grafoClon = grafo.clone();
        assertFalse(grafo.eliminarVertice(1));
        assertFalse(grafo.insertarArco(1,2,100));
        assertFalse(grafo.eliminarArco(1,2));
        assertFalse(grafo.existeVertice(1));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeCamino(1,2));
        assertTrue(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[]");
        assertEquals(grafo.listarEnAnchura().toString(), "[]");
        assertEquals(grafo.toString(), "El grafo esta vacio");
        assertFalse(grafoClon.eliminarVertice(1));
        assertFalse(grafoClon.insertarArco(1,2,100));
        assertFalse(grafoClon.eliminarArco(1,2));
        assertFalse(grafoClon.existeVertice(1));
        assertFalse(grafoClon.existeArco(1,2));
        assertFalse(grafoClon.existeCamino(1,2));
        assertTrue(grafoClon.esVacio());
        assertEquals(grafoClon.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafoClon.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafoClon.listarEnProfundidad().toString(), "[]");
        assertEquals(grafoClon.listarEnAnchura().toString(), "[]");
        assertEquals(grafoClon.toString(), "El grafo esta vacio");
    }

    @Test
    public void testClonConTresVertices() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        Grafo grafoClon = grafo.clone();
        assertFalse(grafo.eliminarArco(1,2));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeVertice(3));
        assertFalse(grafo.existeArco(1,2));
        assertFalse(grafo.existeCamino(1,2));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafo.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,2,3]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,2,3]");
        assertEquals(grafo.toString(), "1 -> \n2 -> \n3 -> \n");
        assertFalse(grafoClon.eliminarArco(1,2));
        assertTrue(grafoClon.existeVertice(1));
        assertTrue(grafoClon.existeVertice(2));
        assertTrue(grafoClon.existeVertice(3));
        assertFalse(grafoClon.existeArco(1,2));
        assertFalse(grafoClon.existeCamino(1,2));
        assertFalse(grafoClon.esVacio());
        assertEquals(grafoClon.caminoMasCorto(1,2).toString(), "[]");
        assertEquals(grafoClon.caminoMasLargo(1,2).toString(), "[]");
        assertEquals(grafoClon.listarEnProfundidad().toString(), "[1,2,3]");
        assertEquals(grafoClon.listarEnAnchura().toString(), "[1,2,3]");
        assertEquals(grafoClon.toString(), "1 -> \n2 -> \n3 -> \n");
    }

    @Test
    public void testClonConTresVerticesYArcos() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        Grafo grafoClon = grafo.clone();
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeVertice(3));
        assertTrue(grafo.existeArco(1,3));
        assertTrue(grafo.existeArco(2,3));
        assertTrue(grafo.existeArco(2,1));
        assertTrue(grafo.existeCamino(1,3));
        assertTrue(grafo.existeCamino(2,3));
        assertTrue(grafo.existeCamino(2,1));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(2,3).toString(), "[2,3]");
        assertEquals(grafo.caminoMasLargo(2,3).toString(), "[2,1,3]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,3,2]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,3,2]");
        assertEquals(grafo.toString(), "1 -> 3 (100)\n2 -> 3 (100), 1 (100)\n3 -> \n");
        assertTrue(grafoClon.existeVertice(1));
        assertTrue(grafoClon.existeVertice(2));
        assertTrue(grafoClon.existeVertice(3));
        assertTrue(grafoClon.existeArco(1,3));
        assertTrue(grafoClon.existeArco(2,3));
        assertTrue(grafoClon.existeArco(2,1));
        assertTrue(grafoClon.existeCamino(1,3));
        assertTrue(grafoClon.existeCamino(2,3));
        assertTrue(grafoClon.existeCamino(2,1));
        assertFalse(grafoClon.esVacio());
        assertEquals(grafoClon.caminoMasCorto(2,3).toString(), "[2,3]");
        assertEquals(grafoClon.caminoMasLargo(2,3).toString(), "[2,1,3]");
        assertEquals(grafoClon.listarEnProfundidad().toString(), "[1,3,2]");
        assertEquals(grafoClon.listarEnAnchura().toString(), "[1,3,2]");
        assertEquals(grafoClon.toString(), "1 -> 3 (100)\n2 -> 3 (100), 1 (100)\n3 -> \n");
    }

    @Test
    public void testClonConTresVerticesYEliminarArco() {
        Grafo grafo = new Grafo();
        assertTrue(grafo.insertarVertice(1));
        assertTrue(grafo.insertarVertice(2));
        assertTrue(grafo.insertarVertice(3));
        assertTrue(grafo.insertarArco(1,3,100));
        assertTrue(grafo.insertarArco(2,3,100));
        assertTrue(grafo.insertarArco(2,1,100));
        Grafo grafoClon = grafo.clone();
        assertTrue(grafo.eliminarArco(2,3));
        assertTrue(grafo.existeVertice(1));
        assertTrue(grafo.existeVertice(2));
        assertTrue(grafo.existeVertice(3));
        assertTrue(grafo.existeArco(1,3));
        assertFalse(grafo.existeArco(2,3));
        assertTrue(grafo.existeArco(2,1));
        assertTrue(grafo.existeCamino(1,3));
        assertTrue(grafo.existeCamino(2,3));
        assertTrue(grafo.existeCamino(2,1));
        assertFalse(grafo.esVacio());
        assertEquals(grafo.caminoMasCorto(2,3).toString(), "[2,1,3]");
        assertEquals(grafo.caminoMasLargo(2,3).toString(), "[2,1,3]");
        assertEquals(grafo.listarEnProfundidad().toString(), "[1,3,2]");
        assertEquals(grafo.listarEnAnchura().toString(), "[1,3,2]");
        assertEquals(grafo.toString(), "1 -> 3 (100)\n2 -> 1 (100)\n3 -> \n");
        assertTrue(grafoClon.existeVertice(1));
        assertTrue(grafoClon.existeVertice(2));
        assertTrue(grafoClon.existeVertice(3));
        assertTrue(grafoClon.existeArco(1,3));
        assertTrue(grafoClon.existeArco(2,3));
        assertTrue(grafoClon.existeArco(2,1));
        assertTrue(grafoClon.existeCamino(1,3));
        assertTrue(grafoClon.existeCamino(2,3));
        assertTrue(grafoClon.existeCamino(2,1));
        assertFalse(grafoClon.esVacio());
        assertEquals(grafoClon.caminoMasCorto(2,3).toString(), "[2,3]");
        assertEquals(grafoClon.caminoMasLargo(2,3).toString(), "[2,1,3]");
        assertEquals(grafoClon.listarEnProfundidad().toString(), "[1,3,2]");
        assertEquals(grafoClon.listarEnAnchura().toString(), "[1,3,2]");
        assertEquals(grafoClon.toString(), "1 -> 3 (100)\n2 -> 3 (100), 1 (100)\n3 -> \n");
    }

}
