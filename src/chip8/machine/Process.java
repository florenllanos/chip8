package chip8.machine;

public class Process {
	
	//Machine m = new Machine();
	
	/*
	 * Execute each instruction read.
	 */
	public void executeInstruction(Machine machine, int instruccion) {
		// OP variables (bitwise expressions)
		int addr, nibble, x, y, kk;
		int op = (instruccion >> 12) & 0xF;
		
		switch (op) {
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
				System.out.println("OP 3 SE Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
				break;
			case 4:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 4 SNE Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
				break;
			case 5:
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				System.out.println("OP 5 SE Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
				break;
			case 6:				
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 6 LD Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
				break;
			case 7:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP 7 ADD Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
				break;
			case 8:
				nibble = instruccion & 0xF;
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				switch (nibble) {
					case 0:
						System.out.println("OP 8xy0 LD Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 1:
						System.out.println("OP 8xy1 OR Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 2:
						System.out.println("OP 8xy2 AND Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 3:
						System.out.println("OP 8xy3 XOR Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 4:
						System.out.println("OP 8xy4 ADD Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 5:
						System.out.println("OP 8xy5 SUB Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 6:
						System.out.println("OP 8xy6 SHR Vx " + Integer.toHexString(machine.getV(x)) + " {, Vy} " + Integer.toHexString(machine.getV(y)));
						break;
					case 7:
						System.out.println("OP 8xy7 SUBN Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
						break;
					case 0xE:
						System.out.println("OP 8xyE SHL Vx " + Integer.toHexString(machine.getV(x)) + " {, Vy} " + Integer.toHexString(machine.getV(y)));
						break;
					default:
						System.out.println("Error instrucción 8, no existe");
				}
				break;
			case 9:
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				System.out.println("OP 9 SNE Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
				break;
			case 0xA:
				addr = instruccion & 0xFFF;
				System.out.println("OP A LD " + Integer.toHexString(machine.getI()) + " addr " + Integer.toHexString(addr));
				break;
			case 0xB:
				addr = instruccion & 0xFFF;
				System.out.println("OP B JP V0 " + Integer.toHexString(machine.getV(0)) + " addr " + Integer.toHexString(addr));
				break;
			case 0xC:
				x = (instruccion >> 8) & 0xF;
				kk = instruccion & 0xFF;
				System.out.println("OP C RND Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
				break;
			case 0xD:				
				x = (instruccion >> 8) & 0xF;
				y = (instruccion >> 4) & 0xF;
				nibble = instruccion & 0xF;
				System.out.println("OP D DRW Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)) + " nibble " + Integer.toHexString(nibble));
				break;
			case 0xE:
				x = (instruccion >> 8) & 0xF;
				nibble = instruccion & 0xF;
				if (nibble == 0xE) { // Ex9E
					System.out.println("OP E SKP Vx " + Integer.toHexString(machine.getV(x)));
				} else { // ExA1
					System.out.println("OP E SKNP Vx " + Integer.toHexString(machine.getV(x)));
				}				
				break;
			case 0xF:
				kk = instruccion & 0xFF;
				x = (instruccion >> 8) & 0xF;
				switch (kk) {
					case 0x07:
						System.out.println("OP Fx07 LD Vx " + Integer.toHexString(machine.getV(x)) + " DT " + Integer.toHexString(machine.getDt()));
						break;
					case 0x0A:
						System.out.println("OP Fx0A LD Vx " + Integer.toHexString(machine.getV(x)) + " Key press");
						break;
					case 0x15:
						System.out.println("OP Fx15 LD DT Vx " + Integer.toHexString(machine.getV(x)));
						break;
					case 0x18:
						System.out.println("OP Fx18 LD ST Vx " + Integer.toHexString(machine.getV(x)));
						break;
					case 0x1E:
						System.out.println("OP Fx1E ADD I " + Integer.toHexString(machine.getI()) + " Vx " + Integer.toHexString(machine.getV(x)));
						break;
					case 0x29:
						System.out.println("OP Fx29 LD F (Sprite instruction) Vx " + Integer.toHexString(machine.getV(x)));
						break;
					case 0x33:
						System.out.println("OP Fx33 LD BCD Vx " + Integer.toHexString(machine.getV(x)));
						break;
					case 0x55:
						System.out.println("OP Fx55 LD [I] " + Integer.toHexString(machine.getI()) + " Vx " + Integer.toHexString(machine.getV(x)));
						break;
					case 0x65:
						System.out.println("OP Fx65 LD Vx " + Integer.toHexString(machine.getV(x)) + " [I] " + Integer.toHexString(machine.getI()));
						break;
					default:
						System.out.println("Error instrucción F, no existe");						
				}
				System.out.println("OP F");
				break;
			default:
				System.out.println("ERROR");
				break;
		}
	}
}
