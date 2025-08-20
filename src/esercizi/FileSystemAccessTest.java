package esercizi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileSystemAccessTest {
	
	static FileSystemAccess fsa;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		fsa = new FileSystemAccess();
	}
	
	@Test
	void testConstructorEmpty() {
		fsa.setFile(null);
		fsa.setFileNamePath(null);
		assertEquals(null, fsa.getFile());
		assertEquals("", fsa.getFilePath());
	}
	
	@Test
	void testConstructorFileNamePath() {
		String testFileNamePath = "src/text_files/test_file.txt";
		String testFileNameAbsolutePath = "C:\\Users\\SP-Formazione\\Desktop\\Matteo Ruggieri\\matteo-eclipse-workbanch\\EsercizioJava36\\src\\text_files\\test_file.txt";
		fsa = new FileSystemAccess(testFileNamePath);
		assertEquals(testFileNameAbsolutePath, fsa.getFilePath());
	}
	
	@Test
	void testConstructorFile() {
		File testFile = new File("src/text_files/test_file.txt");
		fsa = new FileSystemAccess(testFile);
		assertEquals(testFile, fsa.getFile());
		assertEquals("C:\\Users\\SP-Formazione\\Desktop\\Matteo Ruggieri\\matteo-eclipse-workbanch\\EsercizioJava36\\src\\text_files\\test_file.txt", fsa.getFilePath());
	}
	
	@Test
	void testGetFileNameEmpty() {
		fsa.setFile(null);
		assertEquals("", fsa.getFileName());
	}
	
	@Test
	void testGetFileNameSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertEquals("test_file.txt", fsa.getFileName());
	}
	
	@Test
	void testGetFilePathEmpty() {
		fsa.setFile(null);
		assertEquals("", fsa.getFilePath());
	}
	
	@Test
	void testGetFilePathSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		assertEquals("C:\\Users\\SP-Formazione\\Desktop\\Matteo Ruggieri\\matteo-eclipse-workbanch\\EsercizioJava36\\src\\text_files\\test_file.txt", fsa.getFilePath());
	}
	
	@Test
	void testGetFileCurrentEmpty() {
		fsa.setFile(null);
		assertEquals(Optional.empty(), fsa.getFileCurrent());
	}
	
	@Test
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
	
	@Test
	void testIsHiddenEmpty() {
		fsa.setFile(null);
//		assertThrows(FileSystemAccessError.class,  () -> fsa.isHidden());
		assertThrows(FileSystemAccessError.class, fsa::isHidden);
		
		// QUESTO è UGUALE MA NON FUNZIONA
//		assertThrows(FileSystemAccessError.class, () -> {
//			fsa.isHidden();
//        });
	
	}
	
	@Test
	void testIsHiddenFileVisible() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		try {
			assertFalse(fsa.isHidden());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsHiddenFileHidden() {
		fsa.setFile(new File("src/folder_containing_hidden_file/hidden_file.txt"));
		try {
			assertTrue(fsa.isHidden());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsWritebleEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::isWriteble);
	}
	
	@Test
	void testIsWritebleFileNotWriteble() {
		fsa.setFile(new File("src/folder_containing_not_writeble_file/not_writeble_file.txt"));
		try {
			assertFalse(fsa.isWriteble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsWritebleFileWriteble() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		try {
			assertTrue(fsa.isWriteble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsReadebleEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::isReadeble);
	}
	
	@Test
	void testIsReadebleFileNotReadeble() {
		fsa.setFile(new File("src/folder_containing_not_readeble_file/not_readeble_file.txt"));
		try {
			assertFalse(fsa.isReadeble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsReadebleFileReadeble() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		try {
			assertTrue(fsa.isWriteble());
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testFolderOwnerEmpty() {
		fsa.setFile(null);
		assertEquals(Optional.empty(), fsa.folderOwner());
	}
	
	@Test
	void testFolderOwnerSetted() {
		fsa.setFile(new File("src/text_files/test_file.txt"));
		File folderOwner = new File("src/text_files");
		assertEquals(Optional.of(folderOwner), fsa.folderOwner());
	}
	
	@Test
	void testFolderOwnerFileNotFound() {
		fsa.setFile(new File("src/text_files/dshree.txt"));
		File folderOwner = new File("src/text_files");
		assertEquals(Optional.of(folderOwner), fsa.folderOwner());
	}
	
	@Test
	void testFolderFilesNameEmpty() {
		fsa.setFile(new File("src/test_empty_folder"));
		assertArrayEquals(new File[0], fsa.folderFilesName());
	}
	
	@Test
	void testFolderFilesNameFull() {
		fsa.setFile(new File("src/text_files"));
		File file1 = new File("src/text_files/test_file.txt");
		File file2 = new File("src/text_files/test_file_2.txt");
		File[] testFilesArray = {file1, file2};
		assertArrayEquals(testFilesArray, fsa.folderFilesName());
	}
	
	@Test
	void testFileSizeEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::fileSize);
	}
	
	@Test
	void testFileSizeSettedFolder() {
		fsa.setFile(new File("src/test_empty_folder"));
		assertThrows(FileSystemAccessError.class, fsa::fileSize);
	}
	
	@Test
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
	
	@Test
	void testFileSizeNestedEmpty() {
		fsa.setFile(null);
		assertThrows(FileSystemAccessError.class, fsa::fileSizeNested);
	}
	
	@Test
	void testFileSizeNestedSettedFile() {
		try {
			// Il file  "test_file.txt"  pesa 26 byte.
			fsa.setFile(new File("src/text_files/test_file.txt"));
			assertEquals(26, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testFileSizeNestedSettedDirectory() {
		try {
			// La cartella "text_files" pesa 33 byte.
			fsa.setFile(new File("src/text_files"));
			assertEquals(33, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testFileSizeNestedSettedSubforders() {
		try {
			// Sono 2 test_file e 1 test_file_2, quindi 26 + 26 + 7 = 59
			fsa.setFile(new File("src/subfolder_size_calculation_test"));
			assertEquals(59, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testFileSizeNestedSettedEmptyDirectory() {
		try {
			// Il file  "test_file.txt"  pesa 26 byte.
			fsa.setFile(new File("src/test_empty_folder"));
			assertEquals(0, fsa.fileSizeNested());
			
		} catch (FileSystemAccessError e) {
			e.printStackTrace();
		}
	}


}
