package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import com.soulcode.Servicos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("servicos")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/usuarios")
    public List<User> usuarios() {
        return userService.listar();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> inserirUsuario(@RequestBody User user) {
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/usuarios/{idUser}")
    public ResponseEntity<User> alterarSenha(@RequestBody User user,
                                             @PathVariable Integer idUser) {

        Optional <User> user1 = userRepository.findById(idUser);
        user.setId(idUser);
        String senhaNovaCodificada = passwordEncoder.encode(user.getPassword());
        user1.get().setPassword(senhaNovaCodificada);
        userService.alterarSenha(user1.get());
        return ResponseEntity.ok().body(user1.get());
    }
}