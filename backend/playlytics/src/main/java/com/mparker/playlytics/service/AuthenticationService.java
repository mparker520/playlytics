package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import com.mparker.playlytics.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {


    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;

    public AuthenticationService(RegisteredPlayerRepository registeredPlayerRepository) {

        this.registeredPlayerRepository = registeredPlayerRepository;

    }

    //</editor-fold>


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(registeredPlayerRepository.existsByLoginEmail(username)) {


            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceByLoginEmail(username);

            String password = registeredPlayer.getPassword();
            Long authenticatedUserId = registeredPlayer.getId();


            return   new CustomUserDetails(username, password, authenticatedUserId);

        }

        else throw new UsernameNotFoundException(username);

    }


}
