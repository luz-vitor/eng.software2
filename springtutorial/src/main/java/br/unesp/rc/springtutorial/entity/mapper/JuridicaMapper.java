package br.unesp.rc.springtutorial.entity.mapper;

import br.unesp.rc.springtutorial.entity.Acesso;
import br.unesp.rc.springtutorial.entity.Contato;
import br.unesp.rc.springtutorial.entity.Endereco;
import br.unesp.rc.springtutorial.entity.Juridica;

public class JuridicaMapper {
    private JuridicaMapper() {
    }

    public static void update(Juridica juridicaUpdate, Juridica newJuridica) {
        juridicaUpdate.setNome(newJuridica.getNome());

        Acesso a = juridicaUpdate.getAcesso();
        a.setUsuario(newJuridica.getAcesso().getUsuario());
        a.setSenha(newJuridica.getAcesso().getSenha());
        juridicaUpdate.setAcesso(a);

        Contato c = juridicaUpdate.getContato();
        c.setTelefoneComercial(newJuridica.getContato().getTelefoneComercial());
        c.setCelular(newJuridica.getContato().getCelular());
        c.setEmail(newJuridica.getContato().getEmail());
        juridicaUpdate.setContato(c);

        int i = 0;
        for (Endereco e : juridicaUpdate.getEndereco()) {
            Endereco edto = newJuridica.getEndereco().get(i);
            e.setRua(edto.getRua());
            e.setNumero(edto.getNumero());
            e.setBairro(edto.getBairro());
            e.setCep(edto.getCep());
            e.setCidade(edto.getCidade());
            e.setEstado(edto.getEstado());
            i++;
        }
    }
}