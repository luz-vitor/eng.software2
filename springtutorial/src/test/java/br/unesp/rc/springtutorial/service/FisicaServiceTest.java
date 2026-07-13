package br.unesp.rc.springtutorial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.repository.FisicaRepository;
import br.unesp.rc.springtutorial.utils.InstanceGenerator;

@SpringBootTest
public class FisicaServiceTest {
    
    private Fisica entity;

    @Autowired
    private FisicaService fs;

    @Autowired
    private FisicaRepository repository;

    @BeforeEach
    void limparBanco() {        
        repository.deleteAll();
    }
    @Disabled
    @Test
    @DisplayName("FisicaService.save(Fisica)")
    void testSave(){
        entity = InstanceGenerator.getPessoaFisica("222.333.444-55", "user2");
        System.out.println(entity);

        Fisica f = fs.save(entity);
        System.out.println("----------------------------------------");
        System.out.println(f);
        System.out.println("----------------------------------------");

        assertEquals(entity, f);
    }

    @Disabled
    @Test
    @DisplayName("FisicaService.findaByCpf(cpf)")
    void testFindByCpf(){
        entity = InstanceGenerator.getPessoaFisica("222.333.444-55", "user1");
        fs.save(entity);

        Fisica f = fs.findByCpf( "222.333.444-55");
        System.out.println("----------------------------------------");
        System.out.println("Resultado do findByCPF:");
        System.out.println("----------------------------------------");
        System.out.println(f);
        System.out.println("----------------------------------------");

        assertEquals(entity, f);
    }
    
    @Disabled
    @Test
    @DisplayName("1-Inserir pessoa física já cadastrada")
    public void testDuplicated(){
        Fisica f1 = InstanceGenerator.getPessoaFisica("222.333.444-55", "user1");
        Fisica f2 = InstanceGenerator.getPessoaFisica("222.333.444-55", "user2");
       
        fs.save(f1);

        assertThrows(IllegalArgumentException.class,()-> {
            fs.save(f2);
        });
    }

    @Disabled
    @Test
    @DisplayName("2-Consulta pessoa física existente")
    public void findByCpfExistente(){
        entity = InstanceGenerator.getPessoaFisica("123.452.000-13", "userTest");
        fs.save(entity);

        Fisica resultado = fs.findByCpf("123.452.000-13");
        assertEquals(entity.getCpf(), resultado.getCpf());
    }

    @Disabled
    @Test
    @DisplayName("3-Consulta pessoa física não existente")
    public void findByCpfInexistente(){
        Fisica resultado = fs.findByCpf("999.999.999-55");

        assertNull(resultado);
    }

    @Disabled
    @Test
    @DisplayName("4-Listar todas as pessoas fisicas existentes")
    public void findAll(){
        fs.save(InstanceGenerator.getPessoaFisica("111.111.111-11", "João"));
        fs.save(InstanceGenerator.getPessoaFisica("222.222.222-22", "Maria"));

        List<Fisica> lista = fs.findAll();

        assertNotEquals(0, lista.size());
    }

    @Disabled
    @Test
    @DisplayName("5-Excluir pessoa fisica")
    public void testDelete(){
        entity = InstanceGenerator.getPessoaFisica("333.333.333-33", "Carlos");
        fs.save(entity);

        fs.delete(entity);

        Fisica resultado = fs.findByCpf(entity.getCpf());
        
        assertNull(resultado);

    }

    @Disabled
    @Test
    @DisplayName("6-Alterar pessoa fisica")
    public void testUpdate(){
        entity = InstanceGenerator.getPessoaFisica("444.444.444-44", "Jose");
        fs.save(entity);

        entity = fs.findByCpf("444.444.444-44");
        
        entity.setNome("Jose da Silva");
        
        Fisica atualizado = fs.update(entity);

        assertEquals("Jose da Silva", atualizado.getNome());
    }



    @Disabled
    @Test
    @DisplayName("7-findAll() vazio")
    public void testFindAllVazio() {

        // List<Fisica> lista = fs.findAll();

        // for (Fisica f : lista) {
        //     fs.delete(f);
        // }

        // lista = fs.findAll();

        // assertEquals(0, lista.size());

        //ou

        assertTrue(fs.findAll().isEmpty());
    }

     
    @Test
    @DisplayName("Inserir 2 entidades")
    void insertEntity(){
        fs.save(InstanceGenerator.getPessoaFisica("222.333.444-55", "user1"));
        fs.save(InstanceGenerator.getPessoaFisica("222.333.444-56", "user2"));
    }
}
