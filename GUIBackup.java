import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

class GUI extends JFrame implements ActionListener{
    private JFrame frame;
    private JPanel startPanel = new JPanel();
    private JPanel indicesPanel = new JPanel();
    private JButton fileButton;
    private JButton indButton;
    private JLabel displayFiles;
    private ArrayList<File> filePathsArr;   // storing the filepaths in here for rn

    public GUI () {
        // Create Frame
        frame = new JFrame("Project Option 2: Vinna's Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);

        // putting MyName Search Engine
        JLabel name = new JLabel("Vinna's Search Engine");
        name.setBounds(5, 5, 150, 20);
        frame.add(name);

        // need to create a startPanel to hold all this info
        startPanel.setSize(600, 600);
        startPanel.setLayout(null);

        // Load Engine
        JLabel loadEngine = new JLabel("Load My Engine");
        loadEngine.setFont(new Font("Arial", Font.BOLD, 20));
        loadEngine.setBounds(225,150,200,50);
        startPanel.add(loadEngine);

        // Choose Files Button
        fileButton = new JButton("Choose Files");
        fileButton.setBounds(225,200,150,50);
        fileButton.addActionListener(this);
        startPanel.add(fileButton);


        // Inverted Indices Button
        indButton = new JButton("Construct Inverted Indices");
        indButton.setBounds(200,350,200,50);
        indButton.addActionListener(this);
        startPanel.add(indButton);

        // add that panel to the frame
        frame.add(startPanel);
        startPanel.setVisible(true);
    }

    // to display GUI, set it to visible & don't allow resizing of window cause i can't deal with that rn
    public void runGui() {
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Choose Files")) {
            pickFiles();
        }
        else if(command.equals("Construct Inverted Indices")) {
            // lets start with removing the first panel
            startPanel.removeAll();
            startPanel.repaint();
            startPanel.revalidate();

            invertedIndices();
        }
    }

    // PICKING FILES
    private void pickFiles() {
        // user chooses some file(s) with multiselection so they can choose more than one file if wanted
        JFileChooser chooseFiles = new JFileChooser();
        chooseFiles.setMultiSelectionEnabled(true);
        chooseFiles.setCurrentDirectory(new File("./Data"));    // so i dont' have to keep searching for data folder. im too lazy
        chooseFiles.setSelectedFile(new File(""));
        chooseFiles.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (chooseFiles.showOpenDialog(frame) == JFileChooser.OPEN_DIALOG) {
            // store files into an array
            File[] openFiles = chooseFiles.getSelectedFiles();

            // Case: if user wants to reselect files, check: if the displayFiles isn't empty, set it empty
            if(displayFiles != null) {
                displayFiles.setText("");
            }
            displayFiles = new JLabel();
            String appendText = "";
            filePathsArr = new ArrayList<File>();

            // getting the file names we are using
            for(int i = 0; i < openFiles.length; i++) {
                // contains filepath
                String filename = openFiles[i].toString();
                filePathsArr.add(openFiles[i]);

                // removing filepath and getting only the name
                int index = filename.lastIndexOf('\\');
                String outputFile = filename.substring(index+1, filename.length());
                appendText = appendText + outputFile +"<br/>";

                // display text to GUI
                displayFiles.setText("<html>" + appendText + "</html>");
                displayFiles.setBounds(225,250,500,75);
                startPanel.add(displayFiles);

            }

        } else {
            System.out.println("Exited without choosing files!");
        }
    }

    // DOING INVERTED INDICES
    private void invertedIndices() {
        System.out.println("INVERTED INDICES YO");

        // using the indicesPanel now
        JLabel loadEngine = new JLabel("bow chicka wow wow");
        loadEngine.setFont(new Font("Arial", Font.BOLD, 20));
        loadEngine.setBounds(225,150,200,50);
        indicesPanel.add(loadEngine);

        indicesPanel.setSize(600, 600);
        indicesPanel.setLayout(null);
        indicesPanel.repaint();
        indicesPanel.revalidate();
        indicesPanel.setVisible(true);
        frame.add(indicesPanel);
    }
}