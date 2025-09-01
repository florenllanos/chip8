package chip8.machine;

public class Machine {
	// Memory chip8 space.
	private byte[] memory = new byte[4096];
	
	// General purpose register, 8 bits. 16 since v[0x01] until v[0x0F]
	private byte[] v = new byte[15];
	
	// Special register i, 16 bits (normaly are only used the lowest 12 bits). To store memory address mainly.
	private int i;
	
	// Special registers for time and sound. 8 bits.
	private short sound;
	private short timers;
	
	// Program counter. 16 bits.
	private int pc;
	
	// Stack pointer. 8 bits.
	private int sp;
	
	// Stack 16 of 16 bits.
	private int[] stack = new int[15];
	
	// Getters and Setters.
	// TODO: Check to use Lombok in future or record class instead a classic Bean class.
	public byte[] getMemory() {
		return memory;
	}

	public void setMemory(byte[] memory) {
		this.memory = memory;
	}

	public byte getV(int i) {
		return v[i];
	}

	public void setV(int ib, byte v) {
		this.v[i] = v;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public short getSound() {
		return sound;
	}

	public void setSound(short sound) {
		this.sound = sound;
	}

	public short getTimers() {
		return timers;
	}

	public void setTimers(short timers) {
		this.timers = timers;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public int[] getStack() {
		return stack;
	}

	public void setStack(int[] stack) {
		this.stack = stack;
	}	
}
