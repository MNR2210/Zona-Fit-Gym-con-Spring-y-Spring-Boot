package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación");
		// Levantar la fábrica de spring
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicación Finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		boolean salir = false;
		Scanner consola = new Scanner(System.in);
		while (!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner consola){
		logger.info("""
		\n*** Aplicación Zona Fit Gym ***\n
		1. Listar clientes
		2. Buscar cliente
		3. Agregar cliente
		4. Modificar cliente
		5. Eliminar cliente
		6. Salir\n
		Elige una opción:\s""");
		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		boolean salir = false;
		switch (opcion){
			case 1 -> {
				logger.info(nl + "--- Listado de clientes ---" + nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
			}
			case 2 -> {
				logger.info("--- Buscar cliente po id ---" + nl);
				logger.info("ID Cliente a buscar:\s");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					logger.info("Cliente encontrado: " + cliente + nl);
				}
				else {
					logger.info("Cliente no encontrado: " + cliente + nl);
				}
			}
			case 3 -> {
				logger.info("--- Agregar cliente ---" + nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Membresia: ");
				var	membresia = Integer.parseInt(consola.nextLine());
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);
				logger.info("Cliente agregado: " + cliente + nl);
			}
			case 4 -> {
				logger.info("--- Modificar Cliente ---" + nl);
				logger.info("Ingrese ID del cliente a modificar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Membresia: ");
					var membresia = Integer.parseInt(consola.nextLine());
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modificado: " + cliente + nl);
				}
				else
					logger.info("Cliente no modificado: " + cliente + nl);

			}
			case 5 -> {
				logger.info("--- Eliminar Cliente ---" + nl);
				logger.info("Ingrese el ID del cliente a eliminar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				var cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: " + cliente + nl);
				}
				else
					logger.info("Cliente no encontrado: " + cliente + nl);

			}
			case 6 -> {
					logger.info("*** Cerrando aplicación ***" + nl + nl);
					salir = true;
			}
			default -> logger.info("Opción no reconocida: " + opcion + nl);
		}
		return salir;
	}
}