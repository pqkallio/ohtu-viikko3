/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

/**
 *
 * @author pqkallio
 */
public class FileUserDAO implements UserDao {
    private List<User> users;
    private String usernamesFileName;
    private String passwordsFileName;
    
    public FileUserDAO(String usernamesFilename, String passwordsFilename) throws FileNotFoundException, IOException {
        this.users = new ArrayList<>();
        this.usernamesFileName = usernamesFilename;
        this.passwordsFileName = passwordsFilename;
        File names = new File(usernamesFilename);
        File pwords = new File(passwordsFilename);
        
        if (names.exists() && pwords.exists()) {
            readUserDataFromFiles(names, pwords);
        } else {
            names.createNewFile();
            pwords.createNewFile();
        }
    }
    
    @Override
    public List<User> listAll() {
        return this.users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        this.users.add(user);
        File nameFile = new File(usernamesFileName);
        File pwordFile = new File(passwordsFileName);
        try {
            FileWriter nameWriter = new FileWriter(nameFile, true);
            FileWriter pwordWriter = new FileWriter(pwordFile, true);
            nameWriter.write(user.getUsername() + "\n");
            pwordWriter.write(user.getPassword() + "\n");
            nameWriter.close();
            pwordWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.toString() + ": unable to save user data");
        }
    }

    private void readUserDataFromFiles(File names, File pwords) throws FileNotFoundException {
        Scanner usernameReader = new Scanner(names);
        Scanner passwordReader = new Scanner(pwords);
        
        while (usernameReader.hasNextLine()) {
            String name = usernameReader.nextLine();
            String password = passwordReader.nextLine();
            User user = new User(name, password);
            this.users.add(user);
        }
        usernameReader.close();
        passwordReader.close();
    }
}
