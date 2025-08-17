package esercizi;

import java.io.IOException;

public class FileSystemAccessError extends IOException {
	
	/* Questa exception applicativa memorizzerà: 
	 * - L’exception originaria (IOException)
	 * - Il nome del metodo andato in exception
	 * - Le informazioni sul file corrente che ha generato l'exception
	 *  */
	
	private String methodName;
	
	private Exception ex;
	
	private String message;
	
	private String fileName;

	public FileSystemAccessError(String methodName, Exception ex, String message, String fileName) {
		super();
		this.methodName = methodName;
		this.ex = ex;
		this.message = message;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMethodName() {
		return methodName;
	}

	public Exception getEx() {
		return ex;
	}

	public String getMessage() {
		return message;
	}
	
	
}
