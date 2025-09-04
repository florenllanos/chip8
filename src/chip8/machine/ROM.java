package chip8.machine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ROM {
	Machine machine = new Machine();
	
	public ROM (Machine machine) {
		this.machine = machine;
	}
	
	/*
	 * Read ROM file and load the information to memory machine.
	 */
	private Machine readROMToMemory(Machine m) throws FileNotFoundException, IOException {
		String rutaArchivo = "C:/tmp/pd.ch8";
		FileInputStream fis = new FileInputStream(rutaArchivo);
		m.setMemory(fis.readAllBytes());
		fis.close();
		
		return m;
	}
}
