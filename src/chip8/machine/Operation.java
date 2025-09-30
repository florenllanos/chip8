package chip8.machine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chip8.main.Main;

public class Operation {
	
	private final Logger logger = LoggerFactory.getLogger(Operation.class);
	
	int addr, nibble, x, y, kk;
	
	/*
	 * Execute each instruction read.
	 */
	public void executeInstruction(Machine machine, int instruction) {
		// OP variables (bitwise expressions)
		int op = (instruction >> 12) & 0xF;
		logger.debug("Execute OP {}", Integer.toHexString(op));
		
		switch (op) {
			case 0:
				doOP0x0(machine, instruction);
				break;
			case 1:				
				doOP0x1(machine, instruction);
				break;
			case 2:
				doOP0x2(machine, instruction);
				break;
			case 3:
				doOP0x3(machine, instruction);
				break;
			case 4:
				doOP0x4(machine, instruction);
				break;
			case 5:
				doOP0x5(machine, instruction);
				break;
			case 6:				
				doOP0x6(machine, instruction);
				break;
			case 7:
				doOP0x7(machine, instruction);
				break;
			case 8:
				doOP0x8(machine, instruction);
				break;
			case 9:
				doOP0x9(machine, instruction);
				break;
			case 0xA:
				doOP0xA(machine, instruction);
				break;
			case 0xB:
				doOP0xB(machine, instruction);
				break;
			case 0xC:
				doOP0xC(machine, instruction);
				break;
			case 0xD:				
				doOP0xD(machine, instruction);
				break;
			case 0xE:
				doOP0xE(machine, instruction);				
				break;
			case 0xF:
				doOP0xF(machine, instruction);
				break;
			default:
				logger.debug("OP doesn't exists. OP {}", op);
				break;
		}
	}

	/*
	 * ********************************
	 **********  OP methods ***********
	 **********************************
	 */
	/*
	 * 0x0nnn (addr): jump no code machine in addr.
	 * 0x0EE: CLS
	 * 0x0EE: RET
	 */
	private void doOP0x0(Machine machine, int instruccion) {
		addr = instruccion & 0xFFF;
		if (addr == 0x0E0) { // CLS
			//TODO: Pending to implement CLS.
			logger.debug("0x0E0 CLS");
		} else if (addr == 0x0EE) { //RET
			//TODO: Pending to implement RET.
			logger.debug("0x0EE RET");
		} else { // 0x0nnn, Jump to a machine code routine at nnn.
			//TODO: We must control that this addr is only for SYS: from 0x000 to 0x1FF memory space (interpreter space).			
			machine.setPc(addr);
			logger.debug("0x0nnn addr {}", Integer.toHexString(addr));
		}
	}
	
	// 0x1nnn: Jump to location addr.
	private void doOP0x1(Machine machine, int instruction) {
		addr = instruction & 0xFFF;
		//TODO: Control that addr will be upper of 0x200 (addres where must start user programs).
		machine.setPc(addr);
		//logger.debug("0x1 {}", Integer.toHexString(addr));
	}
	
	// 0x2nnn: Call subroutine at nnn (addr). Involved stack use adding current PC in the stack and then point PC to addr (subroutine addr).
	private void doOP0x2(Machine machine, int instruction) {
		addr = instruction & 0xFFF;
		// Stack movement.
		machine.setSp((byte) (machine.getSp() + 1));
		machine.setStack(machine.getSp(), machine.getPc());
		
		// PC to subroutine address.
		machine.setPc(addr);
		
		//logger.debug("0x2 {}", Integer.toHexString(addr));
	}
	
	// 0x3xkk: Skip next instruction if V[x] == kk and and PC = PC + 2.
	private void doOP0x3(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		kk = instruction & 0xFF;
		
		if (machine.getV(x) == kk) machine.setPc(machine.getPc() + 2);
		
		//System.out.println("OP 3 SE Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
	}

	// 0x4xkk: Skip next instruction if V[x] != kk and PC = PC + 2.
	private void doOP0x4(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		kk = instruction & 0xFF;
		
		if (machine.getV(x) != kk) machine.setPc(machine.getPc() + 2);
		
		//System.out.println("OP 4 SNE Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
	}
	
