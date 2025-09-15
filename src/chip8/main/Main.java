package chip8.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import chip8.machine.Machine;
import chip8.machine.Process;

public class Main {
	
	public static void main(String[] args) {		
		Machine machine = new Machine();
		Process process = new Process();
		
		Main main = new Main();

		//main.ciclo(machine);
		
		// Leemos fichero.
		try {
			main.readROMToMemory(machine);
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
				
				//TODO: Rename instruccion to english, instruction.
				int instruccion = byteAlto << 8 | byteBajo;

				process.executeInstruction(machine, instruccion);
				machine.setPc(++i);				
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
	private void readROMToMemory(Machine m) throws FileNotFoundException, IOException {
		String rutaArchivo = "C:/tmp/pd.ch8";
		FileInputStream fis = new FileInputStream(rutaArchivo);
		// TODO: Programs must start at 0x200. Prior memory location are for the interpreter.
		m.setMemory(fis.readAllBytes());
		fis.close();
	}
}
