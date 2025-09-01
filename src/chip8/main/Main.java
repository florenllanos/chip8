package chip8.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import chip8.machine.Machine;

public class Main {
	

	public static void main(String[] args) {		
		Machine machine = new Machine();
		
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
				
				int instruccion = byteAlto << 8 | byteBajo;
								
				main.executeInstruction(machine, instruccion);
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
		m.setMemory(fis.readAllBytes());
		fis.close();
	}
	
	/*
	 * Execute each instruction read.
	 */
	private void executeInstruction(Machine machine, int instruccion) {
		// OP variables (bitwise expressions)
		int op = (instruccion >> 12) & 0xF;
		int addr = instruccion & 0xFFF;
		int nibble = instruccion & 0xF;
		int x = (instruccion >> 8) & 0xF;
		int y = (instruccion >> 4) & 0xF;
		int kk = instruccion & 0xFF;
		
		switch(op) {
			case 0:
				addr = instruccion & 0xFFF;
				if (addr == 0x0E0) {
					System.out.println("OP CLS");
				} else if (addr == 0x0EE) {
					System.out.println("OP RET");					
				} else {
					System.out.println("OP SYS addr " + Integer.toHexString(addr));
				}
				break;
			case 1:				
				addr = instruccion & 0xFFF;
				System.out.println("OP 1 JP addr " + Integer.toHexString(addr));
				break;
			case 2:
				addr = instruccion & 0xFFF;
				System.out.println("OP 2 CALL addr " + Integer.toHexString(addr));
				break;
			case 3:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 3 SE Vx " + Integer.toHexString(x) + " byte " + Integer.toHexString(kk));
				break;
			case 4:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 4 SNE Vx " + Integer.toHexString(x) + " byte " + Integer.toHexString(kk));
				break;
			case 5:
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				System.out.println("OP 5 SE Vx " + Integer.toHexString(x) + " Vy " + Integer.toHexString(y));
				break;
			case 6:				
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 6 LD Vx " + Integer.toHexString(x) + " byte " + Integer.toHexString(kk));
				break;
			case 7:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 7 ADD Vx " + Integer.toHexString(x) + " byte " + Integer.toHexString(kk));
				break;
			case 8:
				// TODO: Pendiente. Tiene varias opciones. Nuevo switch.
				System.out.println("OP 8");
				break;
			case 9:
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				//TODO: no devolver directamente x o y, tiene que devolverse el valor del registro machine.getV(i).
				System.out.println("OP 9 SNE Vx " + Integer.toHexString(x) + " Vy " + Integer.toHexString(y));
				break;
			case 10:
				addr = instruccion & 0xFFF;
				System.out.println("OP A LD " + machine.getI() + " addr " + Integer.toHexString(addr));
				break;
			case 11:
				addr = instruccion & 0xFFF;
				System.out.println("OP B JP V0 " + machine.getV(0) + " addr " + Integer.toHexString(addr));
				break;
			case 12:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP C RND Vx " + machine.getV(x) + " byte " + Integer.toHexString(kk));
				break;
			case 13:				
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				nibble = instruccion & 0xF;
				//TODO: envolver cada valor de machine.get con Integer.toHexString(<valor>) para poder visualizarlo por consola.
				System.out.println("OP D DRW Vx " + machine.getV(x) + " Vy " + machine.getV(y) + " nibble " + nibble);
				break;
			case 14:
				x = (instruccion >> 8) & 0xF;
				nibble = instruccion & 0xF;
				if (nibble == 0xE) { // Ex9E
					System.out.println("OP E SKP Vx " + machine.getV(x));
				} else { // ExA1
					System.out.println("OP E SKNP Vx " + machine.getV(x));
				}				
				break;
			case 15:
				System.out.println("OP F");
				break;
			default:
				System.out.println("ERROR");
				break;
		}
			 
	}
}