	// 0x5xy0: Skip next instruction if V[x] == V[y] and PC = PC + 2.
	private void doOP0x5(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		y = (instruction >> 4) & 0xF;
		
		if (machine.getV(x) == machine.getV(y)) machine.setPc(machine.getPc() + 2);
		
		//System.out.println("OP 5 SE Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}

	// 0x6xkk: V[x] = kk.
	private void doOP0x6(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		kk = instruction & 0xFF;		
		
		machine.setV(x, (byte) kk);
		
		logger.debug("Executed instruction 0x6. x {} - kk {}", x, kk);
		
		//System.out.println("OP 6 LD Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
	}
	
	// 0x7xkk: V[x] = V[x] + kk. 
	private void doOP0x7(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		kk = instruction & 0xFF;
		
		machine.setV(x, (byte) (machine.getV(x) + kk));
		
		//System.out.println("OP 7 ADD Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
	}
	
	// Several instructions depending of the lowest 4 bits of the instruction (nibble variable).
	private void doOP0x8(Machine machine, int instruction) {
		nibble = instruction & 0xF;
		x = (instruction >> 8) & 0xF;
		y = (instruction >> 4) & 0xF;
		switch (nibble) {
			case 0:				
				doOP0x8xy0(machine, x, y);
				break;
			case 1:
				doOP0x8xy1(machine, x, y);
				break;
			case 2:
				doOP0x8xy2(machine, x, y);
				break;
			case 3:
				doOP0x8xy3(machine, x, y);
				break;
			case 4:
				doOP0x8xy4(machine, x, y);
				break;
			case 5:
				doOP0x8xy5(machine, x, y);
				break;
			case 6:
				doOP0x8xy6(machine, x, y);
				break;
			case 7:
				doOP0x8xy7(machine, x, y);
				break;
			case 0xE:
				doOP0x8xyE(machine, x, y);
				break;
			default:
				logger.error("Error, OP 0x8 doesn't exists {}", Integer.toHexString(nibble));
		}		
	}

	/*
	 * ********** Ini doOP0x8 cases
	 */

	// 0x8xy0: Vx = Vy.
	private void doOP0x8xy0(Machine machine, int x, int y) {
		machine.setV(x, machine.getV(y));
		//System.out.println("OP 8xy0 LD Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}
	
	// 0x8xy1: Vx = Vx | Vy (OR bitwise operation).
	private void doOP0x8xy1(Machine machine, int x, int y) {		
		machine.setV(x, (byte) (machine.getV(x) | machine.getV(y)));
		//System.out.println("OP 8xy1 OR Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}

	// 0x8xy2: Vx = Vx & Vy (AND bitwise operation).
	private void doOP0x8xy2(Machine machine, int x, int y) {
		machine.setV(x, (byte) (machine.getV(x) & machine.getV(y)));
		//System.out.println("OP 8xy2 AND Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}
	
	// 0x8xy3: Vx = Vx ^ Vy (XOR bitwise operation).
	private void doOP0x8xy3(Machine machine, int x, int y) {		
		machine.setV(x, (byte) (machine.getV(x) ^ machine.getV(y)));
		//System.out.println("OP 8xy3 XOR Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}

	// 0x8xy4: Vx = Vx + Vy, set VF = carry.
	private void doOP0x8xy4(Machine machine, int x, int y) {
		byte res = (byte) ((machine.getV(x) + machine.getV(y)) > machine.getV(x) ? 1 : 0);
		machine.setV(x, res);
		//System.out.println("OP 8xy4 ADD Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}
	
	// 0x8xy5: Vx > Vy, then VF is set to 1. Vx = Vx - Vy.
	private void doOP0x8xy5(Machine machine, int x, int y) {
		byte res = (byte) (machine.getV(x) > machine.getV(y) ? 1 : 0);
		machine.setV(0xF, res);
		machine.setV(x, (byte) (machine.getV(x) - machine.getV(y)));
		//System.out.println("OP 8xy5 SUB Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}
	
	// Least-significant bit of Vx is 1, VF = 1. Vx / 2.
	private void doOP0x8xy6(Machine machine, int x, int y) {
		byte res = (byte) (((machine.getV(x) & 0xF) == 0x1) ? 1 : 0);
		machine.setV(0xF, res);
		machine.setV(x, (byte) (machine.getV(x) >> 1));
		//System.out.println("OP 8xy6 SHR Vx " + Integer.toHexString(machine.getV(x)) + " {, Vy} " + Integer.toHexString(machine.getV(y)));
	}
	
	// Vy > Vx, then VF is set to 1. Vx = Vy - Vx.
	private void doOP0x8xy7(Machine machine, int x, int y) {
		byte res = (byte) (machine.getV(y) > machine.getV(x) ? 1 : 0);
		machine.setV(0xF, res);
		machine.setV(y, (byte) (machine.getV(y) - machine.getV(x)));
		//System.out.println("OP 8xy7 SUBN Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}
	
	// Most-significant bit of Vx is 1, then VF is set to 1. Vx is multiplied by 2.
	private void doOP0x8xyE(Machine machine, int x, int y) {
		byte res = (byte) ((machine.getV(x) & 0x80) == 0x80 ? 1 : 0);
		machine.setV(0xF, res);
		//System.out.println("OP 8xyE SHL Vx " + Integer.toHexString(machine.getV(x)) + " {, Vy} " + Integer.toHexString(machine.getV(y)));
	}
	// ********* End doOP0x8 cases
	
	// Skip next instruction if V[x] != V[y].
	private void doOP0x9(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		y = (instruction >> 4) & 0xF;
		
		if (machine.getV(x) != machine.getV(y)) machine.setPc(machine.getPc() + 2);
		
		//System.out.println("OP 9 SNE Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)));
	}
	
	// 0xAnnn: I = nnn (addr).
	private void doOP0xA(Machine machine, int instruction) {
		addr = instruction & 0xFFF;
		machine.setI(addr);
		//System.out.println("OP A LD " + Integer.toHexString(machine.getI()) + " addr " + Integer.toHexString(addr));
	}

	// 0xBnnn: PC = nnn + V0.
	private void doOP0xB(Machine machine, int instruction) {
		addr = instruction & 0xFFF;
		machine.setPc(addr + machine.getV(0));
		//System.out.println("OP B JP V0 " + Integer.toHexString(machine.getV(0)) + " addr " + Integer.toHexString(addr));
	}
	
	// 0xCxkk: V[x] = random & kk.
	private void doOP0xC(Machine machine, int instruction) {
		int rnd = (int)(Math.random() * 256); // random number from 0 to 255.
		x = (instruction >> 8) & 0xF;
		kk = instruction & 0xFF;
		machine.setV(x, (byte) (kk & rnd));
		//System.out.println("OP C RND Vx " + Integer.toHexString(machine.getV(x)) + " byte " + Integer.toHexString(kk));
	}
	
	// 0xDxyn: 
	// TODO: OP related with drawing on the screen.
	private void doOP0xD(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		y = (instruction >> 4) & 0xF;
		nibble = instruction & 0xF;
		//System.out.println("OP D DRW Vx " + Integer.toHexString(machine.getV(x)) + " Vy " + Integer.toHexString(machine.getV(y)) + " nibble " + Integer.toHexString(nibble));
		logger.debug("0xD operation related with drawing on the screen. x {}, y {}, nibble {}", Integer.toHexString(x), Integer.toHexString(y), Integer.toHexString(nibble));
	}
	
	// 0xE
	// TODO: OP related with keyboard.
	private void doOP0xE(Machine machine, int instruction) {
		x = (instruction >> 8) & 0xF;
		nibble = instruction & 0xF;
		if (nibble == 0xE) { // Ex9E
			//System.out.println("OP E SKP Vx " + Integer.toHexString(machine.getV(x)));
			logger.debug("0xEx9E operation related with keyboard. x {}, nibble {}", Integer.toHexString(x), Integer.toHexString(nibble));
		} else { // ExA1
			//System.out.println("OP E SKNP Vx " + Integer.toHexString(machine.getV(x)));
			logger.debug("0xExA1 operation related with keyboard. x {}, nibble {}", Integer.toHexString(x), Integer.toHexString(nibble));
		}
	}
	
	/*
	 ************  Ini OP 0xF cases
	 */
	private void doOP0xF(Machine machine, int instruction) {
		kk = instruction & 0xFF;
		x = (instruction >> 8) & 0xF;
		switch (kk) {
			case 0x07:
				doOP0xFx07(machine, x);
				break;
			case 0x0A:
				doOP0xFxA(machine, x);
				break;
			case 0x15:
				doOP0xFx15(machine, x);
				break;
			case 0x18:
				doOP0xFx18(machine, x);
				break;
			case 0x1E:
				doOP0xFx1E(machine, x);
				break;
			case 0x29:
				doOP0xFx29(machine, x);
				break;
			case 0x33:
				doOP0xFx33(machine, x);
				break;
			case 0x55:
				doOP0xFx55(machine, x);
				break;
			case 0x65:
				doOP0xFx65(machine, x);
				break;
			default:
				logger.error("Error, OP 0xF doesn't exists {}", kk);						
		}
		//System.out.println("OP F");		
	}	

	// 0x0Fx07: V[x] = DT.
	private void doOP0xFx07(Machine machine, int x) {
		machine.setV(x, machine.getDt());	
		//System.out.println("OP Fx07 LD Vx " + Integer.toHexString(machine.getV(x)) + " DT " + Integer.toHexString(machine.getDt()));
	}	

	// 0xFxA: 
	// TODO: OP related with keyboard.
	private void doOP0xFxA(Machine machine, int x) {
		//System.out.println("OP Fx0A LD Vx " + Integer.toHexString(machine.getV(x)) + " Key press");
		logger.error("0xFxA OP related with keyboard. x {}", Integer.toHexString(x));
	}	

	// OXFx15: DT = V[x].
	private void doOP0xFx15(Machine machine, int x) {
		machine.setDt(machine.getV(x));	
		//System.out.println("OP Fx15 LD DT Vx " + Integer.toHexString(machine.getV(x)));
	}

	// 0xFx18: ST = V[x].
	private void doOP0xFx18(Machine machine, int x) {
		machine.setSt(machine.getV(x));
		//System.out.println("OP Fx18 LD ST Vx " + Integer.toHexString(machine.getV(x)));
	}

	// 0xFx1E: I = I + V[x].
	private void doOP0xFx1E(Machine machine, int x) {
		machine.setI(machine.getI() + machine.getV(x));
		//System.out.println("OP Fx1E ADD I " + Integer.toHexString(machine.getI()) + " Vx " + Integer.toHexString(machine.getV(x)));
	}

	// 0xFx29:
	// TODO: Op code related with drawing screen.
	private void doOP0xFx29(Machine machine, int x) {
		//System.out.println("OP Fx29 LD F (Sprite instruction) Vx " + Integer.toHexString(machine.getV(x)));
	}

	// 0xFx33: Store in memory value V[x] in positions I, I+1, I+2.
	private void doOP0xFx33(Machine machine, int x) {
		int valueVx = machine.getV(x);
		machine.setMemory(machine.getI(), (byte) ((valueVx / 100) % 10));  // Hundred place.
		machine.setMemory(machine.getI() + 1, (byte) ((valueVx / 10) % 10)); // Decimal place.
		machine.setMemory(machine.getI() + 2, (byte) (valueVx % 10)); // Unit place.
		//System.out.println("OP Fx33 LD BCD Vx " + Integer.toHexString(machine.getV(x)));
	}

	// 0xFx55: Load in memory[I] registers value: V[0] until V[x].
	private void doOP0xFx55(Machine machine, int x) {
		for (int i = 0; i <= x; i++) {
			machine.setMemory(machine.getI() + i, machine.getV(i));
		}
		//System.out.println("OP Fx55 LD [I] " + Integer.toHexString(machine.getI()) + " Vx " + Integer.toHexString(machine.getV(x)));
	}

	// 0xFx65: Read from memory[I] registers an load into registers: V[0] until V[x].
	private void doOP0xFx65(Machine machine, int x) {
		for (int i = 0; i <=x; i++) {
			machine.setV(i, machine.getMemory(machine.getI() + i));
		}
		//System.out.println("OP Fx65 LD Vx " + Integer.toHexString(machine.getV(x)) + " [I] " + Integer.toHexString(machine.getI()));
	}
	// ********* End OP 0xF cases

}
