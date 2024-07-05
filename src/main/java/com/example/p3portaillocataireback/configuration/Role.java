package com.example.p3portaillocataireback.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.p3portaillocataireback.configuration.Permission.*;
@Getter
@RequiredArgsConstructor
public enum Role {
    // Rôle Utilisateur avec permissions de gestion de compte
    USER(
            Set.of(
                    USER_READ, // Lire les informations utilisateur
                    USER_UPDATE, // Mettre à jour les informations utilisateur
                    USER_DELETE, // Supprimer un utilisateur
                    USER_CREATE // Créer un utilisateur
            )
    ),
    // Rôle Administrateur avec permissions avancées
    ADMIN(
            Set.of(
                    ADMIN_READ,// Lire les informations de l'administrateur
                    ADMIN_UPDATE, // Mettre à jour les informations de l'administrateur
                    ADMIN_DELETE, // Supprimer un administrateur
                    ADMIN_CREATE // Créer un administrateur
            )
    );
    // Champ privé contenant les permissions du rôle
    private final Set<Permission> permissions;

    // Méthode pour convertir les permissions en autorités Spring Security
    public List<SimpleGrantedAuthority> getAuthorities() {
        // Conversion des permissions en GrantedAuthorities
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        // Ajout d'une autorité "ROLE_" + nom du rôle
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        // Retourne la liste des autorités
        return authorities;
    }
}
