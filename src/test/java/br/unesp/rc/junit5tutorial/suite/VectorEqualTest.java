package br.unesp.rc.junit5tutorial.suite;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VectorEqualTest {
    private static int v1[];
    private static int v2[];

    public VectorEqualTest(){
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
    public void testEqual() {
    System.out.println("Os valores são iguais?");
    boolean expResult = true;
    boolean result = Arrays.equals(v1, v2);
    assertEquals(expResult, result);
    }

}
