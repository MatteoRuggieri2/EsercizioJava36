package esercizi;

import java.io.File;
import java.util.Optional;

public interface FileSystem {
	
	/* Imposta il path completo di un file o una directory
	es. c:/lavoro/esercizi o C:/test.txt */
	public void setFileNamePath(String path);
	
	/* Restituisce il nome del file corrente es. test.txt */
	public String getFileName ();
	
	/* Restituisce il path del file es. C:/test.txt */
	public String getFilePath();
	
	/* Restituisce il File corrente impostato a livello di
	costruttore
	o setFileNamePath()
	*/
	public Optional<File> getFileCurrent();
	
	/* Restituisce true se il file corrente è un file */
	public boolean isFile();
	
	/* Restituisce true se il file corrente è una directory */
	public boolean isDirectory();
	
	/* Restituisce true se il file è nascosto */
	public boolean isHidden() throws FileSystemAccessError;
	
	/* Restituisce true se il file è scrivibile */
	public boolean isWriteble() throws FileSystemAccessError;
	
	/* Restituisce true se il file è leggibile */
	public boolean isReadeble() throws FileSystemAccessError;
	
	/* Restituisce il folder dove il file è definito
	Se il file corrente non è definito restituisce un Optional
	null
	*/
	public Optional<File> folderOwner();

	/* Restituisce un array con i file presenti nella directory
	Se il file corrente non è una directory o non ci sono files
	restituisce un array vuoto
	*/
	public File[] folderFilesName();
	
	/* Restituisce il size in bytes se il file corrente non è una
	directory */
	public long fileSize() throws FileSystemAccessError;
	
	/* Restituisce il size in bytes se il file corrente non è una
	directory
	Nel caso di directory restituisce la somma del size di tutti i
	files
	della directory corrente e di tutte le directory, a qualsiasi
	livello
	*/
	public long fileSizeNested() throws FileSystemAccessError;
	
	/*
	Crea la directory specificata dal parametro dirPath
	Restituisce true se creata con successo
	*/
	public boolean mkDir(File dirPath);
	
	/*
	Crea la directory con il nome specificato sotto il File
	corrente, se
	questo è una directory
	Restituisce true se creata con successo
	*/
	public boolean mkDir(String dirName);
	
	/*
	Restituisce true se il file o la directory corrente esiste
	*/
	public boolean exists();

	/*
	Crea il file specificato dal parametro File file
	Restituisce true se creato con successo
	*/
	public boolean create(File file);
	
	/*
	Svuota la directory corrente
	*/
	public boolean emptyDir();
	
	/*
	Deleta la directory corrente
	Restituisce true se deletata con successo
	*/
	public boolean deleteDir();
	
	/*
	Deleta la directory specificata dal parametro File dirName
	Restituisce true se deletata con successo
	*/
	public boolean deleteDir(File dirName);
	
	/*
	Deleta la directory specificata dal parametro String dirName
	Restituisce true se deletata con successo
	*/
	public boolean deleteDir(String dirName);
	
	/*
	Deleta il file specificato dal parametro File file
	Restituisce true se deletata con successo
	*/
	public boolean delete(File file);

	/*
	Restituisce la struttura delle directory contenute a partire
	da dir,ricorsivamente.
	Ogni elemento dell’array sarà del tipo;
	dirParent1/dirChild1
	dirParent1/dirChild2
	..
	dirChild1/dirParentx
	*/
	public String[] dirNested(File dir);
	
	/*
	Restituisce la struttura delle directory contenenti, a partire
	da dir, fino alla radice (il drive, indicato da ROOT).
	Ogni elemento dell’array sarà del tipo;
	dirParent1
	dirParent2
	..
	ROOT
	*/
	public String[] dirParents(File dir);
}
