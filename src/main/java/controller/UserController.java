package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.User;
import repository.UserRepository;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserRepository userRepository;
    
	
	@GetMapping("/users")
	public ResponseEntity<Object> getStudent() {
     try {		
	     List<User> users = userRepository.findAll(); 	
		 return new ResponseEntity<>(users, HttpStatus.OK);
	} catch (Exception e) {	
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

	@PostMapping("/users")
	public ResponseEntity<Object> addStudent(@RequestBody User body) {
		
		try {		
			User user =  userRepository.save(body);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Object> getStudentDetail(@PathVariable Long userId) {

		try {		
			
			Optional<User> user = userRepository.findById(userId);
			if(user.isPresent()) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@PutMapping("user/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable Integer id, @RequestBody User body) {

		try {
			Optional<User> user = userRepository.findById(id);

			if (user.isPresent()) {
				User userEdit = user.get();
				userEdit.setUserId(body.getUserId());
				userEdit.setUsername(body.getUsername());
				userEdit.setPassword(body.getPassword());
				
				userRepository.save(userEdit);

				return new ResponseEntity<>(userEdit, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("user/{id}")
	public ResponseEntity<Object> deletestudent(@PathVariable Integer id) {
	    try {
	    	Optional<User> user = userRepository.findById(id);
	    	   if (user.isPresent()) {
		            userRepository.delete(user.get());

	            return new ResponseEntity<>("Delete Successful", HttpStatus.OK);
	        } else {
	        	
	            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody User body) {
		User user =  userRepository.findByUsername(body.getUsername());
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Studeent not found.");
		}
		
		if (!user.getPassword().equals(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
		
	}

}
