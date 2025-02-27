package java_intern;
import java.io.*;
import java.util.Scanner;

public class task_1 {
	public static void writeToFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println("Content successfully written to the file: " + fileName);
            System.exit(0);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        } }
    public static void readFromFile(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            System.exit(0);
            bufferedReader.close(); } 
        catch (IOException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        } }
      public static void modifyFile(String fileName, String oldContent, String newContent) {
        try {
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace(oldContent, newContent);
                fileContent.append(line).append(System.lineSeparator());
            }
            reader.close();
            FileWriter writer = new FileWriter(file);
            writer.write(fileContent.toString());
            writer.close();
            System.out.println("File modified successfully.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("An error occurred while modifying the file.");
            e.printStackTrace();
        } }
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the full path of the file (e.g., C:\\Users\\YourUsername\\Documents\\example.txt): ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("The file does not exist. Please check the file path.");
            return;
        }
        System.out.println("\nSelect an operation:");
        System.out.println("1. Write to the file");
        System.out.println("2. Read from the file");
        System.out.println("3. Modify the file");
        System.out.print("Enter your choice (1/2/3): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline
        switch (choice) {
            case 1:
                System.out.print("Enter the content you want to write to the file: ");
                String content = scanner.nextLine();
                writeToFile(fileName, content);
                break;
            case 2:
                System.out.println("\nReading the content of the file:");
                readFromFile(fileName);
                break;
            case 3:
                System.out.print("Enter the word/phrase to replace: ");
                String oldContent = scanner.nextLine();
                System.out.print("Enter the new word/phrase: ");
                String newContent = scanner.nextLine();
                modifyFile(fileName, oldContent, newContent);
                break;
            default:
                System.out.println("Invalid choice. Exiting.");
                break;
        }
        scanner.close();
    }
}
