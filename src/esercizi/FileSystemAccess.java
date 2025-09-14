package esercizi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		return this.file;
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
		if (getFileCurrent().isPresent()) {
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
		if (getFileCurrent().isEmpty()) {
			return false;
		}
		
		// Altrimenti faccio valutare al metodo
		return this.file.isFile();
	}

	@Override //OK
	public boolean isDirectory() {
		// Se il file è null ritorno false
		if (getFileCurrent().isEmpty()) {
			return false;
		}
		
		// Altrimenti faccio valutare al metodo
		return this.file.isDirectory();
	}

	@Override //OK
	public boolean isHidden() throws FileSystemAccessError {
		// Se il file è null non può essere nè visibile nè nascosto, quindi lancio l'exception
		if (getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("isHidden", null, "Il file non esiste", this.getFileName());
		}
		
		return this.file.isHidden();
	}

	@Override //OK
	public boolean isWriteble() throws FileSystemAccessError {
		// Se il file è null non può essere nè scrivibile nè non scrivibile, quindi lancio l'exception
		if (getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("isWriteble", null, "Il file non esiste", this.getFileName());
		}
		
		return this.file.canWrite();
	}

	@Override //OK
	public boolean isReadeble() throws FileSystemAccessError {
		// Se il file è null non può essere nè leggibile nè non leggibile, quindi lancio l'exception
		if (getFileCurrent().isEmpty()) {
			// LANCIO L'EXCEPTION APPLICATIVA
			throw new FileSystemAccessError("isReadeble", null, "Il file non esiste", this.getFileName());
		}
		
		return this.file.canRead();
	}

	// Questo metodo restituisce la cartella madre
	@Override //OK
	public Optional<File> folderOwner() {
		// Se il file è presente ritorno la cartella madre, altrimenti un Optional.empty()
		if (getFileCurrent().isPresent()) {
			return Optional.of(this.file.getParentFile());
		} else {
			return Optional.empty();
		}
				
	}

	@Override //OK
	public File[] folderFilesName() {
		// Se il File (ovvero la folder) è presente, ritorno l'array di nomi dei file interni, altrimenti un array vuoto.
		if (getFileCurrent().isPresent()) {
			return this.file.listFiles();
		} else {
			return new File[0];
		}
	}

	@Override //OK
	public long fileSize() throws FileSystemAccessError {
		
		// Se il file è stato istanziato, ed è un file e non una folder, ritorno la sua dimensione.
		if (getFileCurrent().isPresent() && isFile()) {
			return this.file.length();
		}
		
		// ALTRIMENTI LANCIO L'EXCEPTION APPLICATIVA
		throw new FileSystemAccessError("fileSize", null, "Il file non esiste", this.getFileName());
	}

	@Override //OK
	public long fileSizeNested() throws FileSystemAccessError {
		
		// Se il file è stato istanziato, ed è un file e non una folder, ritorno la sua dimensione.
		if (getFileCurrent().isPresent() && this.isFile()) {
			return this.file.length();
			
		} else if (getFileCurrent().isEmpty()) {
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

	@Override //OK
	public boolean mkDir(File dirPath) {
		return dirPath.mkdir();
	}

	@Override //OK
	public boolean mkDir(String dirName) {
		return mkDir(new File("src/" + dirName));
	}

	@Override //OK
	public boolean exists() {
		return getFile().exists();
	}

	@Override //OK
	public boolean create(File file) {
		if (file == null) { return false; }
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override //OK
	public boolean emptyDir() {
		File dirToBeEmptied = getFile();
		File[] elToDel = dirToBeEmptied.listFiles(); // Raccoglie sia dir che file
		
		List.of(elToDel).stream()
		  .forEach((el) -> {
			  // Se è una dir, richiamo ricorsivamente questo metodo per svuotarla
			  if (el.isDirectory()) {
				  FileSystemAccess fsa = new FileSystemAccess(el);
				  fsa.emptyDir();
			  }
			  delete(el);
		  });
		
		return dirNested(dirToBeEmptied).length < 1;
	}

	@Override //OK
	public boolean deleteDir() {
		return deleteDir(getFile());
	}

	@Override //OK
	public boolean deleteDir(File dirName) {
		return dirName.delete();
	}

	@Override //OK
	public boolean deleteDir(String dirName) {
		return deleteDir(new File("src/" + dirName));
	}

	@Override //OK
	public boolean delete(File file) {
		return file.delete();
	}

	@Override //OK
	public String[] dirNested(File dir) {
		String dirName = dir.getName();
		String[] rowNestedDir = dir.list();
		return List.of(rowNestedDir).stream()
							 .filter(s -> !s.contains("."))  // Non deve contenere "."
							 .map(s -> dirName + "/" + s)  // Aggiungo il nome della folder padre
							 .collect(Collectors.toList())
							 .toArray(new String[0]);
	}

	@Override //OK
	public String[] dirParents(File dir) {
		if (dir == null) {
            return new String[0];
        }

        List<String> parents = new ArrayList<>();
        File parent = dir.getParentFile();

        // risalgo la catena dei genitori
        while (parent != null) {
            parents.add(parent.getAbsolutePath());
            parent = parent.getParentFile();
        }

        return parents.toArray(new String[0]);
	}
	
	//OK
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
