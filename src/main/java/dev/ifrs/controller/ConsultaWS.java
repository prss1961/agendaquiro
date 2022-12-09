package dev.ifrs.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import dev.ifrs.model.Consulta;
import dev.ifrs.model.Paciente;
import dev.ifrs.model.Quiropraxista;

@Path("/consulta")
@Transactional
public class ConsultaWS {

     
     @POST
     @Path("/adicionar")
     @Consumes(MediaType.APPLICATION_JSON)
     public void addConsulta(@RequestBody AdicionaConsulta consulta){
         Consulta cons = new Consulta();
         cons.setData(consulta.getData());
         cons.setHorario(consulta.getHorario());
         cons.persist();
     }
 
     //Marcar uma consulta, ao adionar um paciente e quiropraxista, ao informar os ids deles e da consulta criada anteriormente.
     //Requer Role de Admin
     @GET
     @Path("/marcar/{idCons}/{idPac}/{idQuiro}")
     @Produces(MediaType.APPLICATION_JSON)
     public Consulta salvar(@PathParam("idCons") Long idCons, @PathParam("idPac") Long idPac, @PathParam("idQuiro") Long idQuiro){
 
     Consulta consulta = Consulta.findById(idCons);
     consulta.setSituacao("MARCADA");
     consulta.persistAndFlush();
 
     Paciente pac = Paciente.findById(idPac);
     if (pac == null)
         throw new BadRequestException("Paciente não encontrado"); 
 
     pac.addConsulta(consulta);
     consulta.setPaciente(pac);
     pac.persistAndFlush();
 
     Quiropraxista quiro = Quiropraxista.findById(idQuiro);
     if (quiro == null)
         throw new BadRequestException("Quiropraxista não encontrado"); 
     
     quiro.addConsulta(consulta);
     consulta.setQuiro(quiro);
     quiro.persistAndFlush();
 
     consulta.persist();
 
     return consulta;
    }
 
     //Altera a situação da consulta passada pelo id para CANCELADA
     @GET
     @Path("/cancelar/{idCons}")
     @Produces(MediaType.APPLICATION_JSON)
     public Consulta cancelar(@PathParam("idCons") Long idCons){
         Consulta consulta = Consulta.findById(idCons);
         consulta.setSituacao("CANCELADA");
         consulta.persist();
 
         return consulta;
     }
 
     //Altera a situação da consulta passada pelo id para CONFIRMADA
     @GET
     @Path("/confirmar/{idCons}")
     @Produces(MediaType.APPLICATION_JSON)
     public Consulta confirmar(@PathParam("idCons") Long idCons){
         Consulta consulta = Consulta.findById(idCons);
         consulta.setSituacao("CONFIRMADA");
         consulta.persist();
 
         return consulta;
     }
 
     //Altera a situação da consulta passada pelo id para REALIZADA
     @GET
     @Path("/realizar/{idCons}")
     @Produces(MediaType.APPLICATION_JSON)
     public Consulta realizar(@PathParam("idCons") Long idCons){
         Consulta consulta = Consulta.findById(idCons);
         consulta.setSituacao("REALIZADA");
         consulta.persist();
 
         return consulta;
     }
 
     //Lista todas as consultas
     @GET
     @Path("/listar")
     @Produces(MediaType.APPLICATION_JSON)
     @Transactional
     public List<Consulta> list() {
         // 3 - O método `listAll` recupera todos os objetos da classe User.
         return Consulta.listAll();
         
     }
     
     //exclui uma consulta. Infelizmente não funciona caso possua Paciente ou Quiropraxista 
     //Provavelmente por causa das dependências entre as classes
     
     @GET
     @Path("/excluir/{idCons}")
     @Produces(MediaType.APPLICATION_JSON)
     
     @Transactional
     public void excluir(@PathParam("idCons") Long idCons){
         Consulta consulta = Consulta.findById(idCons);
         if (consulta == null)
             throw new BadRequestException("Consulta não encontrada");
         consulta.delete();
     }
 
    
}
