package br.unesp.rc.springtutorial.dto.assembler;

import java.util.ArrayList;
import java.util.List;

import br.unesp.rc.springtutorial.dto.EnderecoDTO;
import br.unesp.rc.springtutorial.dto.JuridicaDTO;
import br.unesp.rc.springtutorial.entity.Acesso;
import br.unesp.rc.springtutorial.entity.Contato;
import br.unesp.rc.springtutorial.entity.Endereco;
import br.unesp.rc.springtutorial.entity.Juridica;

public class JuridicaAssembler {
    private JuridicaAssembler() {
    }

    public static Juridica dtoToEntityModel(JuridicaDTO dto) {
        Juridica juridica = new Juridica();

        juridica.setNome(dto.getNome());
        juridica.setCnpj(dto.getCnpj());

        Acesso acesso = new Acesso();
        acesso.setUsuario(dto.getUsuario());
        acesso.setSenha(dto.getSenha());
        juridica.setAcesso(acesso);

        Contato contato = new Contato();
        contato.setTelefoneComercial(dto.getTelefoneComercial());
        contato.setCelular(dto.getCelular());
        contato.setEmail(dto.getEmail());
        juridica.setContato(contato);

        if (dto.getEndereco() != null) {
            for (EnderecoDTO edto : dto.getEndereco()) {
                Endereco endereco = new Endereco();
                endereco.setRua(edto.getRua());
                endereco.setNumero(edto.getNumero());
                endereco.setBairro(edto.getBairro());
                endereco.setCep(edto.getCep());
                endereco.setCidade(edto.getCidade());
                endereco.setEstado(edto.getEstado());
                juridica.setEndereco(endereco);
            }
        }

        return juridica;
    }

    public static JuridicaDTO entityToDtoModel(Juridica juridica) {
        if (juridica == null) {
            return null;
        }

        JuridicaDTO dto = new JuridicaDTO();
        dto.setNome(juridica.getNome());
        dto.setCnpj(juridica.getCnpj());

        if (juridica.getAcesso() != null) {
            dto.setUsuario(juridica.getAcesso().getUsuario());
            dto.setSenha(juridica.getAcesso().getSenha());
        }

        if (juridica.getContato() != null) {
            dto.setTelefoneComercial(juridica.getContato().getTelefoneComercial());
            dto.setCelular(juridica.getContato().getCelular());
            dto.setEmail(juridica.getContato().getEmail());
        }

        if (juridica.getEndereco() != null) {
            List<EnderecoDTO> enderecos = new ArrayList<>();
            for (Endereco endereco : juridica.getEndereco()) {
                EnderecoDTO enderecoDTO = new EnderecoDTO();
                enderecoDTO.setRua(endereco.getRua());
                enderecoDTO.setNumero(endereco.getNumero());
                enderecoDTO.setBairro(endereco.getBairro());
                enderecoDTO.setCep(endereco.getCep());
                enderecoDTO.setCidade(endereco.getCidade());
                enderecoDTO.setEstado(endereco.getEstado());
                enderecos.add(enderecoDTO);
            }
            dto.setEndereco(enderecos);
        }

        return dto;
    }
}
