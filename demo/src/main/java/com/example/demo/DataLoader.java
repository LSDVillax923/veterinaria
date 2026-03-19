package com.example.demo;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.entities.Tratamiento;
import com.example.demo.entities.Veterinario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;


    @Autowired
    private VeterinarioRepository veterinarioRepository;

    private static final String[] NOMBRES = {
        "Andrés", "Camila", "Luis", "Valentina", "Carlos", "Sofía",
        "Juan", "Isabella", "Sergio", "Daniela", "Miguel", "Laura",
        "Alejandro", "María", "Diego", "Paula", "Felipe", "Ana",
        "Sebastián", "Natalia"
    };

    private static final String[] APELLIDOS = {
        "García", "Rodríguez", "López", "Martínez", "González",
        "Pérez", "Sánchez", "Ramírez", "Torres", "Flores",
        "Rivera", "Gómez", "Díaz", "Vargas", "Morales",
        "Ortiz", "Reyes", "Herrera", "Medina", "Castro"
    };

    private static final String[] NOMBRES_MASCOTAS = {
        "Max", "Bella", "Luna", "Rocky", "Coco", "Mia", "Zeus", "Lola",
        "Thor", "Nala", "Rex", "Daisy", "Toby", "Molly", "Duke", "Sadie",
        "Charlie", "Maggie", "Buddy", "Sophie", "Jack", "Lily", "Bear",
        "Rosie", "Cooper", "Zoe", "Oliver", "Stella", "Tucker", "Gracie"
    };

    private static final String[] ESPECIES    = {"Perro", "Gato"};
    private static final String[] RAZAS_PERRO = {"Labrador", "Bulldog", "Poodle", "Beagle", "Chihuahua"};
    private static final String[] RAZAS_GATO  = {"Persa", "Siamés", "Maine Coon", "Ragdoll", "Bengalí"};

    private static final String[] ESTADOS      = {"activa", "activa", "activa", "tratamiento", "inactiva"};
    private static final String[] ENFERMEDADES = {"Ninguna", "Otitis", "Dermatitis", "Obesidad", "Anemia"};

    @Override
    public void run(String... args) throws Exception {

        if (clienteRepository.count() > 0) {
            return;
        }

        Random rnd = new Random(42);

        // 50 clientes
        for (int i = 1; i <= 50; i++) {
            String nombre   = NOMBRES[rnd.nextInt(NOMBRES.length)];
            String apellido = APELLIDOS[rnd.nextInt(APELLIDOS.length)];
            String correo   = nombre.toLowerCase() + i + "@email.com";
            String password = "pass" + i;
            String celular  = "31" + String.format("%08d", i);

            clienteRepository.save(new Cliente(nombre, apellido, correo, password, celular));
        }

        List<Cliente> clientes = clienteRepository.findAll();

        // 100 mascotas asignadas al azar (solo Perro y Gato)
        for (int i = 1; i <= 100; i++) {
            String nombreMascota = NOMBRES_MASCOTAS[rnd.nextInt(NOMBRES_MASCOTAS.length)] + i;
            String especie       = ESPECIES[rnd.nextInt(ESPECIES.length)];
            String raza          = "Perro".equals(especie)
                                   ? RAZAS_PERRO[rnd.nextInt(RAZAS_PERRO.length)]
                                   : RAZAS_GATO[rnd.nextInt(RAZAS_GATO.length)];

            int    edad          = rnd.nextInt(15) + 1;
            double peso          = Math.round((rnd.nextDouble() * 29 + 1) * 10.0) / 10.0;
            String estado        = ESTADOS[rnd.nextInt(ESTADOS.length)];
            String enfermedad    = ENFERMEDADES[rnd.nextInt(ENFERMEDADES.length)];
            String observaciones = "Observación de mascota #" + i;
            String tratamiento = "Control general y seguimiento";
            String veterinarioAsignado = "Dra. Martínez";
            Cliente cliente = clientes.get(rnd.nextInt(clientes.size()));
            

            mascotaRepository.save(new Mascota(
                    nombreMascota, especie, raza, edad, peso,
                    "default.jpg", estado, enfermedad, observaciones,
                    tratamiento, veterinarioAsignado, cliente));
        }

        System.out.println(" DataLoader: 50 clientes y 100 mascotas generados correctamente.");

        tratamientoRepository.save(new Tratamiento("Vacunación anual", "2024-01-15", 1));
        tratamientoRepository.save(new Tratamiento("Desparasitación", "2024-02-10", 1));
        tratamientoRepository.save(new Tratamiento("Control de peso", "2024-03-05", 1));
        tratamientoRepository.save(new Tratamiento("Cirugía quirurgical", "2024-04-20", 1));
        tratamientoRepository.save(new Tratamiento("Tratamiento de enfermedad", "2024-05-15", 1));
        tratamientoRepository.save(new Tratamiento("Consulta general", "2024-06-10", 1));


        veterinarioRepository.save(new Veterinario("Dra. Elena Martínez", "1234567890", "3101234567", "G0rB7@example.com", "Cirujana Veterinaria", "123456", "default.jpg", "activa"));
        veterinarioRepository.save(new Veterinario("Dr. Ricardo Sánchez", "9876543210", "3109876543", "bQm0Q@example.com", "Medico Internista", "123456", "default.jpg", "activa"));
        veterinarioRepository.save(new Veterinario("Dra. Carla Gomez", "4567890123", "3104567890", "xK0bJ@example.com", "Dermatóloga Veterinaria", "123456", "default.jpg", "activa"));

        List<Veterinario> veterinarios = veterinarioRepository.findAll();

        for (Tratamiento tratamiento : tratamientoRepository.findAll()) {
            Veterinario vet = veterinarios.get(rnd.nextInt(veterinarios.size()));
            tratamiento.setVeterinario(vet);
            tratamientoRepository.save(tratamiento);
        }

        System.out.println(" DataLoader: 6 tratamientos y 3 veterinarios generados correctamente.");
    }
}
