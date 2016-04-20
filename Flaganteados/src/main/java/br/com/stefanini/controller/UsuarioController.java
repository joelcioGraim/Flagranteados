package br.com.stefanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.stefanini.model.ResultadoModel;
import br.com.stefanini.model.UsuarioModel;
import br.com.stefanini.repository.UsuarioRepository;

/** 
 *
 * Essa é a classe que o Spring vai gerenciar (Controller para o usuário)
 *
 * @Controller => informa que a classe é um controller a ser gerenciado pelo Spring.
 *
 * @RequestMapping => caminho para acessar o controller.
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {


	/**
	 *@Autowired => injetando o objeto resultadoModel no nosso controller 
	 */
	@Autowired
	ResultadoModel resultadoModel;
 
	/**
	 * Injetando o objeto usuarioRepository 
	 */
	@Autowired
	UsuarioRepository usuarioRepository;
 
 
	/**
	 * 
	 * Chama á view(cadastrar.html) para cadastrar um novo registro
	 * 
	 * @RequestMapping => value  => Defini o caminho para a chamada da view. 
	 * @RequestMapping => method => Defini o o método http que o método vai responder.
	 */
	@RequestMapping(value ="/views/usuario/cadastrar.html", method= RequestMethod.GET)
	public ModelAndView Cadastrar(){
 
		return new ModelAndView("cadastrar");
	}	
 
	/**
	 * Chama a view(consultarRegistros.html) para mostrar todos os registros cadastrados
	 * 
	 */
	@RequestMapping(value ="/consultarRegistros.html", method= RequestMethod.GET)
	public ModelAndView Consultar(){
 
		return new ModelAndView("consultarRegistros");
	}
 
	/**
	 * Chama a view (editarRegistro.html) para editar um registro cadastrado
	 * 
	 */
	@RequestMapping(value ="/editarRegistro.html/{codigo}", method= RequestMethod.GET)
	public ModelAndView EditarRegistro(@PathVariable long id){
 
		UsuarioModel usuarioModel = usuarioRepository.ConsultarPorCodigo(id);
 
		return new ModelAndView("editarRegistro","usuarioModel",usuarioModel);
	}
 
	/**
	 * 
	 * Salva um novo registro via ajax, esse método vai ser chamado pelo cadastrarController.js 
	 * através do AngularJS
	 * 
	 */
	@RequestMapping(value="/salvar", method= RequestMethod.POST)
	public @ResponseBody ResultadoModel Salvar(@RequestBody UsuarioModel usuarioModel){
 
		try {
 
			usuarioRepository.Salvar(usuarioModel);
 
			resultadoModel.setCodigo(1);
			resultadoModel.setMensagem("Registro inserido com sucesso!");
 
		} catch (Exception e) {
 
			resultadoModel.setCodigo(2);
			resultadoModel.setMensagem("Erro ao salvar o registro ("+e.getMessage()+")");
		}
 
		return resultadoModel;
	}
 
	/**
	 * Altera um registro cadastrado (editarRegistroController.js)
	 * 
	 *
	 */
	@RequestMapping(value="/alterar", method= RequestMethod.POST)
	public @ResponseBody ResultadoModel Alterar(@RequestBody UsuarioModel usuarioModel){
 
		try {
 
			usuarioRepository.Alterar(usuarioModel);
 
			resultadoModel.setCodigo(1);
			resultadoModel.setMensagem("Registro alterado com sucesso!");
 
		} catch (Exception e) {
 
			resultadoModel.setCodigo(2);
			resultadoModel.setMensagem("Erro ao salvar o registro ("+e.getMessage()+")");
		}
 
		return resultadoModel;
	}
 
 
	/**
	 * Consulta todos os registros cadastrados(consultarRegistrosController.js)
	 * 
	 */
	@RequestMapping(value="/consultarTodos", method= RequestMethod.GET)
	public @ResponseBody List<UsuarioModel> ConsultarTodos(){
 
		return usuarioRepository.TodosUsuarios();
 
	}
 
	/**
	 * Excluir um usuário pelo código (consultarRegistrosController.js)
	 * 
	 */
	@RequestMapping(value="/excluirRegistro/{codigo}", method= RequestMethod.DELETE)
	public @ResponseBody void ExcluirRegistro(@PathVariable long id){
 
		usuarioRepository.Excluir(id);
 
	}
}
