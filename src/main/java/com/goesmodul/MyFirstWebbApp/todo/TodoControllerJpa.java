package com.goesmodul.MyFirstWebbApp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {
	
	
	private TodoRepository todoRepository;
	
	public TodoControllerJpa(TodoService todoService, TodoRepository todoRepository) {
		super();
		
		this.todoRepository = todoRepository;
	}


	//list-todos
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedinUsername(model);
		
		//List<Todo> todos = todoService.findByUsername(username);
		List<Todo> todos = todoRepository.findByUsername(username);
		model.put("todos", todos);
		return "listTodos";
		
	}


	
	//add-todo GET
	@RequestMapping(value="add-todo", method = RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username = getLoggedinUsername(model);
		Todo todo = new Todo(0, username, "Default Desc", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}
	
	//add-todo POST
	@RequestMapping(value="add-todo", method = RequestMethod.POST)
	public String addNewTodoPage(ModelMap model,@Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedinUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);

		return "redirect:list-todos";
	}
	
	//delete-todo 
	@RequestMapping(value="delete-todo")
	public String deleteTodo(@RequestParam int id) {
	

		
		todoRepository.deleteById(id);
		return "redirect:list-todos";
	}
	
	//update-todo 
	@RequestMapping(value="update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = todoRepository.findById(id).get();
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	//update-todo 
	@RequestMapping(value="update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model,@Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedinUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);

		
		return "redirect:list-todos";
	}
	

	private String getLoggedinUsername(ModelMap model) {
			Authentication authentication = 
			SecurityContextHolder.getContext().getAuthentication(); 
			return authentication.getName();
	}
	
	
				
}