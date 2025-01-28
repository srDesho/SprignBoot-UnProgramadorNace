package com.cristianml.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired;
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;
    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired;

    // Relación Many-to-Many con la entidad RoleEntity
    // fetch = Define cómo se cargarán los datos. FetchType.EAGER cargará todos los roles asociados al usuario de inmediato.
    // cascade = Controla la persistencia de los datos. CascadeType.ALL permite que cualquier operación en User
    // (crear, actualizar, eliminar) se aplique también a los roles asociados.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // Configuración de la tabla intermedia para la relación Many-to-Many entre User y RoleEntity
    // name = Define el nombre de la tabla intermedia en la base de datos ("user_roles").
    // joinColumns = Especifica la clave foránea en la tabla intermedia que hace referencia a la entidad User ("user_id").
    // inverseJoinColumns = Especifica la clave foránea en la tabla intermedia que hace referencia a la entidad RoleEntity ("role_id").
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    // Usamos un Set en lugar de una List para evitar duplicados, ya que un usuario no debe tener roles repetidos.
    private Set<RoleEntity> roles = new HashSet<>();

}
