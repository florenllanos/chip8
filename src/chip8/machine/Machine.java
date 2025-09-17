package chip8.machine;

public class Machine {

	// Memory chip8 space.
	private byte[] memory = new byte[4096];
	
	// General purpose register, 8 bits. 16 since v[0x01] until v[0x0F]
	private byte[] v = new byte[16];
	
	// Special register i, 16 bits (normaly are only used the lowest 12 bits). To store memory address mainly.
	private int i = 0;
	
	// Special registers for time and sound. 8 bits.
	private byte st = 0;  // sound timer.
	private byte dt = 0;  // delay timer.
	
	// Program counter. 16 bits.
	private int pc = 0;
	
	// Stack pointer. 8 bits.
	private byte sp = 0;
	
	// Stack 16 of 16 bits.
	private int[] stack = new int[15];
	
	// Getters and Setters.
	public byte[] getMemory() {
		return memory;
	}
	
	public byte getMemory(int i) {
		return memory[i];
	}

	public void setMemory(byte[] memory) {
		this.memory = memory;
	}
	
	public void setMemory(int i, byte memory) {
		this.memory[i] = memory;
	}

	public byte getV(int i) {
		return v[i];
	}

	public void setV(int i, byte v) {
		this.v[i] = v;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public byte getSt() {
		return st;
	}

	public void setSt(byte st) {
		this.st = st;
	}

	public byte getDt() {
		return dt;
	}

	public void setDt(byte dt) {
		this.dt = dt;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public byte getSp() {
		return sp;
	}

	public void setSp(byte sp) {
		this.sp = sp;
	}

	public int getStack(int i) {
		return stack[i];
	}

	public void setStack(int i, int stackValue) {
		this.stack[i] = stackValue;
	}	
}
