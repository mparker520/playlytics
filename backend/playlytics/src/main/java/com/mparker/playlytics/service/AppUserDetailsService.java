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
public class AppUserDetailsService implements UserDetailsService {


    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;

    public AppUserDetailsService(RegisteredPlayerRepository registeredPlayerRepository) {

        this.registeredPlayerRepository = registeredPlayerRepository;

    }

    //</editor-fold>


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(registeredPlayerRepository.existsByLoginEmail(username)) {


            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceByLoginEmail(username);

            String password = registeredPlayer.getPassword();
            Long authenticatedUserId = registeredPlayer.getId();
            String email = registeredPlayer.getLoginEmail();
            String firstName = registeredPlayer.getFirstName();
            String lastName = registeredPlayer.getLastName();
            String displayName = registeredPlayer.getDisplayName();


            return   new CustomUserDetails(username, password, authenticatedUserId, email, firstName, lastName, displayName);

        }

        else throw new UsernameNotFoundException(username);

    }


}
