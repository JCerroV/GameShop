package com.joaquincv.juegos.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaquincv.juegos.app.bean.output.CommentResponse;
import com.joaquincv.juegos.app.bean.output.RateResponse;
import com.joaquincv.juegos.app.models.entities.Category;
import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.GameComent;
import com.joaquincv.juegos.app.repository.CategoryRepository;
import com.joaquincv.juegos.app.services.GameComentService;
import com.joaquincv.juegos.app.services.ImageService;
import com.joaquincv.juegos.app.services.iGameService;

@RestController
@CrossOrigin("*")
public class GameController {
	
	@Autowired
	GameComentService comentService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	iGameService gameService;
	
	@GetMapping("/games/categories")
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	@PostMapping("/games/new")
	public ResponseEntity<String> addGame(@RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam(value="description",required = false) String description,
            @RequestParam("price") double price,
            @RequestParam(value = "categories", required = false) String categories,
            @RequestParam(value = "image", required = false) MultipartFile image) {
		
		try {	
			List<Category>categoriesSelected=new ObjectMapper().readValue(categories, new TypeReference<List<Category>>() {});		
			String imageUrl = "http://localhost:8080/images/default.jpg";
	        if (image != null && !image.isEmpty()) {
	            imageUrl = imageService.saveImage(image,title);
	            }
	        Game game = new Game(title, author, description, price,categoriesSelected,imageUrl);
            game.setCategories(categoriesSelected);
	        gameService.addGame(game);  
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@PostMapping("/admin/games/new/verify")
	public ResponseEntity<Boolean> verifyGame(@RequestBody String title){
		return new ResponseEntity<>(gameService.findByTitle(title),HttpStatus.OK);
		
	}
	
	@GetMapping("/admin/games")
	public ResponseEntity<Page<Game>>getAll(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10")int size,
			@RequestParam(defaultValue = "id")String order,
			@RequestParam(defaultValue = "true") boolean asc,
			@Param("filter")String filter)
	{
		
		Page<Game> games=gameService.getAllGames(PageRequest.of(page, size,Sort.by(order)),filter);
		
		if (!asc) {
			games=gameService.getAllGames(
					PageRequest.of(page, size,Sort.by(order).descending()),filter);
		}
		return new ResponseEntity<Page<Game>>(games,HttpStatus.OK);
	}
	
	@GetMapping("/games")
	public ResponseEntity<Page<Game>>getGames(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10")int size,
			@RequestParam(defaultValue = "id")String order,
			@RequestParam(defaultValue = "true") boolean asc,
			@Param("filter")String filter)
	{
		
		Page<Game> games=gameService.getGames(PageRequest.of(page, size,Sort.by(order)),filter);
		
		if (!asc) {
			games=gameService.getAllGames(
					PageRequest.of(page, size,Sort.by(order).descending()),filter);
		}
		return new ResponseEntity<Page<Game>>(games,HttpStatus.OK);
	}
	
	@GetMapping("/admin/games/{id}")
	public ResponseEntity<Game>getGame(@PathVariable Long id){
		Game game=gameService.findById(id);
		return new ResponseEntity<Game>(game,HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/games/delete/{id}")
    public void deleteGame(@PathVariable Long id) {
            gameService.deleteGame(id);
    }
	
	@PutMapping("/admin/games/update/{id}")
	public ResponseEntity<Game> updateGame(@PathVariable Long id,@RequestBody Game game){
		gameService.updateGame(id,game);
		return new ResponseEntity<Game>(HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/games/categoryfilter")
	public ResponseEntity<Page<Game>> findByCategories(@RequestParam List<Long> categoryIds, Pageable pageable){
		try {
			return new ResponseEntity<Page<Game>>(gameService.getByCategories(categoryIds, pageable),HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Page<Game>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	////////////////////////////////////COMMENTS//////////////////////////////////////
	
	
	@GetMapping("/games/{id}/comments")
	public ResponseEntity<List<CommentResponse>>getGameComments(@PathVariable Long id){
		try {
			List<CommentResponse> commentResponse=new ArrayList<>();
			List<GameComent> coments=comentService.getAllCommentsFromGame(id);
			coments.forEach(comment -> commentResponse.add(new CommentResponse(comment)));
			
			return new ResponseEntity<List<CommentResponse>>(commentResponse,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@PostMapping("/games/{id}/comments/new")
	public ResponseEntity<String>addNewComment(Principal principal,@PathVariable Long id, @RequestBody String content){
		try {
			comentService.addNewComment(principal.getName(), id, content);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/games/comments/{idComment}/update")
	public ResponseEntity<String>updateComment(@PathVariable Long idComment,@RequestBody String comment){
		try {
			comentService.updateComment(idComment, comment);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/games/comments/{idComment}/delete")
	public ResponseEntity<String>deleteComment(@PathVariable Long idComment){
		try {
			comentService.deleteComment(idComment);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/////////////////////////////VALUATE//////////////////////////////////////
	
	@GetMapping("/games/{id}/myRate")
	public ResponseEntity<RateResponse>getMyRate(Principal principal,@PathVariable Long id){
		try {
			
			return new ResponseEntity<RateResponse>(gameService.getMyRate(principal.getName(), id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RateResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/games/{id}/rate/new")
	public ResponseEntity<String> addNewRate(Principal principal,@PathVariable Long id, @RequestBody int rate){
		try {
			gameService.addNewRate(principal.getName(), id, rate);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/games/rates/{id}/update")
	public ResponseEntity<String>updateRate(@PathVariable Long id,@RequestBody int rate){
		try {
			gameService.updateMyRate(id, rate);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/games/rates/{id}/delete")
	public ResponseEntity<String>deleteRate(@PathVariable Long id){
		try {
			gameService.deleteMyRate(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

}
