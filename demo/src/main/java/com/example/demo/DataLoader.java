package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entities.*;
import com.example.demo.repository.*;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired private AdminRepository            adminRepository;
    @Autowired private ClienteRepository          clienteRepository;
    @Autowired private DrogaRepository            drogaRepository;
    @Autowired private MascotaRepository          mascotaRepository;
    @Autowired private TratamientoRepository      tratamientoRepository;
    @Autowired private TratamientoDrogaRepository tratamientoDrogaRepository;
    @Autowired private VeterinarioRepository      veterinarioRepository;

    // ── Datos de prueba ────────────────────────────────────────────────────────

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

    private static final String[] ESPECIES     = {"Perro", "Gato"};
    private static final String[] RAZAS_PERRO  = {"Labrador", "Bulldog", "Poodle", "Beagle", "Chihuahua"};
    private static final String[] RAZAS_GATO   = {"Persa", "Siamés", "Maine Coon", "Ragdoll", "Bengalí"};
    private static final String[] ESTADOS      = {"activo", "activo", "activo", "inactivo"};
    private static final String[] ENFERMEDADES = {"vacío", "Otitis", "Dermatitis", "Obesidad", "Anemia"};

    // ── Runner ─────────────────────────────────────────────────────────────────

    @Override
    public void run(String... args) throws Exception {

        // Idempotencia: si ya hay datos no vuelve a cargar
        if (clienteRepository.count() > 0) {
            return;
        }

        Random rnd = new Random(42);

        // ── 1. Admins ──────────────────────────────────────────────────────────
        adminRepository.save(new Admin(null, "Carlos",   "Ruiz",    "admin1@vet.com", "admin123"));
        adminRepository.save(new Admin(null, "Fernanda", "Torres",  "admin2@vet.com", "admin123"));
        adminRepository.save(new Admin(null, "Ricardo",  "Molina",  "admin3@vet.com", "admin123"));
        adminRepository.save(new Admin(null, "Paola",    "Vargas",  "admin4@vet.com", "admin123"));
        adminRepository.save(new Admin(null, "Mauricio", "Herrera", "admin5@vet.com", "admin123"));
        System.out.println("DataLoader: 5 admins generados.");

        // ── 2. Veterinarios ────────────────────────────────────────────────────
        veterinarioRepository.save(new Veterinario("Elena Martínez",  "10000001", "3101000001", "elena@vet.com",   "Cirugía Veterinaria",      "pass123", "default.jpg", "activo"));
        veterinarioRepository.save(new Veterinario("Ricardo Sánchez", "10000002", "3101000002", "ricardo@vet.com", "Medicina Interna",         "pass123", "default.jpg", "activo"));
        veterinarioRepository.save(new Veterinario("Carla Gómez",     "10000003", "3101000003", "carla@vet.com",   "Dermatología Veterinaria", "pass123", "default.jpg", "activo"));
        veterinarioRepository.save(new Veterinario("Julián Ospina",   "10000004", "3101000004", "julian@vet.com",  "Oftalmología Veterinaria", "pass123", "default.jpg", "activo"));
        veterinarioRepository.save(new Veterinario("Marcela Rueda",   "10000005", "3101000005", "marcela@vet.com", "Odontología Veterinaria",  "pass123", "default.jpg", "inactivo"));
        System.out.println("DataLoader: 5 veterinarios generados.");

        // ── 3. Drogas ──────────────────────────────────────────────────────────
        drogaRepository.save(new Droga(null, "Amoxicilina",   8500f,  15000f, 100, 0));
        drogaRepository.save(new Droga(null, "Ivermectina",   4200f,   8000f,  80, 0));
        drogaRepository.save(new Droga(null, "Meloxicam",    12000f,  22000f,  60, 0));
        drogaRepository.save(new Droga(null, "Metronidazol",  5500f,  10500f,  90, 0));
        drogaRepository.save(new Droga(null, "Furosemida",    3800f,   7500f,  70, 0));
        System.out.println("DataLoader: 5 drogas generadas.");

        // ── 4. Clientes ────────────────────────────────────────────────────────
        for (int i = 1; i <= 50; i++) {
            String nombre   = NOMBRES[rnd.nextInt(NOMBRES.length)];
            String apellido = APELLIDOS[rnd.nextInt(APELLIDOS.length)];
            String correo   = nombre.toLowerCase() + i + "@email.com";
            String celular  = "31" + String.format("%08d", i);
            clienteRepository.save(new Cliente(nombre, apellido, correo, "pass" + i, celular));
        }
        System.out.println("DataLoader: 50 clientes generados.");

        // ── 5. Mascotas ────────────────────────────────────────────────────────
        List<Cliente> clientes = clienteRepository.findAll();

        for (int i = 1; i <= 100; i++) {
            String especie = ESPECIES[rnd.nextInt(ESPECIES.length)];
            String raza    = "Perro".equals(especie)
                             ? RAZAS_PERRO[rnd.nextInt(RAZAS_PERRO.length)]
                             : RAZAS_GATO[rnd.nextInt(RAZAS_GATO.length)];

            mascotaRepository.save(new Mascota(
                NOMBRES_MASCOTAS[rnd.nextInt(NOMBRES_MASCOTAS.length)] + i,
                especie,
                raza,
                rnd.nextInt(15) + 1,
                Math.round((rnd.nextDouble() * 29 + 1) * 10.0) / 10.0,
                "default.jpg",
                ESTADOS[rnd.nextInt(ESTADOS.length)],
                ENFERMEDADES[rnd.nextInt(ENFERMEDADES.length)],
                "Observación de mascota #" + i,
                clientes.get(rnd.nextInt(clientes.size()))
            ));
        }
        System.out.println("DataLoader: 100 mascotas generadas.");

        // ── 6. Tratamientos ────────────────────────────────────────────────────
        List<Mascota>     mascotas     = mascotaRepository.findAll();
        List<Veterinario> veterinarios = veterinarioRepository.findAll();

        String[] descripciones = {
            "Vacunación anual", "Desparasitación", "Control de peso",
            "Cirugía programada", "Tratamiento de infección", "Consulta general"
        };

        for (String descripcion : descripciones) {
            Mascota     mascota = mascotas.get(rnd.nextInt(mascotas.size()));
            Veterinario vet     = veterinarios.get(rnd.nextInt(veterinarios.size()));
            // Fecha aleatoria dentro de 2024
            LocalDate fecha = LocalDate.of(2024, rnd.nextInt(12) + 1, rnd.nextInt(28) + 1);
            tratamientoRepository.save(new Tratamiento(descripcion, fecha, mascota, vet));
        }
        System.out.println("DataLoader: 6 tratamientos generados.");

        // ── 7. TratamientoDroga (tabla intermedia — 5 registros al azar) ───────
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();
        List<Droga>       drogas       = drogaRepository.findAll();

        for (int i = 0; i < 5; i++) {
            Tratamiento tratamiento = tratamientos.get(rnd.nextInt(tratamientos.size()));
            Droga       droga       = drogas.get(rnd.nextInt(drogas.size()));
            int         cantidad    = rnd.nextInt(3) + 1;  // entre 1 y 3 unidades

            tratamientoDrogaRepository.save(new TratamientoDroga(tratamiento, droga, cantidad));

            // Actualizar inventario de la droga al registrar el uso
            droga.setUnidadesVendidas(droga.getUnidadesVendidas() + cantidad);
            droga.setUnidadesDisponibles(Math.max(0, droga.getUnidadesDisponibles() - cantidad));
            drogaRepository.save(droga);
        }
        System.out.println("DataLoader: 5 registros en TratamientoDroga generados.");

        System.out.println("DataLoader: OK");
    }
}
