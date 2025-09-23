package chip8.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chip8.machine.Machine;
import chip8.machine.Operation;

public class Main {
	
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
		
	public static void main(String[] args) {		
		Machine machine = new Machine();
		
		// Logger initial state of the machine.
		logger.debug("Machine initial state {}", machine);
		logger.debug("Machine memory initial state {}",        machine.getMemory());
		logger.debug("Machine v registers initial state {}",   machine.getV());
		logger.debug("Machine i register initial state {}",    machine.getI());
		logger.debug("Machine dt register initial state {}",   machine.getDt());
		logger.debug("Machine st register initial state {}",   machine.getSt());
		logger.debug("Machine pc register initial state {}",   machine.getPc());
		logger.debug("Machine sp register initial state {}",   machine.getSp());
		logger.debug("Machine stack initial state {}",         machine.getStack());
		
		Operation process = new Operation();
		
		Main main = new Main();

		//main.ciclo(machine);
		
		// Leemos fichero.
		try {			
			main.readROMToMemory(machine, args[0]);
			byte[] mem = machine.getMemory();
			
			int i = 0;
			machine.setPc(i);
			byte byteAlto, byteBajo = 0;
			while (machine.getPc() < mem.length) {
				byteAlto = mem[machine.getPc()];				
				machine.setPc(++i);
				if (machine.getPc() < mem.length) { 
					byteBajo = mem[machine.getPc()];					
				}				

				int instruction = byteAlto << 8 | byteBajo;
				
				
				logger.debug("Instruction {}", Integer.toHexString(instruction));
				// Logging the machine state before execute operation
				logger.debug("Machine memory before execution {}",        machine.getMemory());
				logger.debug("Machine v registers before execution {}",   machine.getV());
				logger.debug("Machine i register before execution {}",    machine.getI());
				logger.debug("Machine dt register before execution {}",   machine.getDt());
				logger.debug("Machine st register before execution {}",   machine.getSt());
				logger.debug("Machine pc register before execution {}",   machine.getPc());
				logger.debug("Machine sp register before execution {}",   machine.getSp());
				logger.debug("Machine stack before execution {}",         machine.getStack());
				
				process.executeInstruction(machine, instruction);								
				machine.setPc(++i);				

				// Logging the machine state after execute operation
				logger.debug("Machine memory after execution {}",        machine.getMemory());
				logger.debug("Machine v registers after execution  {}",   machine.getV());
				logger.debug("Machine i register after execution  {}",    machine.getI());
				logger.debug("Machine dt register after execution  {}",   machine.getDt());
				logger.debug("Machine st register after execution  {}",   machine.getSt());
				logger.debug("Machine pc register after execution  {}",   machine.getPc());
				logger.debug("Machine sp register after execution  {}",   machine.getSp());
				logger.debug("Machine stack after execution  {}",         machine.getStack());
			}

		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void ciclo(Machine m) {
		System.out.println("Hola");
	}
	
	/*
	 * Read ROM file and load the information to memory machine.
	 */
	private void readROMToMemory(Machine m, String romPath) throws FileNotFoundException, IOException {
		//String rutaArchivo = "C:/tmp/pd.ch8";
		//FileInputStream fis = new FileInputStream(rutaArchivo);
		FileInputStream fis = new FileInputStream(romPath);
		// TODO: Programs must start at 0x200. Prior memory location are for the interpreter.
		m.setMemory(fis.readAllBytes());
		fis.close();
	}
}