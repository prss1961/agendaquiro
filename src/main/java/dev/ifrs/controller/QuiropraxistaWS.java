package dev.ifrs.controller;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import dev.ifrs.model.Consulta;
import dev.ifrs.model.Quiropraxista;

@Path("/quiro")
@Transactional
public class QuiropraxistaWS {

    //Adicionar um Quiropraxista. Requer Role de Quiro
    @POST
    @Path("/adicionar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addQuiro(@RequestBody AdicionaQuiropraxista qui){
        Quiropraxista quiro = new Quiropraxista();
        quiro.setNome(qui.getNome());
        quiro.setCpf(qui.getCpf());
        quiro.setEmail(qui.getEmail());
        quiro.setCrm(qui.getCrm());
        quiro.persist();
    }

    //Listar todos os Quiropraxistas. Não mostra as consultas
    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Quiropraxista> list() {
        // 3 - O método `listAll` recupera todos os objetos da classe User.
        return Quiropraxista.listAll();
    }

    //Listar as consultas do Quiropraxista com o Id informado
    @GET
    @Path("/listar/consultas/{idCons}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Set<Consulta> listarCons(@PathParam("idPac") Long idPac) {
        Quiropraxista quiro = Quiropraxista.findById(idPac);
        if (quiro == null)
            throw new BadRequestException("Paciente não encontrado"); 
        
        return quiro.getConsultas(); 
    }

    //Exclui o Quiropraxista com o Id informado. Não exclui caso possua consultas
    @GET
    @Path("/excluir/{idQuiro}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void excluir(@PathParam("idQuiro") Long idQuiro){
        Quiropraxista quiro = Quiropraxista.findById(idQuiro);
        if (quiro == null)
            throw new BadRequestException("Quiropraxista não encontrado");
        quiro.delete();
    }
    
}
