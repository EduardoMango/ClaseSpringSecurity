package org.eduardomango.clasespringsecurity.config;

import jakarta.annotation.PostConstruct;
import org.eduardomango.clasespringsecurity.model.entities.CuentaEntity;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;
import org.eduardomango.clasespringsecurity.model.enums.EstadoCivil;
import org.eduardomango.clasespringsecurity.model.enums.TipoCuenta;
import org.eduardomango.clasespringsecurity.repositories.CuentaRepository;
import org.eduardomango.clasespringsecurity.repositories.UserRepository;
import org.eduardomango.clasespringsecurity.security.model.entities.CredentialsEntity;
import org.eduardomango.clasespringsecurity.security.repositories.CredentialsRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
public class DBStarter {

    private final CuentaRepository cuentaRepository;
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public DBStarter(CuentaRepository cuentaRepository, UserRepository userRepository, CredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder) {
        this.cuentaRepository = cuentaRepository;
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init(){
        Set<UserEntity> users = new HashSet<>();
        Set<CuentaEntity> accounts = new HashSet<>();
        Set<CredentialsEntity> credentialsEntities = new HashSet<>();
        Random random = new Random();

        String[] firstNames = {"Pedro", "Ana", "Luis", "Maria", "Juan", "Laura", "Carlos", "Sofia", "Diego", "Valeria", "Fernando", "Carolina", "Martin", "Gabriela", "Javier"};
        String[] lastNames = {"Gomez", "Rodriguez", "Perez", "Garcia", "Martinez", "Lopez", "Diaz", "Sanchez", "Ramirez", "Torres", "Flores", "Rivera", "Gonzales", "Ruiz", "Hernandez"};
        EstadoCivil[] estadosCiviles = EstadoCivil.values();
        TipoCuenta[] tiposCuenta = TipoCuenta.values();

        // Create 15 users
        for (int i = 0; i < 15; i++) {
            String dni = generateRandomNumberString(8);
            String nombre = firstNames[random.nextInt(firstNames.length)];
            String apellido = lastNames[random.nextInt(lastNames.length)];
            String email = nombre.toLowerCase() + "_" + apellido.toLowerCase() + i + "@email.com";
            Integer edad = 18 + random.nextInt(50); // Ages between 18 and 67
            String cuit = generateCuit(dni);
            EstadoCivil estadoCivil = estadosCiviles[random.nextInt(estadosCiviles.length)];

            UserEntity user = UserEntity.builder()
                    .dni(dni)
                    .email(email)
                    .nombre(nombre)
                    .apellido(apellido)
                    .edad(edad)
                    .cuit(cuit)
                    .estadoCivil(estadoCivil)
                    .build();
            users.add(user);

            CredentialsEntity credentialsEntity = CredentialsEntity.builder()
                    .email(email)
                    .password(passwordEncoder.encode("password"))
                    .user(user)
                    .build();

            System.out.println("Created User: " + user);
        }

        // Create 25 accounts, distributing them among the 15 users
        UserEntity[] userArray = users.toArray(new UserEntity[0]);
        for (int i = 0; i < 25; i++) {
            UserEntity randomUser = userArray[random.nextInt(userArray.length)];
            String accountNumber = generateRandomNumberString(10);
            TipoCuenta tipoCuenta = tiposCuenta[random.nextInt(tiposCuenta.length)];

            CuentaEntity cuenta = CuentaEntity.builder()
                    .usuario(randomUser)
                    .tipo(tipoCuenta)
                    .numero(accountNumber)
                    .build();
            accounts.add(cuenta);


        }

        // Now you would typically persist these entities using your repositories
        credentialsRepository.saveAll(credentialsEntities);
        userRepository.saveAll(users);
        cuentaRepository.saveAll(accounts);

    }

    private String generateRandomNumberString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateCuit(String dni) {
        return "20-" + dni + "-" + new Random().nextInt(10);
    }
}

