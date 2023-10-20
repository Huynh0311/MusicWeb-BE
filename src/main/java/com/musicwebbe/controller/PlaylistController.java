package com.musicwebbe.controller;

import com.musicwebbe.model.Account;
import com.musicwebbe.model.Playlist;
import com.musicwebbe.model.Song;
import com.musicwebbe.model.dto.SongDTO;
import com.musicwebbe.service.impl.AccountService;
import com.musicwebbe.service.impl.PlaylistService;
import com.musicwebbe.service.impl.PlaylistSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private PlaylistSongService playlistSongService;
    @Autowired
    AccountService accountService;

    public Account getCurrentAccount() {
        try {
            String email = "";
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                email = userDetails.getUsername();
            }
            return accountService.findByEmail(email);

        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Playlist>> getAll(){
        return new ResponseEntity<>(playlistService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/findOne/{id}")
    public ResponseEntity<Playlist> findById(@PathVariable int id){
        return new ResponseEntity<>(playlistService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/countSong/{id}")
    public ResponseEntity<Integer> countSong(@PathVariable int id){
        return new ResponseEntity<>(playlistSongService.countSong(id), HttpStatus.OK);
    }
    @DeleteMapping("/deletePlaylist/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable int id){
        playlistService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/getSongByPlaylist/{id}")
    public ResponseEntity<List<SongDTO>> getSongByPlaylist(@PathVariable int id){
        Account account = getCurrentAccount();
        return new ResponseEntity<>(playlistSongService.findAllByPlaylist(id,account), HttpStatus.OK);
    }
    @GetMapping("/getUserByPlaylist/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable int id){
        return new ResponseEntity<>(playlistService.getAccount(id), HttpStatus.OK);
    }

}
