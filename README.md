# File

## FileSystemAccess

L'obiettivo di questo esercizio è l'esplorazione della classe **java.io.File** eseguendo l'accesso al file system, folders, subfolders, creazione directories, files etc.

Creare la classe `FileSystemAccess` con relativo JUnit di test `FileSystemAccessTest`.

Sarà necessario a livello di interfaccia, per alcuni metodi, la clausola `throws FileSystemAccessError`.

La classe `FileSystemAccess` prevede i seguenti costruttori:

```java
FileSystemAccess()
FileSystemAccess(String fileNamePath)
FileSystemAccess(File file)
```

La classe `FileSystemAccess` avrà probabilmnte bisogno di una sola variabile di istanza di tipo _File_ che dovrà sempre essere aggiornata con il file corrente che si sta trattando.

### Output

Implementare l'interfaccia `FileSystem`, e sviluppare i metodi con tutte le funzionalità previste.

Deve essere definita anche una _exception_ applicativa di nome `FileSystemAccessError` che memorizzerà l’exception originaria.
**(IOException)**, il nome del metodo andato in exception e le informazioni sul file corrente che ha generato l’exception.

### Consigli

Tutte le funzionalità previste sono implementabili con i metodi della classe **java.io.File** ed eventuali elaborazioni.
Per l’implementazione, se necessario, si possono utilizzare le classi **Scanner**, **StringBuilder**, **Collection** etc.

I metodi della classe possono operare solo con un oggetto _File_ valido, impostato dal costruttore, oppure con un oggetto _File_ passato come parametro, dove i metodi lo prevedono.
Nel caso di costruttore vuoto il _File_ valido può essere impostato dal metodo `setFileNamePath(String path)`, prima di ogni altra operazione.

## Files

**FileSystem.java**

```java
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
```
