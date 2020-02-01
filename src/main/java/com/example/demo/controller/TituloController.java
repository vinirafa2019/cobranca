package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.StatusTitulo;
import com.example.demo.model.Titulo;
import com.example.demo.model.Titulos;

@Controller
@RequestMapping("/home")
public class TituloController {
	
	
	private static final String CADASTRO_VIEW ="CadastroTitulo";
	@Autowired
	private Titulos titulos;
	
	@RequestMapping("/cadastro")
	public ModelAndView novo() {	
		ModelAndView mv=new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors,RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		titulos.save(titulo);
		attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso");		
		return "redirect:/home/cadastro";
	} 
	@RequestMapping
	public ModelAndView pesquisa() {	
		List<Titulo>todosTitulos = titulos.findAll();
		ModelAndView mv=new ModelAndView("PesquisaTitulo");
		mv.addObject("titulos",todosTitulos);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}" , method =RequestMethod.POST)
	public String excluir(@PathVariable Long codigo,RedirectAttributes attributes) {
		titulos.deleteById(codigo);
		
		attributes.addFlashAttribute("mensagem", "Titulo excluído com sucesso");
		return "redirect:/home";
		
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo>todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	
	
	
	
}
