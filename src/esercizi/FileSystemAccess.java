package esercizi;

import java.io.File;
import java.util.Optional;

public class FileSystemAccess implements FileSystem {
	
	private File file;
	
	private String filePath;  // path relativo
	
	
	
	/*------------------------
    	   CONSTRUCTORS
	--------------------------*/
	public FileSystemAccess() {
		
	};
	
	public FileSystemAccess(String fileNamePath) {
		setFile(new File(fileNamePath));
		setFileNamePath(fileNamePath);
	};
	
	public FileSystemAccess(File file) {
		setFile(file);
		setFileNamePath(getFilePath());
	};

	
	
	/*------------------------
	    GETTERS AND SETTERS
	--------------------------*/
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	
	/*------------------------
	    OVERRIDED METHODS
	--------------------------*/
	@Override //OK
	public void setFileNamePath(String path) {
		this.filePath = path;
	}

	@Override //OK
	public String getFileName() {
		// Se il file è presente ritorno il nome, altrimenti stringa vuota.
		if (getFileCurrent().isPresent()) {
			return this.file.getName();
		} else {
			return "";
		}
	}

	@Override //OK
	public String getFilePath() {
		// Se il file è presente ritorno il path assoluto, altrimenti stringa vuota.
		if (this.getFileCurrent().isPresent()) {
			return this.file.getAbsolutePath();
		} else {
			return "";
		}
	}

	@Override //OK
	public Optional<File> getFileCurrent() {
		// Ritorno un optional, se this.file è vuoto, "ofNullable" restituisce un Optional.empty().
		return Optional.ofNullable(this.file);
	}

	@Override //OK
	public boolean isFile() {
		// Se il file è null ritorno false per evitare che restituisca "NullPointerException"
		if (this.getFileCurrent().isEmpty()) {
			return false;
		}
		
		// Altrimenti faccio valutare al metodo
		return this.file.isFile();
	}

	@Override //OK
	public boolean isDirectory() {
		// Se il file è null ritorno false
		if (this.getFileCurrent().isEmpty()) {
			return false;
		}
		
		// Altrimenti faccio valutare al metodo
		return this.file.isDirectory();
	}

	@Override //OK
	public boolean isHidden() throws FileSystemAccessError {
		// Se il file è null non può essere nè visibile nè nascosto, quindi lancio l'exception
		if (this.getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("isHidden", null, "Il file non esiste", this.getFileName());
		}
		
		return this.file.isHidden();
	}

	@Override
	public boolean isWriteble() throws FileSystemAccessError {
		// Se il file è null non può essere nè scrivibile nè non scrivibile, quindi lancio l'exception
		if (this.getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("isWriteble", null, "Il file non esiste", this.getFileName());
		}
		
		return this.file.canWrite();
	}

	@Override
	public boolean isReadeble() throws FileSystemAccessError {
		// Se il file è null non può essere nè leggibile nè non leggibile, quindi lancio l'exception
		if (this.getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("isWriteble", null, "Il file non esiste", this.getFileName());
		}
		
		return this.file.canRead();
	}

	// Questo metodo restituisce la cartella madre
	@Override
	public Optional<File> folderOwner() {
		// Se il file è presente ritorno la cartella madre, altrimenti un Optional.empty()
		if (this.getFileCurrent().isPresent()) {
			return Optional.of(this.file.getParentFile());
		} else {
			return Optional.empty();
		}
				
	}

	@Override
	public File[] folderFilesName() {
		// Se il file (ovvero la folder) è presente ritorno la lista di nomi dei file interni, altrimenti un array vuoto.
		if (this.getFileCurrent().isPresent()) {
			return this.file.listFiles();
		} else {
			return new File[0];
		}
	}

	@Override
	public long fileSize() throws FileSystemAccessError {
		
		// Se il file è stato istanziato, ed è un file e non una folder, ritorno la sua dimensione.
		if (this.getFileCurrent().isPresent() && this.isFile()) {
			return this.file.length();
		}
		
		// ALTRIMENTI LANCIO L'EXCEPTION APPLICATIVA
		throw new FileSystemAccessError("fileSize", null, "Il file non esiste", this.getFileName());
	}

	@Override
	public long fileSizeNested() throws FileSystemAccessError {
		
		// Se il file è stato istanziato, ed è un file e non una folder, ritorno la sua dimensione.
		if (this.getFileCurrent().isPresent() && this.isFile()) {
			return this.file.length();
			
		} else if (this.getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("fileSize", null, "Il file non esiste", this.getFileName());
		}
		
		long totalFileSize = 0;
		
		// Prendo tutti i file interni della cartella
		File[] folderFiles = this.folderFilesName();
		
		// Per ogni file interno, se è un file aggiungo la sua dimensione al totale
		for (File currentFile : folderFiles) {
			if (currentFile.isFile()) {
				totalFileSize += currentFile.length();
				
			} else if (currentFile.isDirectory()) {
				/* Chiamo la mia funzione ricorsiva che mi calcola la dimensione di tutti i file interni,
				le sotto cartelle e tutti i loro file interni. */
				totalFileSize += calculateFolderSize(currentFile);
			}
			
		}
		
		return totalFileSize;
	}

	@Override
	public boolean mkDir(File dirPath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mkDir(String dirName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean emptyDir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDir(File dirName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDir(String dirName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] dirNested(File dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] dirParents(File dir) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long calculateFolderSize(File folder) {
		long folderSize = 0;

	    // Prendo tutti i file interni della cartella
	    File[] files = folder.listFiles();

	    if (files != null) {
	        // Per ogni file interno, aggiungi la sua dimensione al totale
	        for (File file : files) {
	            if (file.isFile()) {
	                folderSize += file.length();
	            } else if (file.isDirectory()) {
	                // Chiamata ricorsiva per le sottocartelle
	                folderSize += calculateFolderSize(file);
	            }
	        }
	    }

	    return folderSize;
	}

}
