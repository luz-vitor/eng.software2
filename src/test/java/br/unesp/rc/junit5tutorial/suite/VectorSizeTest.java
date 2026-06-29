package br.unesp.rc.junit5tutorial.suite;

import br.unesp.rc.junit5tutorial.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorSizeTest {
    private static int v1[];
    private static int v2[];

    public VectorSizeTest() {
    }

    @BeforeAll
    static void init(){
        System.out.println("Inicializando os vetores");
        int[] a = {1,2,3};
        int[] b = {1,2,3};

        v1 = new int[3];
        v1 = a;
        v2 = new int[3];
        v2 = b;
    }

    @Test
    void testSize(){
        System.out.println("Os vetores são do mesmo tamanho?");
        boolean expResult = true;
        boolean result = Vector.size(v1.length, v2.length);
        assertEquals(expResult, result);
    }

}
