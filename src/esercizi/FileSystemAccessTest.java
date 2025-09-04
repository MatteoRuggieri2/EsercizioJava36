package esercizi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileSystemAccessTest {
	
	static FileSystemAccess fsa;
	final static Path projectDir = Paths.get(System.getProperty("user.dir")); // Path dinamico della project folder
	final static String srcAbsPath = projectDir.toString() + "/src";
	final static String testFileNameAbsolutePath = srcAbsPath + "/text_files/test_file.txt";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		fsa = new FileSystemAccess();
	}
	
	@Test //OK
	void testConstructorEmpty() {
		fsa.setFile(null);
		fsa.setFileNamePath(null);
		assertEquals(null, fsa.getFile());
		assertEquals("", fsa.getFilePath());
	}
	
	@Test //OK
	void testConstructorFileNamePath() {
		String testFileNamePath = "src/text_files/test_file.txt";
		fsa = new FileSystemAccess(testFileNamePath);
		assertEquals(testFileNameAbsolutePath, fsa.getFilePath());
	}
	
	@Test //OK
	void testConstructorFile() {
		File testFile = new File("src/text_files/test_file.txt");
		fsa = new FileSystemAccess(testFile);
		assertEquals(testFile, fsa.getFile());
		assertEquals(testFileNameAbsolutePath, fsa.getFilePath());
	}
	
	@Test //OK
	void testGetFileNameEmpty() {
		fsa.setFile(null);
		assertEquals("", fsa.getFileName());
	}
	
	@Test //OK
	void testGetFileNameSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertEquals("test_file.txt", fsa.getFileName());
	}
	
	@Test //OK
	void testGetFilePathEmpty() {
		fsa.setFile(null);
		assertEquals("", fsa.getFilePath());
	}
	
	@Test //OK
	void testGetFilePathSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertEquals(testFileNameAbsolutePath, fsa.getFilePath());
	}
	
	@Test //OK
	void testGetFileCurrentEmpty() {
		fsa.setFile(null);
		assertEquals(Optional.empty(), fsa.getFileCurrent());
	}
	
	@Test //OK
	void testGetFileCurrentSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		Optional<File> testOptFile = Optional.ofNullable(fsa.getFile());
		assertEquals(testOptFile, fsa.getFileCurrent());
	}
	
	@Test //OK
	void testIsFileEmpty() {
		fsa.setFile(null);
		assertFalse(fsa.isFile());
	}
	
	@Test //OK
	void testIsFileSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertTrue(fsa.isFile());
	}
	
	@Test //OK
	void testIsFileSettedFolder() {
		fsa.setFile(new File("src/test_folder"));
		assertFalse(fsa.isFile());
	}
	
	@Test //OK
	void testIsDirectoryEmpty() {
		fsa.setFile(null);
		assertFalse(fsa.isDirectory());
	}
	
	@Test //OK
	void testIsDirectoryWithSettedFile() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertFalse(fsa.isDirectory());
	}
	
	@Test //OK
	void testIsDirectorySettedDirectory() {
		fsa.setFile(new File("src/test_empty_folder"));
		assertTrue(fsa.isDirectory());
	}
	
	@Test //OK
	void testIsHiddenEmpty() {
		fsa.setFile(null);
//		assertThrows(FileSystemAccessError.class,  () -> fsa.isHidden());
		assertThrows(FileSystemAccessError.class, fsa::isHidden); // Abbreviato con method reference
	
	}
	
	@Test //OK
	void testIsHiddenFileVisible() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		try {
			assertFalse(fsa.isHidden());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testIsHiddenFileHidden() {
		// Crossplatform Test (win -> hidden checkbox checked, mac & lin -> '.' before file name)
		boolean hiddenFileWin = false;
		boolean hiddenFileMacLin = false;
		try {
			fsa.setFile(new File("src/folder_containing_hidden_file/hidden_file.txt"));
			hiddenFileWin = fsa.isHidden();
			
			fsa.setFile(new File("src/folder_containing_hidden_file/.hidden_file.txt"));
			hiddenFileMacLin = fsa.isHidden();
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}

		assertTrue(hiddenFileWin || hiddenFileMacLin);
	}
	
	@Test //OK
	void testIsWritebleEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::isWriteble);
	}
	
	@Test //OK
	void testIsWritebleFileNotWriteble() {
		fsa.setFile(new File("src/folder_containing_not_writeble_file/not_writeble_file.txt"));
		try {
			assertFalse(fsa.isWriteble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testIsWritebleFileWriteble() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		try {
			assertTrue(fsa.isWriteble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testIsReadebleEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::isReadeble);
	}
	
	@Test //OK
	void testIsReadebleFileNotReadeble() {
		fsa.setFile(new File("src/folder_containing_not_readeble_file/not_readeble_file.txt"));
		try {
			assertFalse(fsa.isReadeble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testIsReadebleFileReadeble() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		try {
			assertTrue(fsa.isWriteble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testFolderOwnerEmpty() {
		fsa.setFile(null);
		assertEquals(Optional.empty(), fsa.folderOwner());
	}
	
	@Test //OK
	void testFolderOwnerSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		File folderOwner = new File("src/text_files");
		assertEquals(Optional.of(folderOwner), fsa.folderOwner());
	}
	
	@Test //OK
	void testFolderOwnerFileNotFound() {
		fsa.setFile(new File("src/text_files/dshree.txt"));
		File folderOwner = new File("src/text_files");
		assertEquals(Optional.of(folderOwner), fsa.folderOwner());
	}
	
	@Test //OK
	void testFolderFilesNameEmpty() {
		fsa.setFile(new File("src/test_empty_folder"));
		assertArrayEquals(new File[0], fsa.folderFilesName());
	}
	
	@Test //OK
	void testFolderFilesNameFull() {
		fsa.setFile(new File("src/text_files"));
		File[] testFilesArray = {
				new File("src/text_files/test_file.txt"),
				new File("src/text_files/test_file_2.txt"),
		};
		File[] actual = fsa.folderFilesName();
		Arrays.sort(testFilesArray);
		Arrays.sort(actual);
		assertArrayEquals(testFilesArray, actual);
	}
	
	@Test //OK
	void testFileSizeEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::fileSize);
	}
	
	@Test //OK
	void testFileSizeSettedFolder() {
		fsa.setFile(new File("src/test_empty_folder"));
		assertThrows(FileSystemAccessError.class, fsa::fileSize);
	}
	
	@Test //OK
	void testFileSizeSettedFile() {
		try {
			// Il file  "test_file.txt"  pesa 26 byte.
			fsa.setFile(new File("src/text_files/test_file.txt"));
			assertEquals(26, fsa.fileSize());
			
			// Il file  "test_file_2.txt"  pesa 7 byte.
			fsa.setFile(new File("src/text_files/test_file_2.txt"));
			assertEquals(7, fsa.fileSize());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
		
	}
	
	@Test //OK
	void testFileSizeNestedEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::fileSizeNested);
	}
	
	@Test //OK
	void testFileSizeNestedSettedFile() {
		try {
			// Il file  "test_file.txt"  pesa 26 byte.
			fsa.setFile(new File("src/text_files/test_file.txt"));
			assertEquals(26, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testFileSizeNestedSettedDirectory() {
		try {
			// La cartella "text_files" pesa 33 byte.
			fsa.setFile(new File("src/text_files"));
			assertEquals(33, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testFileSizeNestedSettedSubforders() {
		try {
			// Sono 2 test_file e 1 test_file_2, quindi 26 + 26 + 7 = 59
			fsa.setFile(new File("src/subfolder_size_calculation_test"));
			assertEquals(59, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test //OK
	void testFileSizeNestedSettedEmptyDirectory() {
		try {
			fsa.setFile(new File("src/test_empty_folder"));
			assertEquals(0, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testMkDirByFile() {
		File file = new File("src/folder_mkdir_test");
		assertTrue(fsa.mkDir(file));
		assertTrue(fsa.deleteDir(file)); // Riporto allo stato precedente
	}
	
	@Test
	void testMkDirByString() {
		assertTrue(fsa.mkDir("testMkDirByString"));
		assertTrue(fsa.deleteDir("testMkDirByString"));
	}
	
	@Test
	void testExistsFileOK() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertTrue(fsa.exists());
	}
	
	@Test
	void testExistsFileKO() {
		fsa.setFile(new File("src/text_files/pauhsdfjk.txt"));
		assertFalse(fsa.exists());
	}
	
	@Test
	void testExistsDirOK() {
		fsa.setFile(new File("src/text_files"));
		assertTrue(fsa.exists());
	}
	
	@Test
	void testExistsDirKO() {
		fsa.setFile(new File("src/text_filessss"));
		assertFalse(fsa.exists());
	}
	
	@Test
	void testCreateFile() {
		assertTrue(fsa.create(new File("src/test_create_file.txt")));
		
		//TODO -> Elimina il file creato per riportare allo stato precedente.
	}
	
	
	
	
	
	
	@Test
	void testDeleteDirByFile() {
		File file = new File("src/folder_deletedir_test");
		assertTrue(fsa.mkDir(file)); // Creo la dir da eliminare
		assertTrue(fsa.deleteDir(file));
	}
	
	@Test
	void testDeleteDirByString() {
		assertTrue(fsa.mkDir("folder_deletedir_test")); // Creo la dir da eliminare
		assertTrue(fsa.deleteDir("folder_deletedir_test"));
	}


}
